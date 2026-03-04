import cv2
import mediapipe as mp
import customtkinter as ctk
from PIL import Image, ImageTk
import time
import random
import json
import os
import numpy as np
import webbrowser
import subprocess
from ultralytics import YOLO

# --- CONFIGURATION ---
DB_FILE = "study_data.json"
ctk.set_appearance_mode("Dark")
ctk.set_default_color_theme("blue")

# --- UTILS: DATA ---
class AnalyticsEngine:
    def __init__(self):
        if not os.path.exists(DB_FILE):
            with open(DB_FILE, 'w') as f: json.dump({"sessions": [], "xp": 100}, f) 
    
    def load_data(self):
        with open(DB_FILE, 'r') as f: return json.load(f)

    def save_session(self, duration_minutes, focus_score):
        data = self.load_data()
        session = {"date": time.strftime("%Y-%m-%d %H:%M"), "duration": duration_minutes}
        data["sessions"].append(session)
        data["xp"] += int(duration_minutes * focus_score)
        with open(DB_FILE, 'w') as f: json.dump(data, f)
        return data
        
    def force_save_xp(self, current_xp):
        data = self.load_data()
        data["xp"] = current_xp
        with open(DB_FILE, 'w') as f: json.dump(data, f)

# --- FIREWORKS (In-App) ---
class FireworkParticle:
    def __init__(self, canvas, x, y):
        self.canvas = canvas
        self.x, self.y = x, y
        self.vx, self.vy = random.uniform(-10, 10), random.uniform(-12, 2)
        self.gravity = 0.4
        self.life = 1.0
        self.size = random.uniform(4, 10)
        self.color = random.choice(["#FF3366", "#33CCFF", "#FFFF66", "#33FF66", "#CC33FF", "#FFFFFF"])
        self.id = self.canvas.create_oval(x, y, x+self.size, y+self.size, fill=self.color, outline="")

    def update(self):
        self.vy += self.gravity
        self.x += self.vx
        self.y += self.vy
        self.life -= 0.03
        self.canvas.coords(self.id, self.x, self.y, self.x+self.size, self.y+self.size)
        if self.life <= 0:
            self.canvas.delete(self.id)
            return False
        return True

# --- MAIN APP ---
class StudyTrackerApp(ctk.CTk):
    def __init__(self):
        super().__init__()
        self.title("NeuroFocus Dashboard")
        self.geometry("1100x750")
        self.configure(fg_color="#0d1117") 
        
        self.is_tracking = False
        self.start_time = None
        self.cap = None
        
        # Fluid Math & Punishment Variables
        self.current_health = 1.0
        self.punish_intensity = 0.0
        self.last_update_time = time.time()
        self.xp_bleed_accumulator = 0.0
        
        # Nuclear State Variables
        self.last_tab_open_time = 0  # NEW: Timer for continuous tabs
        self.nuclear_popup = None
        self.nuclear_start_time = 0
        self.is_alarm_ringing = False
        self.alarm_process = None
        
        # The Job Board Arsenal
        self.job_urls = [
            "https://careers.mcdonalds.com/",
            "https://wendys-careers.com/",
            "https://jobs.tacobell.com/",
            "https://careers.bk.com/",
            "https://careers.walmart.com/",
            "https://jobs.target.com/",
            "https://www.mysubwaycareer.com/",
            "https://jobs.dominos.com/",
            "https://jobs.chipotle.com/",
            "https://careers.homedepot.com/",
            "https://www.indeed.com/q-Retail-jobs.html",
            "https://www.snagajob.com/search?q=fast+food"
        ]
        
        self.face_mesh = None 
        self.yolo_model = None
        self.frame_count = 0
        self.gaze_history = [] 

        self.analytics = AnalyticsEngine()
        self.data = self.analytics.load_data()

        self.build_ui()
        self.after(200, self.finish_setup)

    def build_ui(self):
        self.grid_columnconfigure(0, weight=3) 
        self.grid_columnconfigure(1, minsize=300, weight=1) 
        self.grid_rowconfigure(0, weight=1)

        self.left_pane = ctk.CTkFrame(self, fg_color="transparent")
        self.left_pane.grid(row=0, column=0, sticky="nsew", padx=20, pady=20)
        
        self.video_container = ctk.CTkFrame(self.left_pane, corner_radius=15, fg_color="#161b22", border_width=2, border_color="#30363d")
        self.video_container.pack(expand=True, fill="both")
        self.video_container.pack_propagate(False) 
        
        self.loading_lbl = ctk.CTkLabel(self.video_container, text="Initializing Engine...", font=("Helvetica", 20))
        self.loading_lbl.place(relx=0.5, rely=0.5, anchor="center")
        
        self.video_label = ctk.CTkLabel(self.video_container, text="")
        self.firework_canvas = ctk.CTkCanvas(self.video_container, bg="#000000", highlightthickness=0)

        self.health_bar = ctk.CTkProgressBar(self.left_pane, height=12, corner_radius=6, progress_color="#2ea043", fg_color="#21262d")
        self.health_bar.set(1.0)
        self.health_bar.pack(fill="x", pady=(15, 0), padx=10)

        self.right_pane = ctk.CTkFrame(self, fg_color="transparent")
        self.right_pane.grid(row=0, column=1, sticky="nsew", padx=(0, 20), pady=20)

        self.profile_card = ctk.CTkFrame(self.right_pane, corner_radius=15, fg_color="#161b22", border_width=1, border_color="#30363d")
        self.profile_card.pack(fill="x", pady=(0, 15))
        
        self.lbl_rank = ctk.CTkLabel(self.profile_card, text=self.get_rank_title(), font=("Helvetica", 12, "bold"), text_color="#8b949e")
        self.lbl_rank.pack(pady=(15, 0))
        
        self.lbl_xp = ctk.CTkLabel(self.profile_card, text=f"{self.data['xp']} XP", font=("Helvetica", 36, "bold"), text_color="#58a6ff")
        self.lbl_xp.pack(pady=(0, 15))

        self.btn_toggle = ctk.CTkButton(self.profile_card, text="START SESSION", font=("Helvetica", 16, "bold"), fg_color="#2ea043", hover_color="#238636", height=50, corner_radius=8, command=self.toggle_tracking)
        self.btn_toggle.pack(padx=20, pady=(0, 20), fill="x")

        self.telemetry_card = ctk.CTkFrame(self.right_pane, corner_radius=15, fg_color="#161b22", border_width=1, border_color="#30363d")
        self.telemetry_card.pack(fill="both", expand=True)

        ctk.CTkLabel(self.telemetry_card, text="LIVE TELEMETRY", font=("Helvetica", 12, "bold"), text_color="#8b949e").pack(pady=(20, 20))
        
        self.lbl_status = ctk.CTkLabel(self.telemetry_card, text="IDLE", font=("Helvetica", 24, "bold"), text_color="#8b949e")
        self.lbl_status.pack(pady=(0, 20))

        self.lbl_gaze = ctk.CTkLabel(self.telemetry_card, text="Gaze Ratio: --", font=("Courier", 16), text_color="#c9d1d9")
        self.lbl_gaze.pack(pady=10)
        
        self.lbl_phone = ctk.CTkLabel(self.telemetry_card, text="Phone: SAFE", font=("Courier", 16), text_color="#2ea043")
        self.lbl_phone.pack(pady=10)

    def finish_setup(self):
        self.loading_lbl.configure(text="System Ready. Click Start.")

    def get_rank_title(self):
        xp = self.data['xp']
        level = 1 + int(max(0, xp) / 200) 
        if level < 2: return f"LEVEL {level}: ROOKIE"
        if level < 5: return f"LEVEL {level}: SCHOLAR"
        if level < 10: return f"LEVEL {level}: DEEP WORKER"
        return f"LEVEL {level}: NEURO-MASTER"

    def toggle_tracking(self):
        if not self.is_tracking:
            self.loading_lbl.place_forget()
            self.firework_canvas.place_forget()
            self.video_label.place(relx=0.5, rely=0.5, anchor="center")

            if self.face_mesh is None:
                self.lbl_status.configure(text="LOADING AI...", text_color="#d29922")
                self.update()
                self.mp_face_mesh = mp.solutions.face_mesh
                self.face_mesh = self.mp_face_mesh.FaceMesh(max_num_faces=1, refine_landmarks=True)
                self.yolo_model = YOLO('yolov8n.pt') 

            if self.cap is None:
                self.cap = cv2.VideoCapture(1, cv2.CAP_AVFOUNDATION)
                if not self.cap.isOpened():
                    self.cap = cv2.VideoCapture(0, cv2.CAP_AVFOUNDATION)
                    
            if not self.cap.isOpened():
                self.lbl_status.configure(text="CAMERA ERR", text_color="#f85149")
                self.cap = None
                return

            self.is_tracking = True
            self.start_time = time.time()
            self.last_update_time = time.time()
            self.current_health = 1.0
            self.punish_intensity = 0.0
            self.xp_bleed_accumulator = 0.0
            self.last_tab_open_time = 0
            self.gaze_history.clear()
            
            self.lbl_xp.configure(text_color="#58a6ff")
            self.btn_toggle.configure(text="STOP & SAVE", fg_color="#da3633", hover_color="#b62324")
            self.track_loop()
        else:
            self.is_tracking = False
            self.btn_toggle.configure(text="START SESSION", fg_color="#2ea043", hover_color="#238636")
            self.end_session()
            if self.cap is not None:
                self.cap.release()
                self.cap = None

    # --- OS HIJACK LOGIC ---
    def trigger_nuclear_alarm(self):
        if not self.is_alarm_ringing:
            self.is_alarm_ringing = True
            print("NUCLEAR ALARM TRIGGERED: Maxing volume and killing Bluetooth.")
            os.system("osascript -e 'set volume output volume 100'")
            os.system("/opt/homebrew/bin/blueutil -p 0") 
            self.ring_alarm()

    def ring_alarm(self):
        if self.is_alarm_ringing:
            self.alarm_process = subprocess.Popen(["afplay", "/System/Library/Sounds/Glass.aiff"])
            self.after(1000, self.ring_alarm) 

    def stop_nuclear_alarm(self):
        self.is_alarm_ringing = False
        if self.alarm_process:
            self.alarm_process.kill()
            self.alarm_process = None

    def heal_focus(self):
        # Destroys popups and stops alarms instantly when focus returns
        self.stop_nuclear_alarm()
        if self.nuclear_popup is not None:
            # Need to destroy widgets inside first to be safe
            for widget in self.nuclear_popup.winfo_children():
                widget.destroy()
            self.nuclear_popup.destroy()
            self.nuclear_popup = None

    def track_loop(self):
        if not self.is_tracking or self.cap is None: return

        now = time.time()
        dt = now - self.last_update_time
        self.last_update_time = now

        ret, frame = self.cap.read()
        if not ret: return

        frame = cv2.flip(frame, 1)
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        is_focused = False
        phone_detected = False
        self.frame_count += 1

        # 1. YOLO OBJECT DETECTION 
        if self.frame_count % 5 == 0:
            results = self.yolo_model(rgb_frame, classes=[67], verbose=False) 
            if len(results[0].boxes) > 0:
                phone_detected = True
                self.lbl_phone.configure(text="PHONE DETECTED!", text_color="#f85149")
            else:
                self.lbl_phone.configure(text="Phone: SAFE", text_color="#2ea043")

        # 2. MEDIAPIPE EYE TRACKING 
        results_face = self.face_mesh.process(rgb_frame)
        if results_face.multi_face_landmarks:
            landmarks = results_face.multi_face_landmarks[0].landmark
            try:
                l_eye_width = abs(landmarks[133].x - landmarks[33].x)
                l_ratio = abs(landmarks[468].x - landmarks[33].x) / l_eye_width if l_eye_width > 0 else 0.5
                r_eye_width = abs(landmarks[263].x - landmarks[362].x)
                r_ratio = abs(landmarks[473].x - landmarks[362].x) / r_eye_width if r_eye_width > 0 else 0.5
                raw_gaze = (l_ratio + r_ratio) / 2.0
                
                self.gaze_history.append(raw_gaze)
                if len(self.gaze_history) > 5: self.gaze_history.pop(0)
                smooth_gaze = sum(self.gaze_history) / len(self.gaze_history)
                
                self.lbl_gaze.configure(text=f"Gaze Ratio: {smooth_gaze:.2f}")

                if 0.45 < smooth_gaze < 0.55:
                    is_focused = True
            except IndexError:
                pass

        # 3. PUNISHMENT MATH
        if phone_detected:
            self.current_health = 0.0 
        elif not is_focused:
            self.current_health -= dt / 5.0 # Drains over 5 seconds
        else:
            self.current_health += dt / 2.0 

        self.current_health = max(0.0, min(1.0, self.current_health))
        self.health_bar.set(self.current_health)

        # 4. EXTREME CONSEQUENCE ENGINE
        if self.current_health <= 0.0:
            # Action 1: Continuous Job Spam
            if now - self.last_tab_open_time > 2.0: # Open a new tab every 2 seconds
                webbrowser.open(random.choice(self.job_urls))
                self.last_tab_open_time = now

            # Action 2: XP Bleed
            self.xp_bleed_accumulator += dt * 15 
            if self.xp_bleed_accumulator >= 1.0:
                lost_xp = int(self.xp_bleed_accumulator)
                self.data['xp'] -= lost_xp 
                self.xp_bleed_accumulator -= lost_xp
                self.lbl_xp.configure(text=f"{self.data['xp']} XP", text_color="#f85149")
                self.lbl_rank.configure(text=self.get_rank_title()) 

            # Action 3: The Nuclear Popup
            if self.data['xp'] <= 0:
                if self.nuclear_popup is None:
                    self.nuclear_popup = ctk.CTkToplevel(self)
                    self.nuclear_popup.title("FATAL WARNING")
                    self.nuclear_popup.geometry("650x250")
                    self.nuclear_popup.attributes("-topmost", True)
                    self.nuclear_popup.configure(fg_color="#300000")
                    
                    ctk.CTkLabel(self.nuclear_popup, text="You have 5 seconds before you are fucked.", font=("Helvetica", 24, "bold"), text_color="#ff4444").pack(pady=(40, 10))
                    self.lbl_nuke_count = ctk.CTkLabel(self.nuclear_popup, text="5.0", font=("Courier", 60, "bold"), text_color="white")
                    self.lbl_nuke_count.pack()
                    
                    self.nuclear_start_time = now
                
                # Update Popup Countdown
                time_left = max(0.0, 5.0 - (now - self.nuclear_start_time))
                self.lbl_nuke_count.configure(text=f"{time_left:.1f}")
                
                # Drop the hammer
                if time_left <= 0:
                    self.trigger_nuclear_alarm()
        else:
            # Focused! Stop all bleeding and close popups
            self.lbl_xp.configure(text_color="#58a6ff") 
            self.heal_focus()

        # 5. UI UPDATES & RED HAZE
        if self.current_health == 1.0:
            self.lbl_status.configure(text="FOCUSED", text_color="#2ea043")
            self.health_bar.configure(progress_color="#2ea043")
        elif self.current_health > 0.0:
            self.lbl_status.configure(text="LOSING FOCUS", text_color="#d29922")
            self.health_bar.configure(progress_color="#d29922")
        else:
            self.lbl_status.configure(text="XP BLEEDING!", text_color="#f85149")
            self.health_bar.configure(progress_color="#f85149")

        if self.current_health <= 0.0:
            self.punish_intensity = min(0.65, self.punish_intensity + dt * 1.5)
            self.video_container.configure(border_color="#f85149") 
        else:
            self.punish_intensity = max(0.0, self.punish_intensity - dt * 2.0)
            self.video_container.configure(border_color="#30363d") 

        if self.punish_intensity > 0:
            red_layer = np.full_like(rgb_frame, (255, 0, 0), dtype=np.uint8) 
            rgb_frame = cv2.addWeighted(rgb_frame, 1.0 - self.punish_intensity, red_layer, self.punish_intensity, 0)

        img_h, img_w, _ = rgb_frame.shape
        container_w = self.video_container.winfo_width()
        container_h = self.video_container.winfo_height()
        
        if container_w > 10 and container_h > 10:
            scale = min(container_w / img_w, container_h / img_h)
            new_w = int(img_w * scale)
            new_h = int(img_h * scale)
            
            img = Image.fromarray(rgb_frame)
            imgtk = ctk.CTkImage(light_image=img, dark_image=img, size=(new_w, new_h))
            self.video_label.configure(image=imgtk)
            
        self.after(30, self.track_loop)

    def end_session(self):
        self.heal_focus() 
        duration = (time.time() - self.start_time) / 60
        
        self.analytics.force_save_xp(self.data['xp'])
        self.data = self.analytics.save_session(duration, 10) 
        
        self.lbl_xp.configure(text=f"{self.data['xp']} XP", text_color="#58a6ff")
        self.lbl_rank.configure(text=self.get_rank_title())
        self.lbl_status.configure(text="IDLE", text_color="#8b949e")
        self.health_bar.set(1.0)
        self.video_container.configure(border_color="#30363d")
        
        self.video_label.place_forget()
        self.firework_canvas.place(relx=0, rely=0, relwidth=1, relheight=1)
        
        if duration > 0.05:
            self.launch_in_app_fireworks()
        else:
            self.firework_canvas.create_text(self.video_container.winfo_width()/2, self.video_container.winfo_height()/2, text="SESSION TOO SHORT", fill="#8b949e", font=("Helvetica", 24, "bold"))

    def launch_in_app_fireworks(self):
        self.firework_canvas.delete("all")
        particles = []
        cw = self.video_container.winfo_width()
        ch = self.video_container.winfo_height()
        for _ in range(3): 
            ox, oy = random.randint(int(cw*0.2), int(cw*0.8)), random.randint(int(ch*0.2), int(ch*0.6))
            for _ in range(150): 
                particles.append(FireworkParticle(self.firework_canvas, ox, oy))
        
        def animate():
            if not self.is_tracking and particles:
                particles[:] = [p for p in particles if p.update()]
                self.after(20, animate)
            elif not particles and not self.is_tracking:
                self.firework_canvas.create_text(cw/2, ch/2, text="XP BANKED", fill="white", font=("Helvetica", 32, "bold"))
                
        animate()

if __name__ == "__main__":
    app = StudyTrackerApp()
    app.mainloop()

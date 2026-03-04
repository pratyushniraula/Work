import cv2
import mediapipe as mp
import customtkinter as ctk
from PIL import Image, ImageTk
import time
import random
import json
import os
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

# --- CONFIGURATION ---
DB_FILE = "study_data.json"
ctk.set_appearance_mode("Dark")
ctk.set_default_color_theme("dark-blue")

# --- UTILS: MATH & DATA ---
class AnalyticsEngine:
    def __init__(self):
        if not os.path.exists(DB_FILE):
            with open(DB_FILE, 'w') as f: json.dump({"sessions": [], "xp": 0, "level": 1}, f)
    
    def load_data(self):
        with open(DB_FILE, 'r') as f: return json.load(f)

    def save_session(self, duration_minutes, focus_score):
        data = self.load_data()
        session = {
            "date": time.strftime("%Y-%m-%d %H:%M"),
            "duration": duration_minutes,
            "focus_score": focus_score
        }
        data["sessions"].append(session)
        data["xp"] += int(duration_minutes * focus_score)
        with open(DB_FILE, 'w') as f: json.dump(data, f)
        return data

# --- VISUALS: FIREWORKS & CHAOS ---
class ChaosOverlay(ctk.CTkToplevel):
    def __init__(self):
        super().__init__()
        self.attributes("-fullscreen", True)
        self.attributes("-topmost", True)
        self.attributes("-alpha", 0.0)
        self.config(bg="red") 
        self.intensity = 0.0
        self.withdraw()

    def update_chaos(self, is_distracted):
        if is_distracted:
            self.deiconify()
            self.intensity = min(0.6, self.intensity + 0.05) 
        else:
            self.intensity = max(0.0, self.intensity - 0.1) 
            if self.intensity <= 0: self.withdraw()
        
        self.attributes("-alpha", self.intensity)

class Particle:
    def __init__(self, x, y, canvas):
        self.x, self.y = x, y
        self.canvas = canvas
        self.vx = random.uniform(-4, 4)
        self.vy = random.uniform(-4, 4)
        self.life = 1.0
        self.color = random.choice(["#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF"])
        self.id = self.canvas.create_oval(x, y, x+6, y+6, fill=self.color, outline="")

    def update(self):
        self.x += self.vx
        self.y += self.vy
        self.life -= 0.02
        self.canvas.coords(self.id, self.x, self.y, self.x+6, self.y+6)
        if self.life <= 0:
            self.canvas.delete(self.id)
            return False
        return True

# --- MAIN APP ---
class StudyTrackerApp(ctk.CTk):
    def __init__(self):
        super().__init__()
        self.title("NeuroFocus: The Eye Tracker")
        self.geometry("1100x700")
        
        self.is_tracking = False
        self.start_time = None
        self.cap = None
        
        # LAZY LOAD VARIABLES (Loaded only when needed to save main thread)
        self.face_mesh = None 
        self.chaos_overlay = None

        self.analytics = AnalyticsEngine()
        self.data = self.analytics.load_data()

        # Layout Setup
        self.grid_columnconfigure(1, weight=1)
        self.grid_rowconfigure(0, weight=1)

        # Sidebar
        self.sidebar = ctk.CTkFrame(self, width=250, corner_radius=0)
        self.sidebar.grid(row=0, column=0, sticky="nsew")
        
        self.lbl_level = ctk.CTkLabel(self.sidebar, text=f"LEVEL {self.calculate_level()}", font=("Arial", 24, "bold"))
        self.lbl_level.pack(pady=20)
        
        self.lbl_xp = ctk.CTkLabel(self.sidebar, text=f"XP: {self.data['xp']}", font=("Arial", 14))
        self.lbl_xp.pack(pady=5)

        self.skill_tree_btn = ctk.CTkButton(self.sidebar, text="Skill Tree (Perks)", command=self.open_skill_tree)
        self.skill_tree_btn.pack(pady=20)

        # Main Area
        self.main_frame = ctk.CTkFrame(self)
        self.main_frame.grid(row=0, column=1, sticky="nsew", padx=20, pady=20)

        self.tabview = ctk.CTkTabview(self.main_frame)
        self.tabview.pack(fill="both", expand=True)
        self.tab_monitor = self.tabview.add("Monitor")
        self.tab_analytics = self.tabview.add("Analytics")

        # Monitor Tab
        self.video_label = ctk.CTkLabel(self.tab_monitor, text="")
        self.video_label.pack(pady=10)
        
        self.btn_toggle = ctk.CTkButton(self.tab_monitor, text="START SESSION", fg_color="green", height=50, command=self.toggle_tracking)
        self.btn_toggle.pack(pady=10)

        self.lbl_status = ctk.CTkLabel(self.tab_monitor, text="System Idle", font=("Consolas", 16))
        self.lbl_status.pack()

        # Build Analytics UI fast
        self.setup_analytics_ui()

    def calculate_level(self):
        return 1 + int(self.data['xp'] / 500)

    def toggle_tracking(self):
        if not self.is_tracking:
            
            # 1. LAZY LOAD ML MODELS (First time click only)
            if self.face_mesh is None:
                self.lbl_status.configure(text="Loading ML Models (First time)...", text_color="yellow")
                self.update() # Force UI to update before hanging
                
                self.mp_face_mesh = mp.solutions.face_mesh
                self.face_mesh = self.mp_face_mesh.FaceMesh(max_num_faces=1, refine_landmarks=True)
                self.chaos_overlay = ChaosOverlay()

            # 2. START CAMERA
            if self.cap is None:
                self.lbl_status.configure(text="Waking up camera...", text_color="yellow")
                self.update()
                
                self.cap = cv2.VideoCapture(1, cv2.CAP_AVFOUNDATION)
                if not self.cap.isOpened():
                    self.cap = cv2.VideoCapture(0, cv2.CAP_AVFOUNDATION)
                    
            if not self.cap.isOpened():
                self.lbl_status.configure(text="CAMERA BLOCKED", text_color="red")
                self.cap = None
                return

            self.is_tracking = True
            self.start_time = time.time()
            self.btn_toggle.configure(text="STOP & SAVE", fg_color="red")
            self.track_loop()
        else:
            self.is_tracking = False
            self.btn_toggle.configure(text="START SESSION", fg_color="green")
            self.end_session()
            
            if self.cap is not None:
                self.cap.release()
                self.cap = None

    def track_loop(self):
        if not self.is_tracking or self.cap is None: return

        ret, frame = self.cap.read()
        if not ret: return

        frame = cv2.flip(frame, 1)
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        results = self.face_mesh.process(rgb_frame)
        h, w, c = frame.shape

        is_focused = False
        gaze_text = "Gaze: N/A"

        if results.multi_face_landmarks:
            landmarks = results.multi_face_landmarks[0].landmark
            
            try:
                # --- THE MATH ---
                # Left Eye Gaze Ratio
                l_eye_width = abs(landmarks[133].x - landmarks[33].x)
                l_iris_pos = abs(landmarks[468].x - landmarks[33].x)
                l_ratio = l_iris_pos / l_eye_width if l_eye_width > 0 else 0.5
                
                # Right Eye Gaze Ratio
                r_eye_width = abs(landmarks[263].x - landmarks[362].x)
                r_iris_pos = abs(landmarks[473].x - landmarks[362].x)
                r_ratio = r_iris_pos / r_eye_width if r_eye_width > 0 else 0.5
                
                # Average Gaze (Around 0.50 is looking straight ahead)
                gaze = (l_ratio + r_ratio) / 2.0
                gaze_text = f"Gaze Ratio: {gaze:.2f}"
                
                # VISUAL DEBUGGER: Draw bright cyan dots exactly on your pupils
                l_px = (int(landmarks[468].x * w), int(landmarks[468].y * h))
                r_px = (int(landmarks[473].x * w), int(landmarks[473].y * h))
                cv2.circle(frame, l_px, 4, (255, 255, 0), -1) 
                cv2.circle(frame, r_px, 4, (255, 255, 0), -1)

                # RUTHLESS FOCUS ZONE
                # 0.42 to 0.58 means you are looking dead straight. 
                # If you side-eye even a little bit, it breaks focus.
                if 0.42 < gaze < 0.58:
                    is_focused = True
                else:
                    cv2.putText(frame, "EYES DARTING!", (50, 90), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 3)

            except IndexError:
                # If it can't find the pupils, it throws this warning instead of crashing
                cv2.putText(frame, "PUPILS NOT DETECTED", (50, 90), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 165, 255), 3)

        # Print the live Gaze Ratio directly onto the video feed
        cv2.putText(frame, gaze_text, (50, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.8, (255, 255, 255), 2)

        # Trigger the Red Haze Chaos
        self.chaos_overlay.update_chaos(not is_focused)
        self.lbl_status.configure(text="FOCUSED" if is_focused else "DISTRACTED", text_color="green" if is_focused else "red")

        # Update UI Image
        img = Image.fromarray(rgb_frame)
        imgtk = ctk.CTkImage(light_image=img, dark_image=img, size=(480, 360))
        self.video_label.configure(image=imgtk)
        
        self.after(30, self.track_loop)

    def end_session(self):
        if self.chaos_overlay:
            self.chaos_overlay.withdraw()
            
        duration = (time.time() - self.start_time) / 60
        
        self.data = self.analytics.save_session(duration, 10) 
        self.lbl_xp.configure(text=f"XP: {self.data['xp']}")
        self.lbl_level.configure(text=f"LEVEL {self.calculate_level()}")
        self.refresh_analytics()
        
        if duration > 0.05: # Trigger fireworks if session was > 3 seconds
            self.launch_fireworks()

    def launch_fireworks(self):
        top = ctk.CTkToplevel(self)
        top.attributes("-fullscreen", True)
        top.attributes("-alpha", 0.7)
        top.attributes("-topmost", True)
        canvas = ctk.CTkCanvas(top, bg="black", highlightthickness=0)
        canvas.pack(fill="both", expand=True)
        
        particles = []
        for _ in range(5): 
            ox, oy = random.randint(200, 800), random.randint(200, 600)
            for _ in range(50):
                particles.append(Particle(ox, oy, canvas))
        
        def animate():
            if not particles:
                top.destroy()
                return
            particles[:] = [p for p in particles if p.update()]
            top.after(20, animate)
            
        animate()

    def setup_analytics_ui(self):
        self.fig, self.ax = plt.subplots(figsize=(5, 4), dpi=100)
        self.fig.patch.set_facecolor('#2b2b2b') 
        self.ax.set_facecolor('#2b2b2b')
        self.ax.tick_params(colors='white')
        self.canvas_agg = FigureCanvasTkAgg(self.fig, master=self.tab_analytics)
        self.canvas_agg.get_tk_widget().pack(fill="both", expand=True)
        self.refresh_analytics()

    def refresh_analytics(self):
        if self.calculate_level() < 3:
            self.ax.clear()
            self.ax.text(0.5, 0.5, "LOCKED UNTIL LEVEL 3", color="red", ha='center', fontsize=20)
            self.canvas_agg.draw()
            return

        sessions = self.data["sessions"]
        if not sessions: return
        
        dates = [s["date"] for s in sessions][-10:]
        durations = [s["duration"] for s in sessions][-10:]
        
        self.ax.clear()
        self.ax.plot(range(len(dates)), durations, color="#00ff00", marker="o")
        self.ax.set_title("Focus Duration (Last 10 Sessions)", color="white")
        self.ax.set_ylabel("Minutes", color="white")
        self.canvas_agg.draw()

    def open_skill_tree(self):
        win = ctk.CTkToplevel(self)
        win.title("Skill Tree")
        win.geometry("400x400")
        
        skills = [
            ("Basic Analytics", 1, "Unlocked"),
            ("Dark Mode+ (Blue Theme)", 2, "Unlocks at Lvl 2"),
            ("Advanced Graphs", 3, "Unlocks at Lvl 3"),
            ("God Mode (No Penalties)", 10, "Unlocks at Lvl 10")
        ]
        
        curr_lvl = self.calculate_level()
        for name, req_lvl, status in skills:
            color = "green" if curr_lvl >= req_lvl else "gray"
            state = "Unlocked" if curr_lvl >= req_lvl else "LOCKED"
            
            f = ctk.CTkFrame(win, fg_color="transparent", border_color=color, border_width=2)
            f.pack(pady=10, padx=20, fill="x")
            
            ctk.CTkLabel(f, text=name, font=("Arial", 14, "bold")).pack(side="left", padx=10)
            ctk.CTkLabel(f, text=state, text_color=color).pack(side="right", padx=10)

if __name__ == "__main__":
    app = StudyTrackerApp()
    app.mainloop()

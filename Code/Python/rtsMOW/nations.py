import pygame
import random
import math
from collections import deque
import sys

# ==========================================
# CONFIGURATION & CONSTANTS
# ==========================================
SCREEN_WIDTH, SCREEN_HEIGHT = 1400, 900
GRID_COLS, GRID_ROWS = 400, 400
CELL_SIZE = 5
PROVINCE_COUNT = 300
FPS = 60

WATER_BG = (20, 45, 75)
LAND_BASE = (55, 85, 45)
BORDER_COLOR = (20, 20, 20)
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GRAY = (150, 150, 150)
UI_BG = (25, 25, 30, 245)
UI_ACCENT = (60, 120, 170)
UI_HIGHLIGHT = (100, 180, 220)

NATION_COLORS = [
    (200, 50, 50), (50, 150, 200), (200, 180, 50), (120, 50, 200),
    (50, 200, 150), (200, 100, 50), (150, 50, 100), (50, 100, 200),
    (180, 180, 180), (100, 200, 50), (150, 200, 200), (255, 120, 120)
]

TACTICS = {
    "Assault": {
        "atk": ["Charge", "Skirmish", "Flank", "Bombard", "Retreat"],
        "def": ["Shield Wall", "Skirmish", "Counter-Charge", "Fortify", "Retreat"]
    },
    "Siege": {
        "atk": ["Assault Walls", "Bombard", "Undermine", "Starve", "Retreat"],
        "def": ["Repel", "Counter-Sortie", "Repair", "Hold", "Retreat"]
    },
    "Ambush": {
        "atk": ["Spring Trap", "Encircle", "Volley", "Charge", "Retreat"],
        "def": ["Breakout", "Form Square", "Counter-Charge", "Panic", "Retreat"]
    },
    "Amphibious": {
        "atk": ["Land Marines", "Naval Bombard", "Screen", "Retreat"],
        "def": ["Coastal Barrage", "Repel Landing", "Fall Back", "Retreat"]
    },
    "Naval": {
        "atk": ["Boarding", "Broadside", "Ram", "Retreat"],
        "def": ["Evasive", "Broadside", "Brace", "Retreat"]
    }
}

COMBAT_MATRIX = {
    "Assault": {
        "Charge": {"Shield Wall": (0.5, 1.8), "Skirmish": (1.5, 0.8), "Counter-Charge": (2.0, 2.0), "Fortify": (0.4, 2.5)},
        "Skirmish": {"Shield Wall": (1.4, 0.6), "Skirmish": (1.0, 1.0), "Counter-Charge": (0.8, 1.6), "Fortify": (1.2, 0.4)},
        "Flank": {"Shield Wall": (1.8, 0.5), "Skirmish": (1.2, 1.0), "Counter-Charge": (1.5, 1.5), "Fortify": (1.5, 0.8)},
        "Bombard": {"Shield Wall": (2.0, 0.2), "Skirmish": (0.5, 0.5), "Counter-Charge": (1.5, 1.0), "Fortify": (2.5, 0.1)}
    },
    "Siege": {
        "Assault Walls": {"Repel": (0.5, 2.0), "Counter-Sortie": (1.5, 1.8), "Repair": (2.0, 0.5), "Hold": (0.8, 1.5)},
        "Bombard": {"Repel": (1.5, 0.5), "Counter-Sortie": (0.8, 1.5), "Repair": (0.5, 1.5), "Hold": (1.8, 0.2)},
        "Undermine": {"Repel": (1.8, 0.2), "Counter-Sortie": (0.5, 2.0), "Repair": (0.2, 2.0), "Hold": (2.5, 0.1)},
        "Starve": {"Repel": (0.5, 0.5), "Counter-Sortie": (1.2, 1.0), "Repair": (0.5, 0.5), "Hold": (1.0, 2.0)}
    },
    "Ambush": {
        "Spring Trap": {"Breakout": (1.5, 1.2), "Form Square": (0.8, 1.5), "Counter-Charge": (1.8, 2.0), "Panic": (2.5, 0.1)},
        "Encircle": {"Breakout": (0.5, 2.0), "Form Square": (1.5, 0.5), "Counter-Charge": (1.2, 1.2), "Panic": (2.0, 0.2)},
        "Volley": {"Breakout": (1.8, 0.5), "Form Square": (1.5, 0.5), "Counter-Charge": (0.8, 1.5), "Panic": (1.5, 0.2)},
        "Charge": {"Breakout": (1.2, 1.5), "Form Square": (0.4, 2.0), "Counter-Charge": (2.0, 2.0), "Panic": (3.0, 0.1)}
    },
    "Amphibious": {
        "Land Marines": {"Coastal Barrage": (0.4, 2.5), "Repel Landing": (0.8, 1.8), "Fall Back": (1.5, 0.2)},
        "Naval Bombard": {"Coastal Barrage": (1.2, 1.2), "Repel Landing": (2.0, 0.2), "Fall Back": (0.5, 0.5)},
        "Screen": {"Coastal Barrage": (1.5, 0.5), "Repel Landing": (0.8, 0.8), "Fall Back": (1.0, 1.0)}
    },
    "Naval": {
        "Boarding": {"Evasive": (0.4, 1.5), "Broadside": (1.8, 1.5), "Brace": (0.8, 1.5)},
        "Broadside": {"Evasive": (0.8, 0.8), "Broadside": (1.2, 1.2), "Brace": (1.5, 0.5)},
        "Ram": {"Evasive": (0.2, 2.0), "Broadside": (2.0, 1.0), "Brace": (1.5, 1.5)}
    }
}

# ==========================================
# UI & UTILITY CLASSES
# ==========================================
class Button:
    def __init__(self, x, y, w, h, text, font, color=UI_ACCENT):
        self.rect = pygame.Rect(x, y, w, h)
        self.text = text
        self.font = font
        self.color = color
        self.hovered = False
        self.selected = False

    def draw(self, surface):
        c = UI_HIGHLIGHT if self.selected else ((min(255, self.color[0]+40), min(255, self.color[1]+40), min(255, self.color[2]+40)) if self.hovered else self.color)
        pygame.draw.rect(surface, c, self.rect, border_radius=4)
        pygame.draw.rect(surface, WHITE, self.rect, 3 if self.selected else 1, border_radius=4)
        img = self.font.render(self.text, True, BLACK if self.selected else WHITE)
        surface.blit(img, (self.rect.centerx - img.get_width()//2, self.rect.centery - img.get_height()//2))

    def handle_event(self, event):
        if event.type == pygame.MOUSEMOTION:
            self.hovered = self.rect.collidepoint(event.pos)
        elif event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
            if self.hovered: return True
        return False

class TextInput:
    def __init__(self, x, y, w, h, font):
        self.rect = pygame.Rect(x, y, w, h)
        self.font = font
        self.text = ""
        self.active = False

    def draw(self, surface):
        pygame.draw.rect(surface, BLACK, self.rect)
        pygame.draw.rect(surface, UI_HIGHLIGHT if self.active else GRAY, self.rect, 2)
        surface.blit(self.font.render(self.text, True, WHITE), (self.rect.x + 5, self.rect.y + 5))

    def handle_event(self, event):
        if event.type == pygame.MOUSEBUTTONDOWN:
            self.active = self.rect.collidepoint(event.pos)
            return self.active
        if event.type == pygame.KEYDOWN and self.active:
            if event.key == pygame.K_RETURN: self.active = False
            elif event.key == pygame.K_BACKSPACE: self.text = self.text[:-1]
            elif event.unicode.isnumeric(): self.text += event.unicode
            return True
        return False

class GodPanelUI:
    def __init__(self, font):
        self.rect = pygame.Rect(0, 0, SCREEN_WIDTH, 45)
        self.font = font

    def draw(self, surface):
        pygame.draw.rect(surface, UI_BG, self.rect)
        pygame.draw.line(surface, WHITE, (0, 45), (SCREEN_WIDTH, 45), 2)
        surface.blit(self.font.render("GOD MODE: Drag to Move/Attack | Double-Click to Edit (No Drag) | WASD to Pan | SPACE to Spawn Army", True, WHITE), (20, 12))

class DiplomacyPromptUI:
    def __init__(self, font, title_font):
        self.font = font
        self.title_font = title_font
        self.active = False
        self.data = None
        self.rect = pygame.Rect(SCREEN_WIDTH//2 - 250, SCREEN_HEIGHT//2 - 150, 500, 300)
        self.btn_war = Button(self.rect.x + 50, self.rect.y + 150, 180, 40, "Declare War", font, (200, 50, 50))
        self.btn_ally = Button(self.rect.x + 270, self.rect.y + 150, 180, 40, "Form Alliance", font, (50, 200, 50))
        self.btn_cancel = Button(self.rect.x + 160, self.rect.y + 220, 180, 40, "Cancel (Snap Back)", font, GRAY)

    def open(self, attacker, defender, army, target, revert_pos):
        self.data = {"atk": attacker, "def": defender, "army": army, "target": target, "rx": revert_pos[0], "ry": revert_pos[1]}
        self.active = True
        status = attacker.diplomacy.get(defender.id, "Neutral")
        if status == "Ally":
            self.btn_war.text = "Break Alliance & War"
            self.btn_ally.text = "Already Allied"
        else:
            self.btn_war.text = "Declare War"
            self.btn_ally.text = "Form Alliance"

    def handle_event(self, event, engine):
        if not self.active: return False
        if event.type == pygame.MOUSEMOTION:
            for b in [self.btn_war, self.btn_ally, self.btn_cancel]: b.handle_event(event)
        elif event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
            if not self.rect.collidepoint(event.pos): return True
            if self.btn_cancel.handle_event(event):
                self.data["army"].world_x, self.data["army"].world_y = self.data["rx"], self.data["ry"]
                self.active = False
            elif self.btn_war.handle_event(event):
                self.data["atk"].set_diplomacy(self.data["def"], "War")
                engine.execute_drop(self.data["army"], self.data["target"])
                self.active = False
            elif self.btn_ally.handle_event(event) and self.btn_ally.text != "Already Allied":
                self.data["atk"].set_diplomacy(self.data["def"], "Ally")
                self.data["army"].world_x, self.data["army"].world_y = self.data["rx"], self.data["ry"]
                self.active = False
            return True
        return False

    def draw(self, surface):
        if not self.active: return
        s = pygame.Surface((SCREEN_WIDTH, SCREEN_HEIGHT), pygame.SRCALPHA)
        s.fill((0, 0, 0, 150))
        surface.blit(s, (0,0))
        pygame.draw.rect(surface, UI_BG, self.rect, border_radius=10)
        pygame.draw.rect(surface, WHITE, self.rect, 2, border_radius=10)
        title = self.title_font.render("DIPLOMATIC INCIDENT", True, WHITE)
        surface.blit(title, (self.rect.centerx - title.get_width()//2, self.rect.y + 20))
        desc = self.font.render(f"{self.data['atk'].name} forces have entered {self.data['def'].name} territory.", True, GRAY)
        surface.blit(desc, (self.rect.centerx - desc.get_width()//2, self.rect.y + 80))
        for b in [self.btn_war, self.btn_ally, self.btn_cancel]: b.draw(surface)

class UnitEditorUI:
    def __init__(self, font):
        self.font = font
        self.active = False
        self.target_army = None
        self.rect = pygame.Rect(SCREEN_WIDTH - 350, 50, 330, 450)
        self.inputs = {}
        self.btns_add = {}
        self.btns_sub = {}
        y_off = 80
        for troop in ["Infantry", "Cavalry", "Archers", "Artillery", "Warships"]:
            self.inputs[troop] = TextInput(self.rect.x + 120, self.rect.y + y_off, 100, 30, font)
            self.btns_sub[troop] = Button(self.rect.x + 230, self.rect.y + y_off, 30, 30, "-", font)
            self.btns_add[troop] = Button(self.rect.x + 270, self.rect.y + y_off, 30, 30, "+", font)
            y_off += 50
        self.btn_apply = Button(self.rect.x + 50, self.rect.bottom - 60, 100, 35, "Apply", font, (50, 180, 50))
        self.btn_close = Button(self.rect.x + 180, self.rect.bottom - 60, 100, 35, "Close", font, (180, 50, 50))

    def open(self, army):
        self.target_army = army
        self.active = True
        for t in self.inputs: self.inputs[t].text = str(army.composition[t])

    def handle_event(self, event):
        if not self.active: return False
        for t, inp in self.inputs.items():
            if inp.handle_event(event): return True
        if event.type == pygame.MOUSEMOTION:
            for b in self.btns_add.values(): b.handle_event(event)
            for b in self.btns_sub.values(): b.handle_event(event)
            self.btn_apply.handle_event(event)
            self.btn_close.handle_event(event)
        elif event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
            if not self.rect.collidepoint(event.pos): return False
            amt = 1000 if pygame.key.get_pressed()[pygame.K_LSHIFT] else 100
            for t in self.inputs:
                if self.btns_add[t].handle_event(event):
                    val = int(self.inputs[t].text) if self.inputs[t].text else 0
                    self.inputs[t].text = str(val + amt)
                elif self.btns_sub[t].handle_event(event):
                    val = int(self.inputs[t].text) if self.inputs[t].text else 0
                    self.inputs[t].text = str(max(0, val - amt))
            if self.btn_apply.handle_event(event):
                for t in self.inputs: self.target_army.composition[t] = int(self.inputs[t].text) if self.inputs[t].text else 0
                self.active = False
            elif self.btn_close.handle_event(event):
                self.active = False
            return True
        return False

    def draw(self, surface):
        if not self.active or not self.target_army: return
        s = pygame.Surface((self.rect.w, self.rect.h), pygame.SRCALPHA)
        s.fill(UI_BG)
        surface.blit(s, (self.rect.x, self.rect.y))
        pygame.draw.rect(surface, WHITE, self.rect, 2)
        surface.blit(self.font.render(f"Edit Forces ({self.target_army.owner.name})", True, WHITE), (self.rect.x + 20, self.rect.y + 20))
        y_off = 85
        for troop in ["Infantry", "Cavalry", "Archers", "Artillery", "Warships"]:
            surface.blit(self.font.render(troop, True, GRAY), (self.rect.x + 20, self.rect.y + y_off))
            self.inputs[troop].draw(surface)
            self.btns_sub[troop].draw(surface)
            self.btns_add[troop].draw(surface)
            y_off += 50
        self.btn_apply.draw(surface)
        self.btn_close.draw(surface)

class TacticalBattleUI:
    def __init__(self, font, title_font, small_font):
        self.font = font
        self.title_font = title_font
        self.small_font = small_font
        self.active = False
        self.battle = None
        self.phase = "SETUP" 
        self.rect = pygame.Rect(SCREEN_WIDTH//2 - 400, SCREEN_HEIGHT//2 - 300, 800, 600)
        
        self.type_btns = {
            "Assault": Button(self.rect.x + 50, self.rect.y + 200, 130, 40, "Field Assault", font),
            "Siege": Button(self.rect.x + 190, self.rect.y + 200, 130, 40, "Siege", font),
            "Ambush": Button(self.rect.x + 330, self.rect.y + 200, 130, 40, "Ambush", font),
            "Amphibious": Button(self.rect.x + 470, self.rect.y + 200, 130, 40, "Amphibious", font),
            "Naval": Button(self.rect.x + 610, self.rect.y + 200, 130, 40, "Naval Battle", font)
        }
        self.btn_start = Button(self.rect.centerx - 100, self.rect.y + 300, 200, 50, "COMMENCE BATTLE", font, (200, 50, 50))
        
        self.atk_btns = {}
        self.def_btns = {}
        self.btn_execute = Button(self.rect.centerx - 100, self.rect.y + 380, 200, 50, "EXECUTE TURN", font, (50, 180, 50))
        self.selected_atk_stance, self.selected_def_stance, self.selected_type = None, None, None

    def open(self, battle):
        self.battle = battle
        self.active = True
        self.phase = "SETUP"
        self.selected_type = None
        for b in self.type_btns.values(): b.selected = False

    def build_stances(self, b_type):
        self.atk_btns.clear()
        self.def_btns.clear()
        for i, name in enumerate(TACTICS[b_type]["atk"]):
            x, y = self.rect.x + 40 + (i%3)*110, self.rect.y + 200 + (i//3)*50
            self.atk_btns[name] = Button(x, y, 100, 40, name, self.font, (200,80,80) if name=="Retreat" else UI_ACCENT)
        for i, name in enumerate(TACTICS[b_type]["def"]):
            x, y = self.rect.x + 420 + (i%3)*110, self.rect.y + 200 + (i//3)*50
            self.def_btns[name] = Button(x, y, 100, 40, name, self.font, (200,80,80) if name=="Retreat" else UI_ACCENT)

    def handle_event(self, event):
        if not self.active: return False
        if event.type == pygame.MOUSEMOTION:
            if self.phase == "SETUP":
                for b in self.type_btns.values(): b.handle_event(event)
                self.btn_start.handle_event(event)
            else:
                for b in self.atk_btns.values(): b.handle_event(event)
                for b in self.def_btns.values(): b.handle_event(event)
                self.btn_execute.handle_event(event)
                
        elif event.type == pygame.MOUSEBUTTONDOWN and event.button == 1:
            if not self.rect.collidepoint(event.pos): return True 
            
            if self.phase == "SETUP":
                for name, btn in self.type_btns.items():
                    if btn.handle_event(event):
                        self.selected_type = name
                        for b in self.type_btns.values(): b.selected = False
                        btn.selected = True
                
                if self.btn_start.handle_event(event) and self.selected_type:
                    self.battle.set_type(self.selected_type)
                    self.build_stances(self.selected_type)
                    self.phase = "COMBAT"
                    self.selected_atk_stance, self.selected_def_stance = None, None
            else:
                for name, btn in self.atk_btns.items():
                    if btn.handle_event(event):
                        self.selected_atk_stance = name
                        for b in self.atk_btns.values(): b.selected = False
                        btn.selected = True

                for name, btn in self.def_btns.items():
                    if btn.handle_event(event):
                        self.selected_def_stance = name
                        for b in self.def_btns.values(): b.selected = False
                        btn.selected = True

                if self.btn_execute.handle_event(event) and self.selected_atk_stance and self.selected_def_stance:
                    ended = self.battle.execute_turn(self.selected_atk_stance, self.selected_def_stance)
                    self.selected_atk_stance, self.selected_def_stance = None, None
                    for b in self.atk_btns.values(): b.selected = False
                    for b in self.def_btns.values(): b.selected = False
                    if ended: self.active = False
            return True
        return False

    def draw(self, surface):
        if not self.active or not self.battle: return
        s = pygame.Surface((SCREEN_WIDTH, SCREEN_HEIGHT), pygame.SRCALPHA)
        s.fill((0, 0, 0, 200))
        surface.blit(s, (0,0))
        
        pygame.draw.rect(surface, UI_BG, self.rect, border_radius=10)
        pygame.draw.rect(surface, WHITE, self.rect, 2, border_radius=10)
        
        if self.phase == "SETUP":
            title = self.title_font.render("SELECT BATTLE TYPE", True, WHITE)
            surface.blit(title, (self.rect.centerx - title.get_width()//2, self.rect.y + 30))
            desc = self.font.render(f"{self.battle.atk_army.owner.name} is engaging {self.battle.def_entity.owner.name}.", True, GRAY)
            surface.blit(desc, (self.rect.centerx - desc.get_width()//2, self.rect.y + 100))
            for btn in self.type_btns.values(): btn.draw(surface)
            if self.selected_type: self.btn_start.draw(surface)
            else:
                pygame.draw.rect(surface, GRAY, self.btn_start.rect, border_radius=4)
                img = self.font.render("SELECT TACTIC", True, BLACK)
                surface.blit(img, (self.btn_start.rect.centerx - img.get_width()//2, self.btn_start.rect.centery - img.get_height()//2))
        else:
            title = self.title_font.render(f"TACTICAL COMBAT - TURN {self.battle.turn} ({self.battle.b_type.upper()})", True, (255, 100, 100))
            surface.blit(title, (self.rect.centerx - title.get_width()//2, self.rect.y + 20))
            
            atk_t = self.battle.atk_army.get_troops()
            def_t = self.battle.def_entity.get_troops() if isinstance(self.battle.def_entity, Army) else self.battle.def_entity.settlement.garrison
            def_type = "Field Army/Fleet" if isinstance(self.battle.def_entity, Army) else f"Settlement ({self.battle.def_entity.settlement.level})"
            
            surface.blit(self.font.render(f"ATTACKER: {self.battle.atk_army.owner.name} ({atk_t} Troops)", True, WHITE), (self.rect.x + 30, self.rect.y + 100))
            surface.blit(self.font.render(f"DEFENDER: {self.battle.def_entity.owner.name} [{def_type}] ({def_t} Troops)", True, WHITE), (self.rect.x + 400, self.rect.y + 100))
            pygame.draw.line(surface, GRAY, (self.rect.centerx, self.rect.y + 90), (self.rect.centerx, self.rect.y + 360))
            
            surface.blit(self.small_font.render("Select Attacker Stance:", True, GRAY), (self.rect.x + 100, self.rect.y + 160))
            for btn in self.atk_btns.values(): btn.draw(surface)
                
            surface.blit(self.small_font.render("Select Defender Stance:", True, GRAY), (self.rect.x + 480, self.rect.y + 160))
            for btn in self.def_btns.values(): btn.draw(surface)

            if self.selected_atk_stance and self.selected_def_stance: self.btn_execute.draw(surface)
            else:
                pygame.draw.rect(surface, GRAY, self.btn_execute.rect, border_radius=4)
                img = self.font.render("SELECT STANCES", True, BLACK)
                surface.blit(img, (self.btn_execute.rect.centerx - img.get_width()//2, self.btn_execute.rect.centery - img.get_height()//2))

            pygame.draw.rect(surface, (20, 20, 20), (self.rect.x + 20, self.rect.y + 460, 760, 120), border_radius=4)
            surface.blit(self.small_font.render("BATTLE LOG:", True, UI_HIGHLIGHT), (self.rect.x + 30, self.rect.y + 470))
            y_off = 490
            for line in self.battle.log[-5:]:
                surface.blit(self.small_font.render(line, True, WHITE), (self.rect.x + 30, self.rect.y + y_off))
                y_off += 20

# ==========================================
# CORE GAME CLASSES
# ==========================================
class Nation:
    def __init__(self, n_id, name, color):
        self.id = n_id
        self.name = name
        self.color = color
        self.diplomacy = {}

    def set_diplomacy(self, other, status):
        self.diplomacy[other.id] = status
        other.diplomacy[self.id] = status

class Settlement:
    def __init__(self, x, y):
        self.x, self.y = x, y
        self.level = random.choice(["Town", "City", "Castle"])
        self.garrison = {"Town": 1000, "City": 3000, "Castle": 6000}[self.level]

    def draw(self, surface, cam_x, cam_y, zoom):
        dx, dy = int(self.x * zoom - cam_x), int(self.y * zoom - cam_y)
        sz = int(14 * zoom)
        if self.level == "Town": pygame.draw.circle(surface, (120, 80, 40), (dx, dy), sz//2)
        elif self.level == "City": pygame.draw.rect(surface, (180, 180, 180), (dx - sz//2, dy - sz//2, sz, sz))
        elif self.level == "Castle": pygame.draw.polygon(surface, (80, 80, 80), [(dx, dy - sz), (dx + sz, dy + sz//2), (dx - sz, dy + sz//2)])
        pygame.draw.circle(surface, BLACK, (dx, dy), 3)

class Province:
    def __init__(self, p_id, cx, cy):
        self.id = p_id
        self.world_x, self.world_y = cx * CELL_SIZE, cy * CELL_SIZE
        self.owner = None
        self.pixels = []
        self.settlement = Settlement(self.world_x, self.world_y)

class Army:
    def __init__(self, owner, wx, wy):
        self.owner = owner
        self.world_x, self.world_y = wx, wy
        self.state = "IDLE"
        self.composition = {
            "Infantry": random.randint(1500, 4000), "Cavalry": random.randint(300, 1000),
            "Archers": random.randint(500, 1500), "Artillery": random.randint(10, 150),
            "Warships": random.randint(5, 50)
        }
        self.is_selected = False
        self.is_naval = False

    def get_troops(self): return sum(self.composition.values())

    def apply_losses(self, count):
        if self.get_troops() == 0: return
        pct = count / self.get_troops()
        for t in self.composition: self.composition[t] = max(0, int(self.composition[t] * (1 - pct)))

    def draw(self, surface, cam_x, cam_y, zoom, terrain_grid):
        gx, gy = int(self.world_x // CELL_SIZE), int(self.world_y // CELL_SIZE)
        if 0 <= gx < GRID_COLS and 0 <= gy < GRID_ROWS:
            self.is_naval = (terrain_grid[gx][gy] == "WATER")

        dx, dy = int(self.world_x * zoom - cam_x), int(self.world_y * zoom - cam_y)
        sz = int(24 * zoom)
        border_c = WHITE if self.is_selected or self.state == "DRAGGING" else BORDER_COLOR
        border_w = max(1, int(2*zoom))

        if self.is_naval:
            pts = [(dx - sz//2, dy - sz//4), (dx + sz//2, dy - sz//4), (dx + sz//3, dy + sz//3), (dx - sz//3, dy + sz//3)]
            pygame.draw.polygon(surface, self.owner.color, pts)
            pygame.draw.polygon(surface, border_c, pts, border_w)
            pygame.draw.polygon(surface, WHITE, [(dx, dy - sz), (dx, dy - sz//4), (dx + sz//2, dy - sz//4)])
        else:
            rect = pygame.Rect(dx - sz//2, dy - sz//2, sz, sz)
            pygame.draw.rect(surface, self.owner.color, rect, border_radius=int(6*zoom))
            pygame.draw.rect(surface, border_c, rect, border_w, border_radius=int(6*zoom))
        
        img = pygame.font.SysFont(None, int(18*zoom)).render(str(self.get_troops()), True, WHITE)
        surface.blit(img, (dx - img.get_width()//2, dy - sz - 10))

class Battle:
    def __init__(self, engine, atk_army, def_entity):
        self.engine = engine
        self.atk_army = atk_army
        self.def_entity = def_entity
        self.turn = 1
        self.log = ["Forces have engaged."]
        self.b_type = "Assault"
        self.revert_x, self.revert_y = atk_army.world_x, atk_army.world_y - 30

    def set_type(self, b_type):
        self.b_type = b_type
        self.log.append(f"Battle Doctrine set to: {b_type.upper()}")

    def execute_turn(self, atk_stance, def_stance):
        atk_t = self.atk_army.get_troops()
        def_t = self.def_entity.get_troops() if isinstance(self.def_entity, Army) else self.def_entity.settlement.garrison

        type_atk_mod, type_def_mod = 1.0, 1.0
        if self.b_type == "Siege": type_def_mod = 2.5
        elif self.b_type == "Ambush": type_atk_mod = 1.8; type_def_mod = 0.5
        elif self.b_type == "Amphibious": type_atk_mod = 0.6; type_def_mod = 1.5
        
        base_atk_dmg = atk_t * random.uniform(0.05, 0.15) * type_atk_mod
        base_def_dmg = def_t * random.uniform(0.05, 0.15) * type_def_mod
        
        a_mod, d_mod = 1.0, 1.0
        
        if atk_stance == "Retreat" or def_stance == "Retreat":
            if atk_stance == "Retreat" and def_stance != "Retreat":
                self.log.append("Attacker attempts to flee! Defenders cut them down.")
                d_mod, a_mod = 2.5, 0.0 
            elif def_stance == "Retreat" and atk_stance != "Retreat":
                self.log.append("Defender breaks and routes! Attackers pursue ruthlessly.")
                a_mod, d_mod = 2.5, 0.0
            elif atk_stance == "Retreat" and def_stance == "Retreat":
                self.log.append("Both armies retreat simultaneously in confusion.")
                a_mod, d_mod = 0.2, 0.2
        else:
            # Fetch custom matrix multipliers or default to 1.0
            a_mod, d_mod = COMBAT_MATRIX.get(self.b_type, {}).get(atk_stance, {}).get(def_stance, (1.0, 1.0))

        atk_losses = min(int(base_def_dmg * d_mod), atk_t)
        def_losses = min(int(base_atk_dmg * a_mod), def_t)

        self.atk_army.apply_losses(atk_losses)
        if isinstance(self.def_entity, Army): self.def_entity.apply_losses(def_losses)
        else: self.def_entity.settlement.garrison = max(0, self.def_entity.settlement.garrison - def_losses)

        self.log.append(f"T{self.turn}: {atk_stance} vs {def_stance} | Atk: -{atk_losses} | Def: -{def_losses}")

        atk_dead = self.atk_army.get_troops() <= 0
        def_dead = (self.def_entity.get_troops() <= 0) if isinstance(self.def_entity, Army) else (self.def_entity.settlement.garrison <= 0)
        
        if atk_dead or def_dead or atk_stance == "Retreat" or def_stance == "Retreat":
            self.resolve_end(atk_dead, def_dead, atk_stance == "Retreat", def_stance == "Retreat")
            return True 
            
        self.turn += 1
        return False

    def resolve_end(self, atk_dead, def_dead, atk_ret, def_ret):
        if atk_dead:
            if self.atk_army in self.engine.armies: self.engine.armies.remove(self.atk_army)
        elif def_dead:
            if isinstance(self.def_entity, Army):
                if self.def_entity in self.engine.armies: self.engine.armies.remove(self.def_entity)
            else:
                self.def_entity.owner = self.atk_army.owner
                # RESET GARRISON based on Settlement Tier
                tier = self.def_entity.settlement.level
                self.def_entity.settlement.garrison = {"Town": 1000, "City": 3000, "Castle": 6000}[tier]
                
                for px, py in self.def_entity.pixels:
                    self.engine.base_owner_grid[px][py] = self.atk_army.owner
                    self.engine.painted_owner_grid[px][py] = None
        else:
            if atk_ret:
                self.atk_army.world_x, self.atk_army.world_y = self.revert_x, self.revert_y
            if def_ret and isinstance(self.def_entity, Army):
                self.def_entity.world_x += 50
                self.def_entity.world_y += 50
        self.engine.active_battle = None

# ==========================================
# CORE ENGINE
# ==========================================
class GrandStrategyEngine:
    def __init__(self):
        pygame.init()
        self.screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
        pygame.display.set_caption("Omnipotent Sandbox: Cohesive Kingdoms")
        self.clock = pygame.time.Clock()
        
        self.font = pygame.font.SysFont("Arial", 16, bold=True)
        self.small_font = pygame.font.SysFont("Arial", 14)
        self.title_font = pygame.font.SysFont("Impact", 32)
        
        self.nations = [Nation(i, f"Kingdom {i+1}", NATION_COLORS[i]) for i in range(len(NATION_COLORS))]
        self.provinces = []
        self.armies = []
        
        self.terrain_grid = [["WATER" for _ in range(GRID_ROWS)] for _ in range(GRID_COLS)]
        self.base_owner_grid = [[None for _ in range(GRID_ROWS)] for _ in range(GRID_COLS)]
        self.painted_owner_grid = [[None for _ in range(GRID_ROWS)] for _ in range(GRID_COLS)]
        
        self.generate_massive_world()
        
        self.cam_x, self.cam_y = 0, 0
        self.zoom = 1.0
        self.dragged_army = None
        self.drag_start_pos = (0,0)
        self.active_battle = None
        
        self.ui_god = GodPanelUI(self.font)
        self.ui_editor = UnitEditorUI(self.font)
        self.ui_battle = TacticalBattleUI(self.font, self.title_font, self.small_font)
        self.ui_prompt = DiplomacyPromptUI(self.font, self.title_font)

        self.pre_rendered_terrain = pygame.Surface((GRID_COLS * CELL_SIZE, GRID_ROWS * CELL_SIZE))
        self.pre_rendered_terrain.fill(WATER_BG)
        for x in range(GRID_COLS):
            for y in range(GRID_ROWS):
                if self.terrain_grid[x][y] == "LAND":
                    pygame.draw.rect(self.pre_rendered_terrain, LAND_BASE, (x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE))

    def generate_massive_world(self):
        nodes = []
        for _ in range(12): 
            nx = random.randint(GRID_COLS//5, 4*GRID_COLS//5)
            ny = random.randint(GRID_ROWS//5, 4*GRID_ROWS//5)
            nr = random.randint(30, 90)
            nodes.append((nx, ny, nr))

        for x in range(GRID_COLS):
            for y in range(GRID_ROWS):
                if x < 20 or x > GRID_COLS-20 or y < 20 or y > GRID_ROWS-20:
                    self.terrain_grid[x][y] = "WATER"
                    continue
                in_core = False
                in_fringe = False
                for nx, ny, nr in nodes:
                    dist = math.hypot(x - nx, y - ny)
                    if dist <= nr: in_core = True
                    elif dist <= nr * 1.6: in_fringe = True

                if in_core: self.terrain_grid[x][y] = "LAND"
                elif in_fringe: self.terrain_grid[x][y] = "LAND" if random.random() < 0.50 else "WATER"

        for _ in range(5):
            new_grid = [["WATER" for _ in range(GRID_ROWS)] for _ in range(GRID_COLS)]
            for x in range(1, GRID_COLS-1):
                for y in range(1, GRID_ROWS-1):
                    l_count = sum(1 for dx in [-1,0,1] for dy in [-1,0,1] if self.terrain_grid[x+dx][y+dy] == "LAND" and (dx!=0 or dy!=0))
                    if self.terrain_grid[x][y] == "LAND": new_grid[x][y] = "LAND" if l_count >= 4 else "WATER"
                    else: new_grid[x][y] = "LAND" if l_count >= 5 else "WATER"
            self.terrain_grid = new_grid

        land_tiles = [(x, y) for x in range(GRID_COLS) for y in range(GRID_ROWS) if self.terrain_grid[x][y] == "LAND"]
        if not land_tiles: return

        queue = deque()
        for i in range(PROVINCE_COUNT): 
            if not land_tiles: break
            cx, cy = random.choice(land_tiles)
            prov = Province(i, cx, cy)
            self.provinces.append(prov)
            self.base_owner_grid[cx][cy] = prov 
            queue.append((cx, cy, prov))
            land_tiles.remove((cx, cy))

        dirs = [(0,1), (1,0), (0,-1), (-1,0)]
        while queue:
            x, y, prov = queue.popleft()
            prov.pixels.append((x, y))
            random.shuffle(dirs)
            for dx, dy in dirs:
                nx, ny = x + dx, y + dy
                if 0 <= nx < GRID_COLS and 0 <= ny < GRID_ROWS:
                    if self.terrain_grid[nx][ny] == "LAND" and self.base_owner_grid[nx][ny] is None:
                        self.base_owner_grid[nx][ny] = prov
                        queue.append((nx, ny, prov))

        for x in range(GRID_COLS):
            for y in range(GRID_ROWS):
                if self.terrain_grid[x][y] == "LAND" and self.base_owner_grid[x][y] is None:
                    self.terrain_grid[x][y] = "WATER"

        # VORONOI KINGDOM ALLOCATION (Cohesive Territories)
        capitals = random.sample(self.provinces, len(self.nations))
        for i, cap in enumerate(capitals):
            cap.owner = self.nations[i]

        unassigned = [p for p in self.provinces if p not in capitals]
        for prov in unassigned:
            closest_cap = min(capitals, key=lambda c: math.hypot(c.world_x - prov.world_x, c.world_y - prov.world_y))
            prov.owner = closest_cap.owner

        # Sync base_owner_grid
        for prov in self.provinces:
            for px, py in prov.pixels:
                self.base_owner_grid[px][py] = prov.owner

        for prov in self.provinces:
            if random.random() < 0.2: 
                self.armies.append(Army(prov.owner, prov.world_x, prov.world_y))

    def handle_input(self):
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit(); sys.exit()
                
            if self.ui_prompt.active:
                self.ui_prompt.handle_event(event, self)
                continue
            if self.ui_battle.active:
                self.ui_battle.handle_event(event)
                continue
            if self.ui_editor.active:
                self.ui_editor.handle_event(event)
                continue
                
            if event.type == pygame.MOUSEWHEEL:
                old_zoom = self.zoom
                self.zoom = max(0.15, min(self.zoom + event.y * 0.10, 3.5)) 
                self.cam_x = (self.cam_x + SCREEN_WIDTH//2) * (self.zoom / old_zoom) - SCREEN_WIDTH//2
                self.cam_y = (self.cam_y + SCREEN_HEIGHT//2) * (self.zoom / old_zoom) - SCREEN_HEIGHT//2

            elif event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    mx, my = event.pos
                    wx, wy = (mx + self.cam_x) / self.zoom, (my + self.cam_y) / self.zoom
                    clicked_army = next((a for a in self.armies if math.hypot(a.world_x - wx, a.world_y - wy) < 30), None)
                    
                    if clicked_army:
                        if clicked_army.is_selected: 
                            self.ui_editor.open(clicked_army)
                            self.dragged_army = None # PREVENTS FOLLOWING MOUSE
                        else:
                            for a in self.armies: a.is_selected = False
                            clicked_army.is_selected = True
                            self.dragged_army = clicked_army
                            self.dragged_army.state = "DRAGGING"
                            self.drag_start_pos = (clicked_army.world_x, clicked_army.world_y)

            elif event.type == pygame.MOUSEBUTTONUP:
                if event.button == 1 and self.dragged_army:
                    self.dragged_army.state = "IDLE"
                    self.evaluate_drop(self.dragged_army)
                    self.dragged_army = None

            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    mx, my = pygame.mouse.get_pos()
                    wx, wy = (mx + self.cam_x) / self.zoom, (my + self.cam_y) / self.zoom
                    gx, gy = int(wx // CELL_SIZE), int(wy // CELL_SIZE)
                    if 0 <= gx < GRID_COLS and 0 <= gy < GRID_ROWS:
                        hovered_owner = self.base_owner_grid[gx][gy]
                        if hovered_owner is not None:
                            self.armies.append(Army(hovered_owner, wx, wy))

        keys = pygame.key.get_pressed()
        s = 40 / self.zoom 
        if keys[pygame.K_w] or keys[pygame.K_UP]: self.cam_y -= s
        if keys[pygame.K_s] or keys[pygame.K_DOWN]: self.cam_y += s
        if keys[pygame.K_a] or keys[pygame.K_LEFT]: self.cam_x -= s
        if keys[pygame.K_d] or keys[pygame.K_RIGHT]: self.cam_x += s
        
        if self.dragged_army:
            mx, my = pygame.mouse.get_pos()
            self.dragged_army.world_x = (mx + self.cam_x) / self.zoom
            self.dragged_army.world_y = (my + self.cam_y) / self.zoom
            self.process_territory_bleed(self.dragged_army)

    def process_territory_bleed(self, army):
        gx, gy = int(army.world_x // CELL_SIZE), int(army.world_y // CELL_SIZE)
        
        if 0 <= gx < GRID_COLS and 0 <= gy < GRID_ROWS:
            if self.terrain_grid[gx][gy] == "WATER": return

        radius = 5
        for ix in range(gx - radius, gx + radius):
            for iy in range(gy - radius, gy + radius):
                if 0 <= ix < GRID_COLS and 0 <= iy < GRID_ROWS:
                    if self.terrain_grid[ix][iy] == "LAND" and math.hypot(ix - gx, iy - gy) <= radius:
                        base_owner = self.base_owner_grid[ix][iy]
                        if base_owner == army.owner:
                            self.painted_owner_grid[ix][iy] = None
                        elif base_owner is not None:
                            if army.owner.diplomacy.get(base_owner.id) == "War":
                                self.painted_owner_grid[ix][iy] = army.owner

    def evaluate_drop(self, army):
        wx, wy = army.world_x, army.world_y
        target_army = next((a for a in self.armies if a != army and math.hypot(a.world_x - wx, a.world_y - wy) < 30), None)
        if target_army:
            if army.owner == target_army.owner: return
            self._handle_diplomatic_collision(army, target_army.owner, target_army)
            return

        target_prov = next((p for p in self.provinces if math.hypot(p.world_x - wx, p.world_y - wy) < 25), None)
        if target_prov and target_prov.owner != army.owner:
            self._handle_diplomatic_collision(army, target_prov.owner, target_prov)
            return

    def _handle_diplomatic_collision(self, army, target_nation, target_entity):
        status = army.owner.diplomacy.get(target_nation.id, "Neutral")
        if status == "War":
            self.execute_drop(army, target_entity)
        else:
            self.ui_prompt.open(army.owner, target_nation, army, target_entity, self.drag_start_pos)

    def execute_drop(self, army, target_entity):
        battle = Battle(self, army, target_entity)
        self.active_battle = battle
        self.ui_battle.open(battle)

    def draw(self):
        self.screen.fill(WATER_BG)
        
        sw, sh = int(self.pre_rendered_terrain.get_width() * self.zoom), int(self.pre_rendered_terrain.get_height() * self.zoom)
        self.screen.blit(pygame.transform.scale(self.pre_rendered_terrain, (sw, sh)), (-self.cam_x, -self.cam_y))

        mgx = max(0, int(self.cam_x / (CELL_SIZE * self.zoom)))
        mgy = max(0, int(self.cam_y / (CELL_SIZE * self.zoom)))
        mx = min(GRID_COLS, int((self.cam_x + SCREEN_WIDTH) / (CELL_SIZE * self.zoom)) + 1)
        my = min(GRID_ROWS, int((self.cam_y + SCREEN_HEIGHT) / (CELL_SIZE * self.zoom)) + 1)

        for x in range(mgx, mx):
            for y in range(mgy, my):
                if self.terrain_grid[x][y] == "LAND":
                    dx = int(x * CELL_SIZE * self.zoom - self.cam_x)
                    dy = int(y * CELL_SIZE * self.zoom - self.cam_y)
                    ts = math.ceil(CELL_SIZE * self.zoom)
                    
                    base = self.base_owner_grid[x][y]
                    if base:
                        s = pygame.Surface((ts, ts), pygame.SRCALPHA)
                        s.fill((*base.color, 120))
                        self.screen.blit(s, (dx, dy))
                    
                    painted = self.painted_owner_grid[x][y]
                    if painted:
                        s = pygame.Surface((ts, ts), pygame.SRCALPHA)
                        s.fill((*painted.color, 220))
                        self.screen.blit(s, (dx, dy))

        for p in self.provinces: p.settlement.draw(self.screen, self.cam_x, self.cam_y, self.zoom)
        for a in self.armies: a.draw(self.screen, self.cam_x, self.cam_y, self.zoom, self.terrain_grid)
        
        self.ui_god.draw(self.screen)
        self.ui_editor.draw(self.screen)
        self.ui_battle.draw(self.screen)
        self.ui_prompt.draw(self.screen)

        pygame.display.flip()

    def run(self):
        while True:
            self.handle_input()
            self.draw()
            self.clock.tick(FPS)

if __name__ == "__main__":
    game = GrandStrategyEngine()
    game.run()

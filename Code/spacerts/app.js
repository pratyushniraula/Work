/**
 * STELLAR CLASH SANDBOX ENGINE v14
 * Massive Capital Ship HP Rebalance, Station Redesigns, & Support Auras
 */

// ==========================================
// 1. DATA & TEMPLATES
// ==========================================
let templates = [
    // --- SMALL CRAFT (Squadrons) ---
    // Heavily nerfed laser damage and HP
    { id: 'int_mk1', name: 'Interceptor Sqd', class: 'Small', subclass: 'Interceptor', formation: 6, hp: 6, shields: 2, regen: 0, speed: 170, turnRate: 3.5, size: 5, 
      weapons: [{type: 'missile', dmg: 0.4, cooldown: 0.3, range: 400, shots: 1, barrels: 2, spread: 0.02, speed: 900}], 
      shape: 'interceptor', hardpoints: [{x: 0, y: 0}] },
      
    { id: 'ftr_mk1', name: 'Fighter Sqd', class: 'Small', subclass: 'Fighter', formation: 6, hp: 9, shields: 4, regen: 0, speed: 140, turnRate: 2.5, size: 6, 
      weapons: [
          {type: 'laser', dmg: 0.5, cooldown: 0.5, range: 350, shots: 1, barrels: 4, spread: 0.05, speed: 800}, 
          {type: 'light_torp', dmg: 6.0, cooldown: 4.0, range: 500, shots: 1, barrels: 1, spread: 0.05, speed: 600}
      ], shape: 'xwing', hardpoints: [{x: 0, y: 0}] },
      
    { id: 'bmb_mk1', name: 'Bomber Sqd', class: 'Small', subclass: 'Bomber', formation: 4, hp: 15, shields: 8, regen: 0, speed: 90, turnRate: 1.2, size: 7, 
      weapons: [
          {type: 'laser', dmg: 0.5, cooldown: 1.0, range: 250, shots: 1, barrels: 2, spread: 0.05, speed: 800}, 
          {type: 'heavy_bomb', dmg: 25.0, cooldown: 5.0, range: 700, shots: 1, barrels: 1, spread: 0.02, speed: 400}
      ], shape: 'ywing', hardpoints: [{x: 2, y: 0}] },
    
    // --- LARGE CRAFT (Massively Scaled HP & Shields) ---
    { id: 'corvette_mk1', name: 'Anti-Fighter Corvette', class: 'Large', subclass: 'Cruiser', formation: 1, hp: 1000, shields: 400, regen: 8, speed: 70, turnRate: 1.0, size: 12, 
      weapons: [
          {type: 'laser_cannon', dmg: 3.0, cooldown: 0.6, range: 550, shots: 1, barrels: 2, spread: 0.05, speed: 1000},
          {type: 'laser_cannon', dmg: 3.0, cooldown: 0.6, range: 550, shots: 1, barrels: 2, spread: 0.05, speed: 1000},
          {type: 'laser_cannon', dmg: 3.0, cooldown: 0.6, range: 550, shots: 1, barrels: 2, spread: 0.05, speed: 1000}
      ], shape: 'corvette', hardpoints: [{x: 4, y: -2.5}, {x: 4, y: 2.5}, {x: -3, y: 0}] },

    { id: 'lfrig_mk1', name: 'Light Frigate', class: 'Large', subclass: 'Cruiser', formation: 1, hp: 1500, shields: 600, regen: 12, speed: 60, turnRate: 0.8, size: 18, 
      weapons: [
          {type: 'light_turbolaser', dmg: 10.0, cooldown: 2.0, range: 700, shots: 1, barrels: 1, spread: 0.08, speed: 900},
          {type: 'light_turbolaser', dmg: 10.0, cooldown: 2.0, range: 700, shots: 1, barrels: 1, spread: 0.08, speed: 900},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 550, shots: 1, barrels: 2, spread: 0.05, speed: 900},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 550, shots: 1, barrels: 2, spread: 0.05, speed: 900}
      ], shape: 'lfrig', hardpoints: [{x: 5, y: -2}, {x: 5, y: 2}, {x: -4, y: -2}, {x: -4, y: 2}] },

    { id: 'hfrig_mk1', name: 'Heavy Frigate', class: 'Large', subclass: 'Cruiser', formation: 1, hp: 2200, shields: 800, regen: 15, speed: 50, turnRate: 0.7, size: 22, 
      weapons: [
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 2.8, range: 800, shots: 1, barrels: 2, spread: 0.1, speed: 850},
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 2.8, range: 800, shots: 1, barrels: 2, spread: 0.1, speed: 850},
          {type: 'light_turbolaser', dmg: 10.0, cooldown: 2.0, range: 700, shots: 1, barrels: 1, spread: 0.08, speed: 900},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 550, shots: 1, barrels: 2, spread: 0.05, speed: 900}
      ], shape: 'hfrig', hardpoints: [{x: 6, y: -3.5}, {x: 6, y: 3.5}, {x: -2, y: 0}, {x: -8, y: 0}] },

    { id: 'mfrig_mk1', name: 'Long Range Frigate', class: 'Large', subclass: 'Cruiser', formation: 1, hp: 1800, shields: 600, regen: 12, speed: 55, turnRate: 0.75, size: 20, 
      weapons: [
          {type: 'med_missile', dmg: 22.0, cooldown: 3.5, range: 1200, shots: 1, barrels: 1, spread: 0.05, speed: 600},
          {type: 'med_missile', dmg: 22.0, cooldown: 3.5, range: 1200, shots: 1, barrels: 1, spread: 0.05, speed: 600},
          {type: 'med_missile', dmg: 22.0, cooldown: 3.5, range: 1200, shots: 1, barrels: 1, spread: 0.05, speed: 600},
          {type: 'laser_cannon', dmg: 3.0, cooldown: 0.8, range: 500, shots: 1, barrels: 2, spread: 0.05, speed: 900}
      ], shape: 'mfrig', hardpoints: [{x: 4, y: -4}, {x: 4, y: 4}, {x: -2, y: 0}, {x: -6, y: 0}] },

    // NEW: Shield Support Cruiser
    { id: 'supp_cruiser_mk1', name: 'Shield Support Cruiser', class: 'Large', subclass: 'Support', formation: 1, hp: 1500, shields: 1000, regen: 25, speed: 55, turnRate: 0.6, size: 18, 
      weapons: [
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 500, shots: 1, barrels: 2, spread: 0.05, speed: 900},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 500, shots: 1, barrels: 2, spread: 0.05, speed: 900}
      ], shape: 'support', hardpoints: [{x: 5, y: -5}, {x: 5, y: 5}] },

    { id: 'art_cruiser_mk1', name: 'Artillery Cruiser', class: 'Large', subclass: 'Cruiser', formation: 1, hp: 1800, shields: 600, regen: 10, speed: 45, turnRate: 0.5, size: 30, 
      weapons: [
          {type: 'heavy_turbolaser', dmg: 80.0, cooldown: 6.0, range: 1800, shots: 1, barrels: 1, spread: 0.02, speed: 1500}, 
          {type: 'laser_cannon', dmg: 3.0, cooldown: 0.8, range: 400, shots: 1, barrels: 2, spread: 0.1, speed: 900}
      ], shape: 'artillery', hardpoints: [{x: 12, y: 0}, {x: -6, y: 0}] },

    { id: 'torp_cruiser_mk1', name: 'Torpedo Cruiser', class: 'Large', subclass: 'Cruiser', formation: 1, hp: 2500, shields: 800, regen: 15, speed: 50, turnRate: 0.65, size: 26, 
      weapons: [
          {type: 'light_torp', dmg: 35.0, cooldown: 3.0, range: 800, shots: 1, barrels: 2, spread: 0.05, speed: 600},
          {type: 'light_torp', dmg: 35.0, cooldown: 3.0, range: 800, shots: 1, barrels: 2, spread: 0.05, speed: 600},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 500, shots: 1, barrels: 2, spread: 0.08, speed: 900}
      ], shape: 'torp_cruiser', hardpoints: [{x: 8, y: -4}, {x: 8, y: 4}, {x: -4, y: 0}] },

    { id: 'ldest_mk1', name: 'Light Destroyer', class: 'Large', subclass: 'Light Destroyer', formation: 1, hp: 3000, shields: 1200, regen: 18, speed: 45, turnRate: 0.6, size: 32, 
      weapons: [
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 2.8, range: 900, shots: 1, barrels: 2, spread: 0.1, speed: 850}, 
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 2.8, range: 900, shots: 1, barrels: 2, spread: 0.1, speed: 850},
          {type: 'light_turbolaser', dmg: 10.0, cooldown: 2.0, range: 700, shots: 1, barrels: 1, spread: 0.08, speed: 900},
          {type: 'light_turbolaser', dmg: 10.0, cooldown: 2.0, range: 700, shots: 1, barrels: 1, spread: 0.08, speed: 900},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 550, shots: 1, barrels: 2, spread: 0.05, speed: 1000},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 550, shots: 1, barrels: 2, spread: 0.05, speed: 1000}
      ], shape: 'ldest', hardpoints: [{x: 10, y: -6}, {x: 10, y: 6}, {x: 0, y: -8}, {x: 0, y: 8}, {x: -8, y: -3}, {x: -8, y: 3}] },

    // NEW: Missile Destroyer
    { id: 'missile_dest_mk1', name: 'Missile Destroyer', class: 'Large', subclass: 'Light Destroyer', formation: 1, hp: 3200, shields: 1200, regen: 18, speed: 40, turnRate: 0.5, size: 32, 
      weapons: [
          {type: 'med_missile', dmg: 25.0, cooldown: 3.0, range: 1200, shots: 1, barrels: 2, spread: 0.08, speed: 650},
          {type: 'med_missile', dmg: 25.0, cooldown: 3.0, range: 1200, shots: 1, barrels: 2, spread: 0.08, speed: 650},
          {type: 'med_missile', dmg: 25.0, cooldown: 3.0, range: 1200, shots: 1, barrels: 2, spread: 0.08, speed: 650},
          {type: 'med_missile', dmg: 25.0, cooldown: 3.0, range: 1200, shots: 1, barrels: 2, spread: 0.08, speed: 650},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 500, shots: 1, barrels: 2, spread: 0.05, speed: 900}
      ], shape: 'ldest', hardpoints: [{x: 8, y: -6}, {x: 8, y: 6}, {x: 0, y: -8}, {x: 0, y: 8}, {x: -8, y: 0}] },

    { id: 'hdest_mk1', name: 'Heavy Dreadnought', class: 'Large', subclass: 'Heavy Destroyer', formation: 1, hp: 5000, shields: 2000, regen: 25, speed: 30, turnRate: 0.3, size: 50,
      weapons: [
          {type: 'heavy_turbolaser', dmg: 25.0, cooldown: 3.5, range: 1200, shots: 1, barrels: 2, spread: 0.15, speed: 1100},
          {type: 'heavy_turbolaser', dmg: 25.0, cooldown: 3.5, range: 1200, shots: 1, barrels: 2, spread: 0.15, speed: 1100},
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 2.8, range: 1000, shots: 1, barrels: 2, spread: 0.1, speed: 850},
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 2.8, range: 1000, shots: 1, barrels: 2, spread: 0.1, speed: 850},
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 2.8, range: 1000, shots: 1, barrels: 2, spread: 0.1, speed: 850},
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 2.8, range: 1000, shots: 1, barrels: 2, spread: 0.1, speed: 850},
          {type: 'laser_cannon', dmg: 5.0, cooldown: 1.0, range: 600, shots: 1, barrels: 2, spread: 0.05, speed: 900},
          {type: 'laser_cannon', dmg: 5.0, cooldown: 1.0, range: 600, shots: 1, barrels: 2, spread: 0.05, speed: 900}
      ], shape: 'dreadnought', hardpoints: [{x: 15, y: -5}, {x: 15, y: 5}, {x: 5, y: -7}, {x: 5, y: 7}, {x: -5, y: -7}, {x: -5, y: 7}, {x: -15, y: -5}, {x: -15, y: 5}] },

    { id: 'cap_mk1', name: 'Capital Battleship (Carrier)', class: 'Large', subclass: 'Capital', formation: 1, hp: 8000, shields: 4000, regen: 40, speed: 20, turnRate: 0.2, size: 75,
      weapons: [
          {type: 'heavy_turbolaser', dmg: 25.0, cooldown: 4.5, range: 1500, shots: 1, barrels: 2, spread: 0.15, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 25.0, cooldown: 4.5, range: 1500, shots: 1, barrels: 2, spread: 0.15, speed: 1200},
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 3.0, range: 1200, shots: 1, barrels: 2, spread: 0.1, speed: 1000},
          {type: 'med_turbolaser', dmg: 16.0, cooldown: 3.0, range: 1200, shots: 1, barrels: 2, spread: 0.1, speed: 1000},
          {type: 'laser_cannon', dmg: 5.0, cooldown: 1.0, range: 600, shots: 1, barrels: 2, spread: 0.05, speed: 1000},
          {type: 'laser_cannon', dmg: 5.0, cooldown: 1.0, range: 600, shots: 1, barrels: 2, spread: 0.05, speed: 1000},
          {type: 'med_laser_cannon', dmg: 8.0, cooldown: 1.5, range: 800, shots: 1, barrels: 2, spread: 0.05, speed: 1000} 
      ], complement: { int: 12, ftr: 12, bmb: 6 }, launchRate: 4.0, shape: 'capital', 
      hardpoints: [{x: 20, y: -12}, {x: 20, y: 12}, {x: 5, y: -18}, {x: 5, y: 18}, {x: -15, y: -22}, {x: -15, y: 22}, {x: -30, y: 0}] },

    { id: 'cap_gun_mk1', name: 'Capital Battleship (Gunship)', class: 'Large', subclass: 'Capital', formation: 1, hp: 10000, shields: 5000, regen: 50, speed: 20, turnRate: 0.2, size: 75,
      weapons: [
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1500, shots: 1, barrels: 2, spread: 0.15, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1500, shots: 1, barrels: 2, spread: 0.15, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1500, shots: 1, barrels: 2, spread: 0.15, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1500, shots: 1, barrels: 2, spread: 0.15, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1500, shots: 1, barrels: 2, spread: 0.15, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1500, shots: 1, barrels: 2, spread: 0.15, speed: 1200},
          {type: 'med_turbolaser', dmg: 18.0, cooldown: 3.0, range: 1200, shots: 1, barrels: 2, spread: 0.1, speed: 1000},
          {type: 'med_turbolaser', dmg: 18.0, cooldown: 3.0, range: 1200, shots: 1, barrels: 2, spread: 0.1, speed: 1000}
      ], shape: 'capital', 
      hardpoints: [{x: 25, y: -10}, {x: 25, y: 10}, {x: 10, y: -16}, {x: 10, y: 16}, {x: -10, y: -22}, {x: -10, y: 22}, {x: -30, y: -26}, {x: -30, y: 26}] },

    { id: 'escort_carrier_mk1', name: 'Escort Carrier', class: 'Large', subclass: 'Carrier', formation: 1, hp: 2000, shields: 800, regen: 15, speed: 50, turnRate: 0.5, size: 28, 
      weapons: [
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 500, shots: 1, barrels: 2, spread: 0.05, speed: 900},
          {type: 'laser_cannon', dmg: 4.0, cooldown: 1.0, range: 500, shots: 1, barrels: 2, spread: 0.05, speed: 900}
      ], complement: { int: 6, ftr: 6, bmb: 0 }, launchRate: 4.0, shape: 'escort_carrier', 
      hardpoints: [{x: 8, y: -6}, {x: -8, y: 6}] },

    { id: 'station_mk1', name: 'Deep Space Station', class: 'Large', subclass: 'Station', formation: 1, hp: 25000, shields: 10000, regen: 100, speed: 0, turnRate: 0, size: 100, 
      weapons: [
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1800, shots: 1, barrels: 2, spread: 0.1, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1800, shots: 1, barrels: 2, spread: 0.1, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1800, shots: 1, barrels: 2, spread: 0.1, speed: 1200},
          {type: 'heavy_turbolaser', dmg: 35.0, cooldown: 4.5, range: 1800, shots: 1, barrels: 2, spread: 0.1, speed: 1200},
          {type: 'med_turbolaser', dmg: 18.0, cooldown: 3.0, range: 1400, shots: 1, barrels: 2, spread: 0.1, speed: 1000},
          {type: 'med_turbolaser', dmg: 18.0, cooldown: 3.0, range: 1400, shots: 1, barrels: 2, spread: 0.1, speed: 1000},
          {type: 'med_turbolaser', dmg: 18.0, cooldown: 3.0, range: 1400, shots: 1, barrels: 2, spread: 0.1, speed: 1000},
          {type: 'med_turbolaser', dmg: 18.0, cooldown: 3.0, range: 1400, shots: 1, barrels: 2, spread: 0.1, speed: 1000},
          {type: 'laser_cannon', dmg: 6.0, cooldown: 1.0, range: 800, shots: 1, barrels: 2, spread: 0.05, speed: 1000},
          {type: 'laser_cannon', dmg: 6.0, cooldown: 1.0, range: 800, shots: 1, barrels: 2, spread: 0.05, speed: 1000},
          {type: 'laser_cannon', dmg: 6.0, cooldown: 1.0, range: 800, shots: 1, barrels: 2, spread: 0.05, speed: 1000},
          {type: 'laser_cannon', dmg: 6.0, cooldown: 1.0, range: 800, shots: 1, barrels: 2, spread: 0.05, speed: 1000}
      ], complement: { int: 24, ftr: 24, bmb: 12 }, launchRate: 3.0, shape: 'station', 
      hardpoints: [
          {x: 75, y: 0}, {x: -75, y: 0}, {x: 0, y: 75}, {x: 0, y: -75},
          {x: 50, y: 50}, {x: -50, y: -50}, {x: 50, y: -50}, {x: -50, y: 50},
          {x: 25, y: 0}, {x: -25, y: 0}, {x: 0, y: 25}, {x: 0, y: -25}
      ] }
];

// ==========================================
// 2. ENGINE CLASSES
// ==========================================
class Ship {
    constructor(x, y, side, template, squadId = null) {
        this.id = Math.random().toString(36).substr(2, 9);
        this.squadId = squadId || this.id;
        this.x = x; this.y = y;
        this.vx = 0; this.vy = 0;
        this.angle = side === 2 ? Math.PI : 0; 
        this.side = side; 
        this.template = JSON.parse(JSON.stringify(template)); 
        this.hp = this.template.hp;
        this.shields = this.template.shields;
        this.waypoint = null;
        this.selected = false;
        
        this.turrets = this.template.weapons.map((w, i) => {
            let hpList = this.template.hardpoints || [{x:0, y:0}];
            let hp = hpList[i % hpList.length];
            return {
                weapon: w,
                x: hp.x,
                y: hp.y,
                angle: this.angle,
                target: null,
                cooldown: (i * (w.cooldown / this.template.weapons.length)) + (Math.random() * 0.2)
            };
        });
        
        this.hangarQueue = [];
        if (this.template.complement) {
            for(let i=0; i<this.template.complement.int; i++) this.hangarQueue.push('int_mk1');
            for(let i=0; i<this.template.complement.ftr; i++) this.hangarQueue.push('ftr_mk1');
            for(let i=0; i<this.template.complement.bmb; i++) this.hangarQueue.push('bmb_mk1');
        }
        this.carrierCooldown = 2.0; 
        this.drivingTarget = null;
    }

    update(dt, entities) {
        if (this.shields < this.template.shields) {
            this.shields = Math.min(this.template.shields, this.shields + this.template.regen * dt);
        }

        // Support Cruiser Aura Regeneration
        if (this.template.subclass === 'Support' && this.hp > 0) {
            entities.forEach(other => {
                if (other.side === this.side && other !== this && other.hp > 0) {
                    let d = Math.hypot(other.x - this.x, other.y - this.y);
                    if (d <= 400) {
                        other.shields = Math.min(other.template.shields, other.shields + (30 * dt)); 
                    }
                }
            });
        }

        if (this.hangarQueue.length > 0) {
            this.carrierCooldown -= dt;
            if (this.carrierCooldown <= 0) {
                this.launchWing(entities);
                this.carrierCooldown = this.template.launchRate || 5.0;
            }
        }

        let isStation = this.template.subclass === 'Station';

        if (!isStation) {
            if (!this.drivingTarget || this.drivingTarget.hp <= 0) {
                this.drivingTarget = this.findGlobalDrivingTarget(entities);
            }
        }

        // LAYERED COLLISION AVOIDANCE
        let sepX = 0, sepY = 0;
        if (!isStation) {
            let myIsLarge = this.template.class === 'Large';
            entities.forEach(other => {
                if (other === this || other.template.subclass === 'Station') return;
                let otherIsLarge = other.template.class === 'Large';
                if (myIsLarge !== otherIsLarge) return;

                let dist = Math.hypot(other.x - this.x, other.y - this.y);
                let buffer = this.template.size + other.template.size + (myIsLarge ? 20 : 6); 
                if (dist < buffer && dist > 0) {
                    let push = (buffer - dist) / buffer; 
                    sepX -= ((other.x - this.x) / dist) * push * (myIsLarge ? 30 : 15);
                    sepY -= ((other.y - this.y) / dist) * push * (myIsLarge ? 30 : 15);
                }
            });
        }

        // STEERING & THRUST
        let isManualRotating = (this.selected && this.side === activeSide && input.keys['c']);
        let hasTargetAngle = false;
        let targetAngle = 0;
        let shouldThrust = false; 
        
        let maxRange = this.turrets.length > 0 ? Math.max(...this.turrets.map(t => t.weapon.range)) : 0;
        let distToTarget = this.drivingTarget ? Math.hypot(this.drivingTarget.x - this.x, this.drivingTarget.y - this.y) : Infinity;

        if (isStation) {
            // Stations slowly rotate automatically
            this.vx = 0; this.vy = 0; sepX = 0; sepY = 0;
            this.angle += 0.08 * dt;
        } else {
            if (isManualRotating) {
                targetAngle = Math.atan2(input.mouseWorldY - this.y, input.mouseWorldX - this.x);
                hasTargetAngle = true;
                shouldThrust = false;
            } else if (this.waypoint) {
                targetAngle = Math.atan2(this.waypoint.y - this.y, this.waypoint.x - this.x);
                hasTargetAngle = true;
                shouldThrust = true;
                if (Math.hypot(this.waypoint.x - this.x, this.waypoint.y - this.y) < 30) this.waypoint = null;
            } else if (this.drivingTarget) {
                targetAngle = Math.atan2(this.drivingTarget.y - this.y, this.drivingTarget.x - this.x);
                hasTargetAngle = true;
                
                if (distToTarget > maxRange * 0.75) {
                    shouldThrust = true;
                } else {
                    shouldThrust = this.template.class === 'Small' ? 0.75 : false; 
                }
            }

            if (hasTargetAngle) {
                let diff = targetAngle - this.angle;
                while (diff < -Math.PI) diff += Math.PI * 2;
                while (diff > Math.PI) diff -= Math.PI * 2;
                this.angle += Math.sign(diff) * Math.min(Math.abs(diff), this.template.turnRate * dt);
            }

            if (shouldThrust === true) {
                this.vx = Math.cos(this.angle) * this.template.speed;
                this.vy = Math.sin(this.angle) * this.template.speed;
            } else if (typeof shouldThrust === 'number' && shouldThrust > 0) {
                this.vx = Math.cos(this.angle) * (this.template.speed * shouldThrust);
                this.vy = Math.sin(this.angle) * (this.template.speed * shouldThrust);
            } else {
                this.vx *= 0.95; this.vy *= 0.95; 
            }

            this.x += (this.vx + sepX) * dt;
            this.y += (this.vy + sepY) * dt;
        }

        // MULTI-TARGETING TURRET LOGIC
        this.turrets.forEach(t => {
            t.cooldown -= dt;
            
            if (!t.target || t.target.hp <= 0 || Math.hypot(t.target.x - this.x, t.target.y - this.y) > t.weapon.range + 200) {
                t.target = this.findTurretTarget(entities, t.weapon);
            }

            if (t.target) {
                let absX = this.x + (t.x * Math.cos(this.angle) - t.y * Math.sin(this.angle));
                let absY = this.y + (t.x * Math.sin(this.angle) + t.y * Math.cos(this.angle));
                
                let aimAngle = Math.atan2(t.target.y - absY, t.target.x - absX);
                let diff = aimAngle - t.angle;
                while (diff < -Math.PI) diff += Math.PI * 2;
                while (diff > Math.PI) diff -= Math.PI * 2;
                
                let tSpeed = t.weapon.type.includes('heavy') ? 1.5 : (t.weapon.type.includes('laser_cannon') ? 4.0 : 2.5);
                t.angle += Math.sign(diff) * Math.min(Math.abs(diff), tSpeed * dt);

                let dist = Math.hypot(t.target.x - absX, t.target.y - absY);
                
                if (t.cooldown <= 0 && dist <= t.weapon.range && Math.abs(diff) < 0.15) {
                    this.fireTurret(t, absX, absY, dist);
                    t.cooldown = t.weapon.cooldown;
                }
            } else {
                let diff = this.angle - t.angle;
                while (diff < -Math.PI) diff += Math.PI * 2;
                while (diff > Math.PI) diff -= Math.PI * 2;
                t.angle += Math.sign(diff) * Math.min(Math.abs(diff), 2.0 * dt);
            }
        });
    }

    findGlobalDrivingTarget(entities) {
        let bestScore = Infinity;
        let bestTarget = null;
        let prefersSmall = ['Interceptor', 'Fighter', 'Cruiser'].includes(this.template.subclass);
        let prefersCruiser = this.template.subclass === 'Light Destroyer';
        let prefersBig = ['Bomber', 'Heavy Destroyer', 'Capital'].includes(this.template.subclass);

        for (let e of entities) {
            if (e.side !== this.side && e.hp > 0) {
                let d = Math.hypot(e.x - this.x, e.y - this.y);
                let isSmall = e.template.class === 'Small';
                let score = d; 

                if (prefersSmall && !isSmall) score += 10000;
                if (prefersBig && isSmall) score += 10000;
                if (prefersCruiser && e.template.subclass !== 'Cruiser') score += 5000;

                if (score < bestScore) {
                    bestScore = score;
                    bestTarget = e;
                }
            }
        }
        return bestTarget;
    }

    findTurretTarget(entities, weapon) {
        let bestScore = Infinity;
        let bestTarget = null;
        let wType = weapon.type || '';
        
        let prefersSmall = wType.includes('laser_cannon') || wType.includes('anti_fighter') || this.template.class === 'Small';
        let prefersBig = wType.includes('turbolaser') || wType.includes('torp') || wType.includes('bomb') || wType.includes('missile');

        for (let e of entities) {
            if (e.side !== this.side && e.hp > 0) {
                let d = Math.hypot(e.x - this.x, e.y - this.y);
                if (d > weapon.range + 300) continue; 
                
                let isSmall = e.template.class === 'Small';
                let score = d; 

                if (prefersSmall && !isSmall) score += 5000;
                if (prefersBig && isSmall) score += 5000;

                if (score < bestScore) {
                    bestScore = score;
                    bestTarget = e;
                }
            }
        }
        return bestTarget;
    }

    fireTurret(t, startX, startY, distToTarget) {
        let w = t.weapon;
        let bCount = w.barrels || 1;
        
        let dynamicSpread = w.spread;
        if (t.target && t.target.template.class === 'Small' && w.type.includes('turbolaser')) {
            dynamicSpread *= 3.0; 
        }

        for(let i=0; i < bCount; i++) {
            let spreadOffset = (Math.random() - 0.5) * dynamicSpread;
            let finalAngle = t.angle + spreadOffset;
            
            let latOffset = 0;
            if (bCount > 1) {
                latOffset = (i - (bCount-1)/2) * 2; 
            }
            
            let spawnX = startX + Math.cos(t.angle + Math.PI/2) * latOffset;
            let spawnY = startY + Math.sin(t.angle + Math.PI/2) * latOffset;

            projectiles.push(new Projectile(spawnX, spawnY, finalAngle, w, this.side));
        }
    }

    launchWing(entities) {
        let squadId = Math.random().toString(36).substr(2, 9);
        for(let i=0; i<3; i++) {
            if (this.hangarQueue.length === 0) break;
            let templateId = this.hangarQueue.shift(); 
            let fTemp = templates.find(t => t.id === templateId);
            if(fTemp) {
                let f = new Ship(this.x + (Math.random()-0.5)*50, this.y + (Math.random()-0.5)*50, this.side, fTemp, squadId);
                entities.push(f);
            }
        }
    }

    takeDamage(amt) {
        if (this.shields > 0) {
            if (this.shields >= amt) { this.shields -= amt; amt = 0; } 
            else { amt -= this.shields; this.shields = 0; }
        }
        this.hp -= amt;
    }

    draw(ctx) {
        ctx.save();
        ctx.translate(this.x, this.y);

        // Support Cruiser Aura
        if (this.template.subclass === 'Support') {
            ctx.save();
            ctx.globalAlpha = 0.15 + Math.sin(performance.now() * 0.003) * 0.05;
            ctx.fillStyle = this.side === 1 ? '#00ffff' : '#ff4444';
            ctx.beginPath(); ctx.arc(0, 0, 400, 0, Math.PI * 2); ctx.fill();
            ctx.restore();
        }

        ctx.rotate(this.angle);

        if (this.selected) {
            ctx.strokeStyle = 'rgba(255,255,255,0.7)';
            ctx.lineWidth = 1;
            ctx.beginPath();
            ctx.arc(0, 0, this.template.size + 8, 0, Math.PI * 2);
            ctx.stroke();
        }

        // Faction Colors & Palettes (Gunship gets darker paint)
        let baseColor = this.side === 1 ? '#d6dbdf' : '#4a4a4a'; 
        let darkColor = this.side === 1 ? '#a8b0b8' : '#2b2b2b';
        if (this.template.id === 'cap_gun_mk1') {
            baseColor = this.side === 1 ? '#a0a0a0' : '#331111';
        }

        let stripeColor = this.side === 1 ? '#1e88e5' : '#e53935';
        let engineColor = this.side === 1 ? '#00e5ff' : '#ff7043';
        
        let s = this.template.size;
        let shape = this.template.shape;

        ctx.fillStyle = baseColor;
        ctx.strokeStyle = darkColor;
        ctx.lineWidth = 1;

        if (shape === 'interceptor') {
            ctx.beginPath(); ctx.moveTo(s, 0); ctx.lineTo(-s, s*0.6); ctx.lineTo(-s, -s*0.6); ctx.fill(); ctx.stroke();
            ctx.fillStyle = darkColor; ctx.beginPath(); ctx.moveTo(s*0.5, 0); ctx.lineTo(-s*0.5, s*0.2); ctx.lineTo(-s*0.5, -s*0.2); ctx.fill();
            ctx.fillStyle = engineColor; ctx.fillRect(-s, -1, 2, 2); 
        } 
        else if (shape === 'xwing') {
            ctx.fillRect(-s*0.6, -1, s*1.4, 2); 
            ctx.beginPath();
            ctx.moveTo(-s*0.2, 0); ctx.lineTo(-s*0.5, s*0.8); ctx.lineTo(-s*0.7, s*0.8); ctx.lineTo(-s*0.4, 0);
            ctx.moveTo(-s*0.2, 0); ctx.lineTo(-s*0.5, -s*0.8); ctx.lineTo(-s*0.7, -s*0.8); ctx.lineTo(-s*0.4, 0);
            ctx.fill(); ctx.stroke();
            ctx.fillStyle = stripeColor; ctx.fillRect(s*0.2, -1, s*0.3, 2);
            ctx.fillStyle = engineColor; ctx.fillRect(-s*0.7, s*0.6, 2, 1.5); ctx.fillRect(-s*0.7, -s*0.6 - 1.5, 2, 1.5);
        } 
        else if (shape === 'ywing') {
            ctx.beginPath(); ctx.moveTo(s*0.6, 0); ctx.lineTo(s*0.3, s*0.15); ctx.lineTo(s*0.3, -s*0.15); ctx.fill(); ctx.stroke();
            ctx.fillStyle = darkColor; ctx.fillRect(s*0.3, -1, s*0.15, 2); 
            ctx.fillStyle = baseColor; ctx.fillRect(s*0.1, -1, s*0.3, 2); 
            ctx.fillRect(-s*0.2, -s*0.4, s*0.3, s*0.8); 
            ctx.fillRect(-s*1.2, -s*0.4, s*1.2, s*0.2); 
            ctx.fillRect(-s*1.2, s*0.2, s*1.2, s*0.2); 
            ctx.fillStyle = stripeColor; ctx.fillRect(s*0.4, -1, 2, 2);
            ctx.fillStyle = engineColor; ctx.fillRect(-s*1.2, -s*0.35, s*0.1, s*0.1); ctx.fillRect(-s*1.2, s*0.25, s*0.1, s*0.1);
        } 
        else if (shape === 'corvette') {
            ctx.fillStyle = darkColor; ctx.fillRect(-s, -s*0.4, s*0.6, s*0.8); 
            ctx.fillStyle = baseColor; ctx.fillRect(-s*0.4, -s*0.3, s*1.2, s*0.6); ctx.strokeRect(-s*0.4, -s*0.3, s*1.2, s*0.6);
            ctx.fillRect(s*0.8, -s*0.6, s*0.2, s*1.2); ctx.strokeRect(s*0.8, -s*0.6, s*0.2, s*1.2);
            ctx.fillStyle = stripeColor; ctx.fillRect(s*0.8, -s*0.2, s*0.2, s*0.4);
            ctx.fillStyle = engineColor; ctx.fillRect(-s, -s*0.2, 2, s*0.4);
        } 
        else if (shape === 'lfrig') {
            ctx.beginPath(); ctx.moveTo(s*1.4, 0); ctx.lineTo(s*0.2, s*0.2); ctx.lineTo(-s, s*0.15); ctx.lineTo(-s, -s*0.15); ctx.lineTo(s*0.2, -s*0.2); ctx.fill(); ctx.stroke();
            ctx.fillStyle = darkColor; ctx.beginPath(); ctx.moveTo(s*0.8, 0); ctx.lineTo(s*0.2, s*0.1); ctx.lineTo(-s*0.8, s*0.08); ctx.lineTo(-s*0.8, -s*0.08); ctx.lineTo(s*0.2, -s*0.1); ctx.fill();
            ctx.fillStyle = engineColor; ctx.fillRect(-s, -s*0.1, 3, s*0.2);
        } 
        else if (shape === 'hfrig' || shape === 'mfrig' || shape === 'torp_cruiser') {
            ctx.beginPath(); ctx.moveTo(s*1.2, 0); ctx.lineTo(s*0.3, s*0.35); ctx.lineTo(-s*1.1, s*0.4); ctx.lineTo(-s*1.1, -s*0.4); ctx.lineTo(s*0.3, -s*0.35); ctx.fill(); ctx.stroke();
            ctx.fillStyle = darkColor; 
            ctx.fillRect(-s*0.6, -s*0.2, s*1.2, s*0.4); 
            ctx.fillStyle = stripeColor; ctx.fillRect(s*0.3, -s*0.1, s*0.5, s*0.2);
            ctx.fillStyle = engineColor; ctx.fillRect(-s*1.1, -s*0.15, 3, s*0.3);
        } 
        else if (shape === 'support') {
            ctx.beginPath(); ctx.ellipse(0, 0, s*1.0, s*0.5, 0, 0, Math.PI*2); ctx.fill(); ctx.stroke();
            ctx.fillStyle = stripeColor;
            ctx.beginPath(); ctx.arc(0, 0, s*0.2, 0, Math.PI*2); ctx.fill(); 
        }
        else if (shape === 'artillery') {
            ctx.fillStyle = darkColor; ctx.fillRect(0, -s*0.15, s*1.4, s*0.3); 
            ctx.fillStyle = baseColor; 
            ctx.beginPath(); ctx.moveTo(s*0.2, 0); ctx.lineTo(-s*0.4, s*0.5); ctx.lineTo(-s, s*0.4); ctx.lineTo(-s, -s*0.4); ctx.lineTo(-s*0.4, -s*0.5); ctx.fill(); ctx.stroke();
            ctx.fillStyle = engineColor; ctx.fillRect(-s, -s*0.2, 4, s*0.4);
        }
        else if (shape === 'escort_carrier') {
            ctx.fillRect(-s, -s*0.5, s*2, s); ctx.strokeRect(-s, -s*0.5, s*2, s);
            ctx.fillStyle = darkColor; ctx.fillRect(-s*0.8, -s*0.3, s*1.6, s*0.6); 
            ctx.fillStyle = baseColor; ctx.fillRect(-s*0.2, s*0.2, s*0.6, s*0.4); 
            ctx.fillStyle = stripeColor; ctx.fillRect(-s*0.8, -s*0.05, s*1.6, s*0.1); 
            ctx.fillStyle = engineColor; ctx.fillRect(-s, -s*0.4, 3, s*0.8);
        }
        else if (shape === 'ldest') {
            ctx.beginPath(); ctx.moveTo(s*1.3, 0); ctx.lineTo(s*0.2, s*0.6); ctx.lineTo(-s*1.1, s*0.3); ctx.lineTo(-s*0.8, 0); ctx.lineTo(-s*1.1, -s*0.3); ctx.lineTo(s*0.2, -s*0.6); ctx.fill(); ctx.stroke();
            ctx.fillStyle = darkColor; ctx.beginPath(); ctx.moveTo(s*0.8, 0); ctx.lineTo(s*0.4, s*0.3); ctx.lineTo(-s*0.5, s*0.15); ctx.lineTo(-s*0.5, -s*0.15); ctx.lineTo(s*0.4, -s*0.3); ctx.fill();
            ctx.fillStyle = stripeColor; ctx.beginPath(); ctx.moveTo(s*1.2, 0); ctx.lineTo(s*0.9, s*0.1); ctx.lineTo(s*0.9, -s*0.1); ctx.fill();
            ctx.fillStyle = engineColor; ctx.fillRect(-s*0.8, -s*0.15, 3, s*0.3);
        } 
        else if (shape === 'dreadnought') {
            let w = s * 0.4; 
            ctx.beginPath(); ctx.moveTo(s*1.2, 0); ctx.lineTo(s*0.8, w); ctx.lineTo(-s, w); ctx.lineTo(-s, -w); ctx.lineTo(s*0.8, -w); ctx.fill(); ctx.stroke();
            ctx.fillStyle = darkColor; ctx.fillRect(-s*0.8, -w*0.3, s*1.8, w*0.6);
            ctx.fillStyle = baseColor; ctx.fillRect(-s*0.6, -w*0.5, s*0.4, w*1.0); ctx.strokeRect(-s*0.6, -w*0.5, s*0.4, w*1.0);
            ctx.fillStyle = stripeColor; ctx.fillRect(s*0.2, -w*0.3, s*0.2, w*0.6);
            ctx.fillStyle = engineColor; ctx.fillRect(-s, -w*0.6, 4, w*1.2);
        } 
        else if (shape === 'capital') {
            if (this.side === 2) {
                // RED SIDE: Faceted Mon Calamari style
                ctx.beginPath();
                ctx.moveTo(s*1.2, 0);
                ctx.lineTo(s*0.7, s*0.4); ctx.lineTo(-s*0.4, s*0.5); ctx.lineTo(-s*1.0, s*0.3); ctx.lineTo(-s*1.2, 0);
                ctx.lineTo(-s*1.0, -s*0.3); ctx.lineTo(-s*0.4, -s*0.5); ctx.lineTo(s*0.7, -s*0.4);
                ctx.fill(); ctx.stroke();
                ctx.fillStyle = darkColor;
                ctx.beginPath(); ctx.ellipse(-s*0.2, 0, s*0.6, s*0.2, 0, 0, Math.PI*2); ctx.fill();
                ctx.beginPath(); ctx.ellipse(s*0.4, s*0.2, s*0.2, s*0.1, 0, 0, Math.PI*2); ctx.fill();
                ctx.beginPath(); ctx.ellipse(s*0.4, -s*0.2, s*0.2, s*0.1, 0, 0, Math.PI*2); ctx.fill();
                ctx.fillStyle = stripeColor; ctx.beginPath(); ctx.ellipse(s*1.0, 0, s*0.1, s*0.05, 0, 0, Math.PI*2); ctx.fill();
                ctx.fillStyle = engineColor;
                ctx.beginPath(); ctx.arc(-s*1.1, 0, s*0.1, 0, Math.PI*2); ctx.fill();
                ctx.beginPath(); ctx.arc(-s*1.0, s*0.15, s*0.08, 0, Math.PI*2); ctx.fill();
                ctx.beginPath(); ctx.arc(-s*1.0, -s*0.15, s*0.08, 0, Math.PI*2); ctx.fill();
            } else {
                // BLUE SIDE: Tiered Star Destroyer Wedge
                ctx.beginPath(); ctx.moveTo(s*1.3, 0); ctx.lineTo(-s, s*0.7); ctx.lineTo(-s, -s*0.7); ctx.fill(); ctx.stroke();
                ctx.fillStyle = darkColor;
                ctx.beginPath(); ctx.moveTo(s*0.6, 0); ctx.lineTo(-s*0.8, s*0.5); ctx.lineTo(-s*0.8, -s*0.5); ctx.fill();
                ctx.fillStyle = baseColor;
                ctx.fillRect(-s*0.7, -s*0.1, s*1.1, s*0.2);
                ctx.fillRect(-s*0.6, -s*0.25, s*0.2, s*0.5); ctx.strokeRect(-s*0.6, -s*0.25, s*0.2, s*0.5);
                ctx.fillStyle = stripeColor; ctx.fillRect(s*0.8, -s*0.05, s*0.2, s*0.1);
                ctx.fillStyle = engineColor; ctx.fillRect(-s, -s*0.4, 4, s*0.8);
            }
        }
        else if (shape === 'station') {
            // Outer Ring
            ctx.strokeStyle = darkColor;
            ctx.lineWidth = s*0.1;
            ctx.beginPath(); ctx.arc(0, 0, s*0.9, 0, Math.PI*2); ctx.stroke();
            
            // Inner Ring
            ctx.strokeStyle = baseColor;
            ctx.lineWidth = s*0.15;
            ctx.beginPath(); ctx.arc(0, 0, s*0.5, 0, Math.PI*2); ctx.stroke();
            
            // 4 Massive Spokes
            ctx.fillStyle = darkColor;
            ctx.fillRect(-s, -s*0.1, s*2, s*0.2);
            ctx.fillRect(-s*0.1, -s, s*0.2, s*2);
            
            // Habitat blocks on the spokes
            ctx.fillStyle = stripeColor;
            ctx.fillRect(-s*0.8, -s*0.2, s*0.3, s*0.4);
            ctx.fillRect(s*0.5, -s*0.2, s*0.3, s*0.4);
            ctx.fillRect(-s*0.2, -s*0.8, s*0.4, s*0.3);
            ctx.fillRect(-s*0.2, s*0.5, s*0.4, s*0.3);

            // Central Core
            ctx.fillStyle = '#222';
            ctx.beginPath(); ctx.arc(0, 0, s*0.3, 0, Math.PI*2); ctx.fill();
            
            // Glowing energy reactor
            ctx.shadowBlur = 15;
            ctx.shadowColor = engineColor;
            ctx.fillStyle = engineColor;
            ctx.beginPath(); ctx.arc(0, 0, s*0.15, 0, Math.PI*2); ctx.fill();
            ctx.shadowBlur = 0;
            ctx.fillStyle = '#fff';
            ctx.beginPath(); ctx.arc(0, 0, s*0.05, 0, Math.PI*2); ctx.fill();
        }

        ctx.restore();
        
        // --- DRAW VISIBLE MICRO-TURRETS ---
        if (this.template.class === 'Large') {
            this.turrets.forEach(t => {
                ctx.save();
                ctx.translate(this.x, this.y); 
                
                let absX = t.x * Math.cos(this.angle) - t.y * Math.sin(this.angle);
                let absY = t.x * Math.sin(this.angle) + t.y * Math.cos(this.angle);
                ctx.translate(absX, absY);
                ctx.rotate(t.angle);
                
                let wType = t.weapon.type || '';
                let tSize = 1.2; let bLen = 2.5; let bThick = 0.8;
                
                if (wType.includes('heavy')) { tSize = 2.0; bLen = 4.5; bThick = 1.2; }
                else if (wType.includes('med')) { tSize = 1.6; bLen = 3.5; bThick = 1.0; }
                
                ctx.fillStyle = '#222'; 
                ctx.strokeStyle = '#000'; 
                ctx.lineWidth = 0.5;
                ctx.beginPath(); ctx.arc(0, 0, tSize, 0, Math.PI*2); ctx.fill(); ctx.stroke();
                
                ctx.strokeStyle = '#555'; ctx.lineCap = 'round'; ctx.lineWidth = bThick;
                let bCount = t.weapon.barrels || 1;
                for(let i=0; i<bCount; i++) {
                    let lat = bCount > 1 ? (i - (bCount-1)/2) * 1.2 : 0;
                    ctx.beginPath(); ctx.moveTo(0, lat); ctx.lineTo(bLen, lat); ctx.stroke();
                }
                
                ctx.restore();
            });
        }

        // Health/Shield Bars
        if (this.hp < this.template.hp || this.shields > 0) {
            let barW = s * 1.5;
            ctx.fillStyle = 'red'; ctx.fillRect(this.x - barW/2, this.y + s + 10, barW, 3);
            ctx.fillStyle = '#00ff00'; ctx.fillRect(this.x - barW/2, this.y + s + 10, barW * (Math.max(0,this.hp)/this.template.hp), 3);
            if (this.shields > 0) {
                ctx.fillStyle = 'cyan'; ctx.fillRect(this.x - barW/2, this.y + s + 14, barW * (this.shields/this.template.shields), 2);
            }
        }
    }
}

class Projectile {
    constructor(x, y, angle, wep, side) {
        this.x = x; this.y = y;
        this.angle = angle;
        this.vx = Math.cos(angle) * wep.speed;
        this.vy = Math.sin(angle) * wep.speed;
        this.damage = wep.dmg;
        this.side = side;
        this.dist = 0;
        this.maxDist = wep.range;
        
        let t = wep.type.toLowerCase();
        this.isTorp = t.includes('torp') || t.includes('missile') || t.includes('bomb');
        
        if (t.includes('bomb')) {
            this.color = '#a020f0'; 
            this.thickness = 3.5; this.length = 8;
        } else if (t.includes('missile')) {
            this.color = '#e6e68a'; 
            this.thickness = 3.5; this.length = 8;
        } else if (t.includes('torp')) {
            this.color = '#8ab6e6'; 
            this.thickness = 3.5; this.length = 8;
        } else {
            this.color = side === 1 ? '#00ff00' : '#ff0000';
            if (t.includes('heavy_turbo')) { this.thickness = 5; this.length = 25; }
            else if (t.includes('med_turbo')) { this.thickness = 3.5; this.length = 20; }
            else if (t.includes('light_turbo')) { this.thickness = 2.5; this.length = 15; }
            else if (t.includes('laser_cannon')) { this.thickness = 1.8; this.length = 12; }
            else { this.thickness = 1.0; this.length = 8; } 
        }
        
        this.active = true;
        this.exploded = false;
    }
    update(dt) {
        let mx = this.vx * dt;
        let my = this.vy * dt;
        this.x += mx; this.y += my;
        this.dist += Math.hypot(mx, my);
        if (this.dist >= this.maxDist) {
            this.active = false;
            this.exploded = true;
        }
    }
    draw(ctx) {
        ctx.strokeStyle = this.color;
        ctx.shadowBlur = 6;
        ctx.shadowColor = this.color;
        ctx.lineWidth = this.thickness;
        ctx.lineCap = 'round'; 
        
        ctx.beginPath();
        ctx.moveTo(this.x, this.y);
        ctx.lineTo(this.x - Math.cos(this.angle) * this.length, this.y - Math.sin(this.angle) * this.length);
        ctx.stroke();
        ctx.shadowBlur = 0; 
    }
}

class HitMark {
    constructor(x, y) { this.x = x; this.y = y; this.life = 0.15; }
    update(dt) { this.life -= dt; }
    draw(ctx) {
        ctx.fillStyle = `rgba(255, 255, 150, ${this.life / 0.15})`;
        ctx.beginPath(); ctx.arc(this.x, this.y, 6, 0, Math.PI*2); ctx.fill();
    }
}

class Particle {
    constructor(x, y) {
        this.x = x; this.y = y;
        this.vx = (Math.random()-0.5)*100; this.vy = (Math.random()-0.5)*100;
        this.life = 0.3 + Math.random()*0.3;
    }
    update(dt) { this.x += this.vx*dt; this.y += this.vy*dt; this.life -= dt; }
    draw(ctx) { ctx.fillStyle = `rgba(255, 150, 0, ${this.life})`; ctx.beginPath(); ctx.arc(this.x, this.y, 2, 0, Math.PI*2); ctx.fill(); }
}

// ==========================================
// 3. SIMULATION & RENDER LOOP
// ==========================================
const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');
let ships = [];
let projectiles = [];
let particles = [];
let hitMarks = [];

let isRunning = false;
let activeSide = 1;
let spawnCursorTemplate = null;
let lastTime = performance.now();

function gameLoop(time) {
    let dt = (time - lastTime) / 1000;
    lastTime = time;
    if (dt > 0.1) dt = 0.1; 

    canvas.width = canvas.clientWidth;
    canvas.height = canvas.clientHeight;

    updateInput(dt);

    if (isRunning) {
        ships.forEach(s => s.update(dt, ships));
        
        projectiles.forEach(p => {
            p.update(dt);
            if (p.exploded) {
                hitMarks.push(new HitMark(p.x, p.y));
                for(let i=0; i<3; i++) particles.push(new Particle(p.x, p.y));
            } else if (p.active) {
                for(let s of ships) {
                    if (s.side !== p.side && Math.hypot(s.x - p.x, s.y - p.y) < s.template.size) {
                        s.takeDamage(p.damage);
                        p.active = false;
                        hitMarks.push(new HitMark(p.x, p.y));
                        break;
                    }
                }
            }
        });

        particles.forEach(p => p.update(dt));
        hitMarks.forEach(h => h.update(dt));

        projectiles = projectiles.filter(p => p.active);
        particles = particles.filter(p => p.life > 0);
        hitMarks = hitMarks.filter(h => h.life > 0);
        
        for (let i = ships.length - 1; i >= 0; i--) {
            if (ships[i].hp <= 0) {
                let pCount = ships[i].template.class === 'Large' ? 30 : 5;
                for(let j=0; j<pCount; j++) particles.push(new Particle(ships[i].x, ships[i].y));
                ships.splice(i, 1);
            }
        }
    } else {
        ships.forEach(s => {
            let isManualRotating = (s.selected && s.side === activeSide && input.keys['c']);
            if (isManualRotating && s.template.subclass !== 'Station') {
                let dx = input.mouseWorldX - s.x;
                let dy = input.mouseWorldY - s.y;
                let targetAngle = Math.atan2(dy, dx);
                let diff = targetAngle - s.angle;
                while (diff < -Math.PI) diff += Math.PI * 2;
                while (diff > Math.PI) diff -= Math.PI * 2;
                s.angle += Math.sign(diff) * Math.min(Math.abs(diff), s.template.turnRate * 0.1); 
                
                s.turrets.forEach(t => t.angle = s.angle);
                let angleInput = document.getElementById('mod-angle');
                if (angleInput) angleInput.value = (s.angle * 180 / Math.PI).toFixed(0);
            }
        });
    }

    render();
    requestAnimationFrame(gameLoop);
}

function render() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    
    ctx.strokeStyle = '#1a1a1a'; ctx.lineWidth = 1;
    let ox = camera.x % 50, oy = camera.y % 50;
    for(let i=0; i<canvas.width; i+=50) { ctx.beginPath(); ctx.moveTo(i-ox, 0); ctx.lineTo(i-ox, canvas.height); ctx.stroke(); }
    for(let i=0; i<canvas.height; i+=50) { ctx.beginPath(); ctx.moveTo(0, i-oy); ctx.lineTo(canvas.width, i-oy); ctx.stroke(); }

    ctx.save();
    ctx.translate(canvas.width/2, canvas.height/2);
    ctx.scale(camera.zoom, camera.zoom);
    ctx.translate(-camera.x, -camera.y);

    hitMarks.forEach(h => h.draw(ctx));
    projectiles.forEach(p => p.draw(ctx));
    
    let sortedShips = [...ships].sort((a,b) => (a.template.class==='Large' ? -1 : 1));
    sortedShips.forEach(s => s.draw(ctx));
    
    particles.forEach(p => p.draw(ctx));

    if (input.isDraggingLeft) {
        ctx.strokeStyle = 'rgba(66, 165, 245, 0.8)';
        ctx.fillStyle = 'rgba(66, 165, 245, 0.2)';
        let w = input.mouseWorldX - input.dragStartX;
        let h = input.mouseWorldY - input.dragStartY;
        ctx.fillRect(input.dragStartX, input.dragStartY, w, h);
        ctx.strokeRect(input.dragStartX, input.dragStartY, w, h);
    }

    if (spawnCursorTemplate) {
        ctx.globalAlpha = 0.5;
        ctx.fillStyle = activeSide === 1 ? '#42a5f5' : '#ef5350';
        
        ctx.save();
        ctx.translate(input.mouseWorldX, input.mouseWorldY);
        ctx.rotate(activeSide === 2 ? Math.PI : 0);
        ctx.beginPath(); ctx.arc(0, 0, spawnCursorTemplate.size * 2, 0, Math.PI*2); ctx.fill();
        ctx.restore();
        
        ctx.globalAlpha = 1.0;
    }

    ctx.restore();
}

// ==========================================
// 4. INPUT & CAMERA
// ==========================================
let camera = { x: 0, y: 0, zoom: 1.0 };
let input = { keys: {}, isDraggingLeft: false, dragStartX: 0, dragStartY: 0, mouseWorldX: 0, mouseWorldY: 0 };

window.addEventListener('keydown', e => {
    if (document.activeElement.tagName === 'INPUT') return;

    let k = e.key.toLowerCase();
    if (k === 'arrowup') k = 'w';
    if (k === 'arrowdown') k = 's';
    if (k === 'arrowleft') k = 'a';
    if (k === 'arrowright') k = 'd';
    
    input.keys[k] = true;
    
    if (k === '1') { e.preventDefault(); setActiveSide(1); }
    if (k === '2') { e.preventDefault(); setActiveSide(2); }
    
    if (k === 'delete' || k === 'backspace') { 
        ships = ships.filter(s => !s.selected); 
        updateInspector(); 
    }
});

window.addEventListener('keyup', e => {
    let k = e.key.toLowerCase();
    if (k === 'arrowup') k = 'w';
    if (k === 'arrowdown') k = 's';
    if (k === 'arrowleft') k = 'a';
    if (k === 'arrowright') k = 'd';
    input.keys[k] = false;
});

canvas.addEventListener('contextmenu', e => e.preventDefault());

canvas.addEventListener('mousemove', e => {
    let rect = canvas.getBoundingClientRect();
    input.mouseWorldX = ((e.clientX - rect.left) - canvas.width/2) / camera.zoom + camera.x;
    input.mouseWorldY = ((e.clientY - rect.top) - canvas.height/2) / camera.zoom + camera.y;
});

canvas.addEventListener('mousedown', e => {
    if (e.button === 0) { 
        if (spawnCursorTemplate) {
            let squadId = Math.random().toString(36).substr(2, 9);
            for(let i=0; i<spawnCursorTemplate.formation; i++) {
                let offsetX = (Math.random()-0.5) * 60;
                let offsetY = (Math.random()-0.5) * 60;
                ships.push(new Ship(input.mouseWorldX + offsetX, input.mouseWorldY + offsetY, activeSide, spawnCursorTemplate, squadId));
            }
            return;
        }
        input.isDraggingLeft = true;
        input.dragStartX = input.mouseWorldX;
        input.dragStartY = input.mouseWorldY;
    } else if (e.button === 2) { 
        if (spawnCursorTemplate) {
            spawnCursorTemplate = null;
            buildPalette();
            return;
        }

        let clickedShip = ships.find(s => Math.hypot(s.x - input.mouseWorldX, s.y - input.mouseWorldY) < s.template.size + 15);
        ships.forEach(s => {
            if (s.selected && s.side === activeSide) {
                if (clickedShip && clickedShip.side !== activeSide) {
                    s.drivingTarget = clickedShip; s.waypoint = null;
                    s.turrets.forEach(t => t.target = clickedShip);
                } else {
                    s.waypoint = {x: input.mouseWorldX + (Math.random()-0.5)*30, y: input.mouseWorldY + (Math.random()-0.5)*30};
                    s.drivingTarget = null;
                }
            }
        });
    }
});

canvas.addEventListener('mouseup', e => {
    if (e.button === 0 && input.isDraggingLeft) {
        input.isDraggingLeft = false;
        let minX = Math.min(input.dragStartX, input.mouseWorldX);
        let maxX = Math.max(input.dragStartX, input.mouseWorldX);
        let minY = Math.min(input.dragStartY, input.mouseWorldY);
        let maxY = Math.max(input.dragStartY, input.mouseWorldY);

        let clickSelect = Math.abs(maxX - minX) < 5 && Math.abs(maxY - minY) < 5;
        let selectedSquads = new Set();

        ships.forEach(s => {
            if (s.side !== activeSide) { s.selected = false; return; }
            let inBox = false;
            if (clickSelect) {
                inBox = Math.hypot(s.x - input.mouseWorldX, s.y - input.mouseWorldY) < s.template.size + 10;
            } else {
                inBox = (s.x >= minX && s.x <= maxX && s.y >= minY && s.y <= maxY);
            }
            
            s.selected = inBox;
            if (inBox && s.squadId) selectedSquads.add(s.squadId);
        });

        ships.forEach(s => {
            if (s.side === activeSide && selectedSquads.has(s.squadId)) {
                s.selected = true;
            }
        });

        updateInspector();
    }
});

canvas.addEventListener('wheel', e => {
    e.preventDefault();
    camera.zoom *= (e.deltaY < 0 ? 1.1 : 0.9);
    camera.zoom = Math.max(0.1, Math.min(camera.zoom, 4.0));
});

function updateInput(dt) {
    let camSpeed = 500 / camera.zoom;
    if (input.keys['w']) camera.y -= camSpeed * dt;
    if (input.keys['s']) camera.y += camSpeed * dt;
    if (input.keys['a']) camera.x -= camSpeed * dt;
    if (input.keys['d']) camera.x += camSpeed * dt;
}

// ==========================================
// 5. UI BINDINGS & MODDING
// ==========================================
const btnStart = document.getElementById('btn-start');
const phaseInd = document.getElementById('phase-indicator');
const sideInd = document.getElementById('side-indicator');
const palette = document.getElementById('palette-list');
const inspector = document.getElementById('inspector');
const sidePanel = document.querySelector('.active-side-panel');

btnStart.addEventListener('click', () => {
    isRunning = !isRunning;
    btnStart.innerText = isRunning ? "Pause Simulation" : "Start Simulation";
    btnStart.className = isRunning ? "running" : "";
    phaseInd.innerText = isRunning ? "Phase: RUNNING" : "Phase: SETUP";
});

function setActiveSide(side) {
    activeSide = side;
    sideInd.innerText = `Controlling: SIDE ${side} (${side === 1 ? 'Blue' : 'Red'})`;
    sidePanel.style.borderLeftColor = side === 1 ? '#42a5f5' : '#ef5350';
    ships.forEach(s => s.selected = false);
    spawnCursorTemplate = null;
    buildPalette();
    updateInspector();
}

function buildPalette() {
    palette.innerHTML = '';
    templates.forEach(t => {
        let div = document.createElement('div');
        div.className = 'palette-item' + (spawnCursorTemplate === t ? ' selected' : '');
        div.innerText = `+ ${t.name} (${t.formation}x)`;
        div.onclick = () => { spawnCursorTemplate = t; buildPalette(); }
        palette.appendChild(div);
    });
}

function updateInspector() {
    let selectedShips = ships.filter(s => s.selected);
    if (selectedShips.length === 0) {
        inspector.innerHTML = `<p>Select a ship/squad on the map to edit its template.</p>`;
        return;
    }
    
    let ship = selectedShips[0];
    let t = ship.template;
    
    let html = `<h4>Editing: ${t.name}</h4>
                <div style="margin-bottom: 8px;">
                    <label title="You can also select the ship, hold 'C', and move your mouse to rotate it!" style="color:#00e5ff;">HEADING DEGREES (Hold 'C' & Mouse to aim)</label>
                    <input type="number" id="mod-angle" value="${(ship.angle * 180 / Math.PI).toFixed(0)}">
                </div>`;

    html += `<div style="display: grid; grid-template-columns: 1fr 1fr; gap: 4px;">`;
    const fields = ['hp', 'shields', 'speed', 'turnRate'];
    fields.forEach(f => {
        html += `<div><label>${f.toUpperCase()}</label><input type="number" id="mod-${f}" value="${t[f]}"></div>`;
    });
    html += `</div>`;
    
    if (t.complement) {
        html += `<details style="margin-top: 10px; background: #1a1a1a; padding: 5px; border-radius: 4px;">
                    <summary style="cursor: pointer; font-size: 13px; font-weight: bold; color: #aaa;">Hangar Complement</summary>
                    <div style="display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 4px; margin-top: 5px;">
                        <div><label>INT</label><input type="number" id="mod-comp-int" value="${t.complement.int}"></div>
                        <div><label>FTR</label><input type="number" id="mod-comp-ftr" value="${t.complement.ftr}"></div>
                        <div><label>BMB</label><input type="number" id="mod-comp-bmb" value="${t.complement.bmb}"></div>
                    </div>
                 </details>`;
    }

    html += `<details style="margin-top: 10px; background: #1a1a1a; padding: 5px; border-radius: 4px;">
                <summary style="cursor: pointer; font-size: 13px; font-weight: bold; color: #aaa;">Weapons Array (${t.weapons.length})</summary>
                <div style="max-height: 300px; overflow-y: auto; padding-right: 5px; margin-top: 5px;">`;
    
    t.weapons.forEach((w, i) => {
        html += `<div style="margin-bottom: 8px; border-left: 2px solid #555; padding-left: 6px;">
                    <label style="color: #fff; display: block; margin-bottom: 4px;">[${i+1}] ${w.type.toUpperCase()}</label>
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 4px;">`;
        const wepFields = ['dmg', 'cooldown', 'range', 'speed'];
        wepFields.forEach(f => {
            html += `<div><label style="font-size: 10px;">${f}</label><input type="number" step="0.05" id="mod-wep-${i}-${f}" value="${w[f]}" style="padding: 2px;"></div>`;
        });
        html += `</div></div>`;
    });
    
    html += `</div></details>`;

    html += `<button id="btn-apply-mod" style="margin-top:15px; background: #004d40;">Apply to Selected</button>`;
    html += `<button id="btn-save-template" style="background: #424242;">Save as Global Template</button>`;
    
    inspector.innerHTML = html;

    document.getElementById('btn-apply-mod').onclick = () => {
        fields.forEach(f => t[f] = parseFloat(document.getElementById(`mod-${f}`).value));
        
        if (t.complement) {
            t.complement.int = parseInt(document.getElementById('mod-comp-int').value);
            t.complement.ftr = parseInt(document.getElementById('mod-comp-ftr').value);
            t.complement.bmb = parseInt(document.getElementById('mod-comp-bmb').value);
        }

        t.weapons.forEach((w, i) => {
            ['dmg', 'cooldown', 'range', 'speed'].forEach(f => w[f] = parseFloat(document.getElementById(`mod-wep-${i}-${f}`).value));
        });
        
        let newAngle = parseFloat(document.getElementById('mod-angle').value) * Math.PI / 180;

        selectedShips.forEach(s => {
            s.hp = t.hp; s.shields = t.shields;
            s.angle = newAngle; 
            s.turrets.forEach(tur => tur.angle = newAngle);
        });
    };

    document.getElementById('btn-save-template').onclick = () => {
        document.getElementById('btn-apply-mod').click();
        let existingIdx = templates.findIndex(x => x.id === t.id);
        if (existingIdx !== -1) templates[existingIdx] = JSON.parse(JSON.stringify(t));
        else templates.push(JSON.parse(JSON.stringify(t)));
        buildPalette();
        alert("Template updated!");
    };
}

document.getElementById('btn-export').onclick = () => {
    const blob = new Blob([JSON.stringify(templates, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url; a.download = 'stellar_clash_templates.json';
    a.click(); URL.revokeObjectURL(url);
};

document.getElementById('btn-import').onchange = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = (event) => {
        try { templates = JSON.parse(event.target.result); buildPalette(); alert("Templates loaded successfully!"); } 
        catch(err) { alert("Invalid JSON file."); }
    };
    reader.readAsText(file);
};

buildPalette();
requestAnimationFrame(gameLoop);

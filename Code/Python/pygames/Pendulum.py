import pygame as pg
from math import sin, cos, pi

class Pendulum:
    def __init__ (self, length : float, theta : float, omega : float, color : pg.color, origin : pg.Vector2, mass: float):
        self.length = length
        self.theta = theta
        self.omega = omega
        self.color = color
        self.origin = origin
        self.mass = mass
        self.position = pg.Vector2(origin.x + length*sin(theta), origin.y + length*cos(theta))

    def update(self, dt : float):
        self.omega += -9.81 * sin(self.theta) / self.length * dt
        self.theta += self.omega*dt
        self.position = pg.Vector2(self.origin.x + self.length*sin(self.theta), self.origin.y + self.length*cos(self.theta))

def main():
    resolution = (700,700)
    pg.init()
    canvas = pg.display.set_mode(resolution)
    screen = pg.Surface(canvas.get_size())
    p = Pendulum(100,pi/4,0,pg.Color("#FF6B35"),pg.Vector2(360,360),1)

    while True:
        for event in pg.event.get():
            if event.type == pg.QUIT:
                pg.quit()
                quit()
        screen.fill(pg.Color("#000000"))
        p.update(1.0/60.0)
        pg.draw.line(screen, pg.Color("#004E89"), p.origin, p.position, 5)
        pg.draw.circle(screen, p.color, p.position, 10)
        canvas.blit(screen, (0,0))
        pg.display.flip()

main()
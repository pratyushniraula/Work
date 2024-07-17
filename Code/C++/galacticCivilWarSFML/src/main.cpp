#include <SFML/Graphics.hpp>
#include <vector>
#include <iostream>
#include <cmath>
#include <chrono>

// Forward declaration of classes
class Projectile;
class SpaceShip;

// Definition of Projectile class
class Projectile {
public:
    sf::CircleShape shape;
    sf::Vector2f velocity;
    int damage;

    Projectile(float x, float y, float radius, sf::Vector2f velocity, int damage)
        : shape(radius), velocity(velocity), damage(damage) {
        shape.setPosition(x, y);
        shape.setFillColor(sf::Color::Red);
    }

    void update() {
        shape.move(velocity);
    }
};

// Definition of SpaceShip class
class SpaceShip {
public:
    
};

int main() {
    sf::RenderWindow window(sf::VideoMode(800, 600), "Galactic Rebellion - Space Battle");
    window.setFramerateLimit(60);

//    std::vector<SpaceShip> ships;
//    ships.push_back(SpaceShip()); // Friendly ship
//    ships.push_back(SpaceShip()); // Enemy ship

    std::vector<Projectile> projectiles;

    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed) {
                window.close();
            }

            if (event.type == sf::Event::KeyPressed) {
                if (event.key.code == sf::Keyboard::Space) {
                    projectiles.push_back(Projectile(400, 300, 5, sf::Vector2f(0, -5), 10));
                }
            }
        }
       

        window.clear();

        

        window.display();
    }

    return 0;
}

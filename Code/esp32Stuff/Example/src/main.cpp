#include <Arduino.h>

const int ledPin = 2;  // Define LED pin
int x = 10;            // Initialize variable

void setup() {
  // setup pin 2 as a digital output pin
  pinMode(ledPin, OUTPUT);
  Serial.begin(115200);  // Initialize serial communication at 9600 baud
}

void loop() {
  if(x > 0) {
    Serial.println("Hello World");  // Print "Hello World" when x > 0
    digitalWrite(ledPin, HIGH);    // Turn on the LED
    delay(500);                    // Wait for 500 milliseconds
    digitalWrite(ledPin, LOW);     // Turn off the LED
    delay(500);                    // Wait for 500 milliseconds
    x--;                           // Decrement x
  } 
  else {
    digitalWrite(ledPin, HIGH);    // Turn on the LED
    delay(100);                    // Blink quickly when x <= 0
    digitalWrite(ledPin, LOW);     // Turn off the LED
    delay(100);
    Serial.println("Counter is less than or equal to zero.");  // Message for else case
  }
}

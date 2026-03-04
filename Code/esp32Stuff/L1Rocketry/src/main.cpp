#include <Arduino.h>      // Arduino framework
#include "FS.h"           // Filesystem library
#include "SD.h"           // SD card library
#include "SPI.h"          // SPI communication for SD card
#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BMP280.h>

Adafruit_BMP280 bmp; // Create an instance of the BMP280 sensor
int x = 0;
// Sea level pressure in hPa (can be adjusted for more accurate altitude readings)
#define SEALEVELPRESSURE_HPA (1013.25)

//void setup()
void setup() {
  Serial.begin(115200);

  // Initialize the BMP280 sensor
  if (!bmp.begin(0x76)) {  // 0x76 is the default I2C address for the BMP280
    Serial.println("Could not find a valid BMP280 sensor, check wiring!");
    while (1);
  }
  x = 0;
  // Set up oversampling for more precise readings
  bmp.setSampling(Adafruit_BMP280::MODE_NORMAL,     // Operating Mode
                  Adafruit_BMP280::SAMPLING_X2,     // Temp. oversampling
                  Adafruit_BMP280::SAMPLING_X16,    // Pressure oversampling
                  Adafruit_BMP280::FILTER_X16,      // Filtering
                  Adafruit_BMP280::STANDBY_MS_500); // Standby time

    pinMode(2, OUTPUT); //initialize led
    //initialize stopwatch
    //start stopwatch
}

//void loop()
void loop() {

    long time = (millis())/1000; // Get the current time in milliseconds
  // Get temperature in Celsius
  float temperature = bmp.readTemperature(); // Get temperature in Celsius

  // Get pressure in hPa
  float pressure = bmp.readPressure() / 100.0F; // Convert to hPa (from Pa)

  // Calculate altitude in meters
  float altitude = bmp.readAltitude(SEALEVELPRESSURE_HPA); // Calculate altitude in meters

  // Initialize the SPI communication for the SD card
    SPI.begin();
    // Initialize the SD card
    if (!SD.begin(5)) {
        Serial.println("Card Mount Failed");
        return;
    }

    // Check SD card size and details
    uint8_t cardType = SD.cardType();
    if (cardType == CARD_NONE) {
        Serial.println("No SD card attached");
        return;
    }


    //File file = SD.open("/temperature.csv", FILE_APPEND);
    //open file on sd card to write or append data but truncate if file exists
      Serial.println("x=" + String(x));
    if(x == 0){
      File file = SD.open("/temperature.csv", FILE_WRITE);
      if(file){file.close();}
      file = SD.open("/temperature.csv", FILE_APPEND);

      File file2 = SD.open("/pressure.csv", FILE_WRITE);
      if(file2){file2.close();}
      file2 = SD.open("/pressure.csv", FILE_APPEND);

      File file3 = SD.open("/altitude.csv", FILE_WRITE);
      if(file3){file3.close();}
      file3 = SD.open("/altitude.csv", FILE_APPEND);

      //print identifiers in files
      file.println("Time (s),Temperature (°C)");
      file2.println("Time (s),Pressure (hPa)");
      file3.println("Time (s),Altitude (m)");

      file.close();
      file2.close();
      file3.close();
    }
    x = 1;

  //open or create file on sd card to write or append data
    // File file = SD.open("/temperature.txt", FILE_APPEND);
    // File file2 = SD.open("/pressure.txt", FILE_APPEND);
    // File file3 = SD.open("/altitude.txt", FILE_APPEND);
    File file = SD.open("/temperature.csv", FILE_APPEND);
    File file2 = SD.open("/pressure.csv", FILE_APPEND);
    File file3 = SD.open("/altitude.csv", FILE_APPEND);
    
    Serial.println("Writing to file...");

    Serial.println(time);
    
    // Print the results to the serial monitor
    Serial.print("Temperature = ");
    Serial.print(temperature);
    Serial.println(" °C");
    //print on file
    // file.print("Time = ");
    // file.print(time);
    // file.print(" ");
    // file.print("Temperature = ");
    // file.print(temperature);
    // file.println(" °C");

    //print like csv
    file.print(time);
    file.print(",");
    file.println(temperature);

    // Print the results to the serial monitor
    Serial.print("Pressure = ");
    Serial.print(pressure);
    Serial.println(" hPa");
    //print on file
    // file2.print("Time = ");
    // file2.print(time);
    // file2.print(" ");
    // file2.print("Pressure = ");
    // file2.print(pressure);
    // file2.println(" hPa");

    //print like csv
    file2.print(time);
    file2.print(",");
    file2.println(pressure);

    // Print the results to the serial monitor
    Serial.print("Approx. Altitude = ");
    Serial.print(altitude);
    Serial.println(" m");
    //print on file
    // file3.print("Time = ");
    // file3.print(time);
    // file3.print(" ");
    // file3.print("Approx. Altitude = ");
    // file3.print(altitude);
    // file3.println(" m");
    
    //print like csv
    file3.print(time);
    file3.print(",");
    file3.println(altitude);

    // Close the file to ensure data is saved
    file.close();
    file2.close();
    file3.close();

    //turn on led to indicate data is written
    digitalWrite(2, HIGH);
    // Wait for 2 seconds before taking another reading
    delay(2000);

    //turn off led
    digitalWrite(2, LOW);
}
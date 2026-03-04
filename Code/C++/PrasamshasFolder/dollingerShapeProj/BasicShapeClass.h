#ifndef BASICSHAPECLASS_H
#define BASICSHAPECLASS_H

#include <iostream>
using namespace std;

class BasicShapeClass {
protected:
    double area;

public:
    BasicShapeClass() : area(0) {}

    double getArea() const { return area; }
    void setArea(double a) { area = a; }

    virtual double calculateArea() = 0; // Pure virtual function
    virtual void display() const = 0;   // Pure virtual function

    virtual ~BasicShapeClass() {}
};

#endif
#ifndef CIRCLESHAPESUBCLASS_H
#define CIRCLESHAPESUBCLASS_H

#include "BasicShapeClass.h"
#include "LineInfo.h"

class CircleShapeSubClass : public BasicShapeClass {
private:
    const double PI = 3.14159;
    double radius;

public:
    CircleShapeSubClass(double r) : radius(r) {
        if (radius < 0) 
            throw domain_error(LineInfo("Radius less than zero", __FILE__, __LINE__));
        area = calculateArea();
    }

    double calculateArea() override {
        return PI * radius * radius;
    }

    void display() const override {
        cout << "Shape is a Circle:" << endl;
        cout << " Radius : " << radius << endl;
        cout << " Area   : " << area << endl;
    }
};

#endif

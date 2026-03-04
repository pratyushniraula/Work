#ifndef TRIANGLESHAPESUBCLASS_H
#define TRIANGLESHAPESUBCLASS_H

#include "BasicShapeClass.h"
#include "LineInfo.h"

class TriangleShapeSubClass : public BasicShapeClass {
private:
    double height;
    double base;

public:
    TriangleShapeSubClass(double h, double b) : height(h), base(b) {
        if (height < 0 || base < 0)
            throw domain_error(LineInfo("Height or Base less than zero", __FILE__, __LINE__));
        area = calculateArea();
    }

    double calculateArea() override {
        return 0.5 * base * height;
    }

    void display() const override {
        cout << "Shape is a Triangle:" << endl;
        cout << " Height : " << height << endl;
        cout << " Base   : " << base << endl;
        cout << " Area   : " << area << endl;
    }
};

#endif
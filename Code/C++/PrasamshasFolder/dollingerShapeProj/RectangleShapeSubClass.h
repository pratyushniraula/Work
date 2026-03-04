#ifndef RECTANGLESHAPESUBCLASS_H
#define RECTANGLESHAPESUBCLASS_H

#include "BasicShapeClass.h"
#include "LineInfo.h"

class RectangleShapeSubClass : public BasicShapeClass {
private:
    double width;
    double length;

public:
    RectangleShapeSubClass(double w, double l) : width(w), length(l) {
        if (width < 0 || length < 0)
            throw domain_error(LineInfo("Width or Length less than zero", __FILE__, __LINE__));
        area = calculateArea();
    }

    double calculateArea() override {
        return width * length;
    }

    void display() const override {
        cout << "Shape is a Rectangle:" << endl;
        cout << " Width  : " << width << endl;
        cout << " Length : " << length << endl;
        cout << " Area   : " << area << endl;
    }
};

#endif
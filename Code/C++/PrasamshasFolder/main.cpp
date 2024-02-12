#include <iostream>

using namespace std;

int main(){
	const double AREA = 14.125;
	const double PI = 3.14159;
	cout << "how many people are in your party" << endl;

	int psize;
	cin >> psize;

	cout << "make the diameter of the pizzas you want to order" << endl;
	double diameter;
	cin >> diameter;

	int totalSlices = psize * 4;

	double radius = diameter/2;
	
	double areapPizza = radius*radius*PI;
	
	double TslicespPizza = areapPizza/AREA;

	double totalPies = totalSlices/TslicespPizza;
	
	cout << "you would need " << totalPies << "pies of pizzas for your party";



	return 08;
}

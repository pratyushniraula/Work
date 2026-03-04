//
// ========================================
// HW3: Implement a Vector Class
// ========================================
// Add comments, which count 10% of the grading. 
// In this assignment, we will implement 
// a vector class based on array, pointer 
// and dynamic memory allocation. 
// 
// The class has two private member 
// variables and some member functions. 
// Their designs are explained below. 
// 
// You can add new member functions 
// but not new member variables. In 
// addition, you cannot use any existing 
// vector-related library or function. 
// 
// 


#include <iostream>
using namespace std;

// 
// ======= Task =======
// Design and implement all member 
// functions of the MyVector class. 
// 
class MyVector {

public:

	// The "vsize" function returns the size 
	// of the current vector.  
	int vsize(){
		// Simply return the size member variable
		return size;
	}

	// The "empty" function returns 1 if the 
	// current vector has no element and 0 otherwise. 
	int empty(){
		// Check if size is 0
		if (size == 0) {
			return 1;
		}
		// If size is not 0, return 0
		return 0;
	}

	// The "at" function returns the idx_th element 
	// of the vector. It also returns -1 if "idx" 
	// is outside the range of the current vector.
	int at(int idx){
		// Check if idx is out of bounds
		if (idx < 0 || idx >= size) {
			return -1;
		}
		// If idx is valid,
		return p[idx];
	}

	// The "resize" function resizes the current 
	// vector into a vector of size n. Note there 
	// are two scenarios (in lecture slides) which 
	// may need to be addressed separately. 
	void resize(int n){
		// Do nothing
		if (n < 0) {
			return;
		}
		// If n is 0, we need to delete the current array
		if (n == 0) {
			delete[] p;
			p = nullptr;
			size = 0;
			return;
		}
		// Create a new array of size n
		int* PP = new int[n];
		// Copy old values to new array
		for (int i = 0; i < (n < size ? n : size); i++) {
			PP[i] = p[i];
		}
		//delete old array set the size to n and point p to new array
		delete[] p;
		p = PP;
		size = n;
	}

	// The "push_back" function adds a new element 
	// "x" to the end of the current vector. Note 
	// the vector size increases after addition. 
	void push_back(int x){
		// Create a new array of size + 1
		int* PP = new int[size + 1];
		// Copy old values to new array
		for (int i = 0; i < size; i++) {
			PP[i] = p[i];
		}
		// Add new value to the end
		PP[size] = x;
		// Update pointer and size
		delete[] p;
		// Point p to the new array and increase size
		p = PP;
		size++;
	}

	// The "pop_back" function removes the last 
	// element from the current vector, but does 
	// nothing if the current vector is empty. 
	// Note the vector size decreases after pop. 
	void pop_back(){
		// Do nothing if the vector is empty
		if (size == 0) {
			return;
		}

		int* PP = new int[size - 1];
		for (int i = 0; i < size - 1; i++) {
			PP[i] = p[i];
		}
		delete[] p;
		p = PP;
		size--;
	}

	// The "insert" function inserts a new element 
	// "x" as the idx_th element in the current vector.
	// It also does nothing if "idx" is outside the 
	// range of the current vector. 
	void insert(int idx, int x){
		// Do nothing if idx is out of bounds
		if (idx < 0 || idx > size) {
			return;
		}
		// Create a new array of size + 1
		int* PP = new int[size + 1];
		// Copy old values to new array and insert new value at idx
		for (int i = 0; i < idx; i++) {
			PP[i] = p[i];
		}
		// Insert new value at idx
		PP[idx] = x;
		// Copy the rest of the old values
		for (int i = idx; i < size; i++) {
			PP[i + 1] = p[i];
		}
		// Update pointer and size and delete old array
		delete[] p;
		p = PP;
		size++;
	}

	// The "erase" function removes the idx_th element 
	// from the vector. It also does nothing if the 
	// current vector is empty or if "idx" is outside 
	// the range of the current vector. 
	void erase(int idx){
		// Do nothing if vector is empty or idx is out of bounds
		if (idx < 0 || idx >= size) return;
		// Create a new array of size - 1
		int* PP = new int[size - 1];
		// Copy old values to new array except the one at idx
		for (int i = 0; i < idx; i++) {
			PP[i] = p[i];
		}
		// Copy the rest of the old values
		for (int i = idx + 1; i < size; i++) {
			PP[i - 1] = p[i];
		}
		// Update pointer and size and delete old array
		delete[] p;
		p = PP;
		size--;
	}

	// The constructor should initialize 
	// pointer "p" to NULL and "size" to 0. 
	MyVector(){
		// Initialize pointer and size
		p = NULL;
		size = 0;
	}

private:

	int* p; // This pointer holds the vector (or,essentially, 
	// address of an array that holds the vector elements.)
	// Do remember to update it after some vector operations.

	int size; // This integer holds the size of the current 
	// vector. Do remember to update it after some 
	// vector operations. 
};


// The main function has been completed for you. 
// It is used to test your implmentation. 
// You should not modify it (unless there is typo).
int main()
{
	MyVector x;

	int mode;
	int new_size, idx, data;
	int temp;

	cin >> mode; // This decides which function to test. 
	cin >> new_size >> idx >> data;

	// Mode 0: test push_back(), vsize() and at()
	if (mode == 0) {

		while (cin >> temp) {
			x.push_back(temp);
		}

		cout << x.vsize() << '\n';

		for (int i = 0; i < x.vsize(); i++) {
			cout << x.at(i) << '\n';
		}
	}
	// Mode 1: test resize()
	else if (mode == 1)
	{
		while (cin >> temp) {
			x.push_back(temp);
		}

		x.resize(new_size);

		cout << x.vsize() << '\n';

		for (int i = 0; i < x.vsize(); i++) {
			cout << x.at(i) << '\n';
		}
	}
	// Mode 2: test pop_back()
	else if (mode == 2) {

		while (cin >> temp) {
			x.push_back(temp);
		}

		x.pop_back();

		cout << x.vsize() << '\n';

		for (int i = 0; i < x.vsize(); i++) {
			cout << x.at(i) << '\n';
		}
	}
	// Mode 3: test insert()
	else if (mode == 3) {

		while (cin >> temp) {
			x.push_back(temp);
		}

		x.insert(idx, data);

		cout << x.vsize() << '\n';

		for (int i = 0; i < x.vsize(); i++) {
			cout << x.at(i) << '\n';
		}
	}
	// Mode 4: test erase()
	else if (mode == 4) {

		while (cin >> temp) {
			x.push_back(temp);
		}

		x.erase(idx);

		cout << x.vsize() << '\n';

		for (int i = 0; i < x.vsize(); i++) {
			cout << x.at(i) << '\n';
		}
	}
	else {
		cout << "Wrong Mode Input!";
	}

	return 0;
}
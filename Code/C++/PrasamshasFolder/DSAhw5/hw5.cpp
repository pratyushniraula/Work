//
// ========================================
// HW6: Implement Recursive Functions of 
//      Binary Search and Merge Sort 
// ========================================
// 
// In this assignment, we will implement 
// a recursive function for binary search 
// based on array, and another recursive 
// function for merge sort based on singly 
// linked list. 
// 
// The Node class (used to construct list) 
// has been defined to you. Do not modify it. 
// 
// Do not define a linked list class for 
// this assignment. In the main function, 
// write a while loop to take inputs and 
// use them to construct a linked list. 
// Then, input this list (its head pointer) 
// into the merge sort function. See the 
// main function for detailed instructions. 
// 
// You cannot use any existing binary search 
// or merge sort related functions or libraries. 
// 
// Here are two extra rules you need to follow:
// 
// 1. The merge sort function should always 
// sort input list in ascending order. 
// 
// 2. For binary search, if there are even number 
// of elements in the list, always pick the mid-1 
// element for comparison (with the search key). 
// 
// Note that there are five tasks in this template.
// 


#include <iostream>
using namespace std;

class Node{
public:
    Node();  // have to add this line - constructor declaration
	void Set_SID(int x);
	int Get_SID();
	void Set_Pnext(Node *p);
	Node * Get_Pnext();
private:
	int SID;
	Node* p_next;
};
void Node::Set_SID(int x) {
	SID = x;
}
int Node::Get_SID() {
	return SID;
}
void Node::Set_Pnext(Node* p) {
	p_next = p;
}
Node* Node::Get_Pnext() {
	return p_next;
}
Node::Node() {
	SID = -1;
	p_next = NULL;
}

//
// ----------------------------
// ---------- Task 1 ----------
// ----------------------------
// 
// Complete the following recursive 
// binary search function. 
// 
// The function takes a list as input, 
// and outputs index of the search key 
// in the list. It returns -1 if the 
// key is not in the list. 
// 
// Input: you can design the input arguments 
// in any way you like. Just don't forget to 
// include a search key which is the key we 
// aim to find. Also keep in mind that, at 
// the end of all recursive calls, function 
// should return the index of a matching key 
// in the input array. For example, if the 
// input array is x = {1, 3, 5, 7, 9} and 
// the search key is 7, this BSearch function 
// should return "3" in the main program 
// because x[3] = 7. Also don't forget to 
// design conditions for a failed search 
// (return -1 in the main program). 
// 
// Important!!
// In every recursion, print out the number 
// that is being compared with the search key. 
// (Do not add any space.) For example, if the 
// list is {1,2,3,4,5,6,7,8,9,10,11} and the 
// search key is 2, then your function should 
// print "6" in the first recursive call, 
// print "3" in the second recursive call, 
// print "1" in the third recursive call 
// and print "2" in the last recursive call. 
// So after BSearch is excuted in the main 
// program, we should have "6312" printed out.
// 
// Do not confuse the above print-out with 
// the function's returned index. In the main 
// program (mode 0), we will also print the 
// returned index -- so printing "63121", 
// where the last "1" is index of 2 in the list. 
// 
// Add this function for array-based binary search
int BSearch(int arr[], int left, int right, int key) {
    if (left > right) {
        return -1;  // Key not found
    }
    
    // For even number of elements, pick mid-1 element
    int mid = left + (right - left) / 2;
    if ((right - left + 1) % 2 == 0) {
        mid = mid - 1;  // Pick mid-1 for even number of elements
    }
    
    cout << arr[mid];  // Print the number being compared
    
    if (arr[mid] == key) {
        return mid;  // Found the key, return index
    } else if (arr[mid] < key) {
        return BSearch(arr, mid + 1, right, key);
    } else {
        return BSearch(arr, left, mid - 1, key);
    }
}

//
// ----------------------------
// ---------- Task 2 ----------
// ----------------------------
// 
// Complete the following recursive merge 
// sort function. The function takes a list 
// as input, and outputs address of the 
// head node of the sorted list. 
// 
// Input: you can design the input arguments 
// in any way you like. 
// 
Node* Merge(Node* left, Node* right) {
    // Dummy node to help with merging
    Node dummy;
    Node* tail = &dummy;

    while (left != NULL && right != NULL) {
        if (left->Get_SID() <= right->Get_SID()) {
            tail->Set_Pnext(left);
            left = left->Get_Pnext();
        } else {
            tail->Set_Pnext(right);
            right = right->Get_Pnext();
        }
        tail = tail->Get_Pnext();
    }

    // Attach the remaining elements
    if (left != NULL) {
        tail->Set_Pnext(left);
    } else {
        tail->Set_Pnext(right);
    }

    return dummy.Get_Pnext();
}
Node* MSort(Node* head) {
    // Base case: If the list is empty or has only one element, it's already sorted
    if (head == NULL || head->Get_Pnext() == NULL) {
        return head;
    }

    // Find the middle using slow/fast pointer
    Node* slow = head;
    Node* fast = head;
    Node* prev = NULL;

    while (fast != NULL && fast->Get_Pnext() != NULL) {
        fast = fast->Get_Pnext()->Get_Pnext();
        Node* next = slow->Get_Pnext();
        slow->Set_Pnext(prev);
        prev = slow;
        slow = next;
    }

    // If odd length, middle is slow; if even, middle is prev
    Node* mid = fast == NULL ? prev : slow;

    // Recursively sort the two halves
    Node* left = MSort(head);
    Node* right = MSort(mid);

    // Merge the sorted halves
    return Merge(left, right);
}

int main()
{
	// This array holds the list 
	// for binary search. 
	int L1[11];

	// This is the head pointer which 
	// holds the list for merge sort. 
	Node* L2 = NULL;

	int mode, temp, key; 

	cin >> mode >> key;

	// The first loop takes input for binary 
	// search. For simplicity, we assume there 
	// are always 11 input integers that are 
	// unique and sorted in ascending order. 
	// You can definitely write additional 
	// code in the binary search function 
	// to check the validity of these 
	// assumptions, but the test cases on 
	// Canvas will satisfy the assumptions. 
	for (int i = 0; i < 11; i++) {
		cin >> L1[i];
	}

	// ----------------------------
	// ---------- Task 3 ----------
	// ----------------------------
	// The second loop takes input for merge 
	// sort. You need to complete the loop.  
	// This task is the same as the task in 
	// hw4. You may see reference code in 
	// lecture 05.The head pointer "L2" has 
	// been declared above for you. Use it 
	// to store head of the constructed list. 
	// You can also declare extra pointers 
	// or nodes as necessary.
	while (cin >> temp) {
        Node* newNode = new Node();
        newNode->Set_SID(temp);
        newNode->Set_Pnext(L2);
        L2 = newNode;
        
	}

	// -----------------------------------
	// Start testing your implementation.
	// -----------------------------------
	// 
	// Mode 0: test BSearch()
	if (mode == 0) {

		// Task 4: apply binary search. 
		// One input should be "L1" (search list)
		// Another input should be "key" (search key)
		// Complete the input based on your design.
		cout << BSearch(L1, 0, 10, key);  // L1 is int[11], not Node*
	}
	// Mode 1: test MSort()
	else if (mode == 1) {
		
		// Task 5: apply merge sort. 
		// One input should be "L2" (input list)
		// Complete the input based on your design.		
		L2 = MSort(L2);
		Node* temp = L2;
		while (temp != NULL) {
			cout << temp->Get_SID();
			temp = temp->Get_Pnext();
		}
	}
	
	return 0;
}
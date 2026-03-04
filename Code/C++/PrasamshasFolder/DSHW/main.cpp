//
// ========================================
// HW6: Implement a Binary Search Tree 
//       which can be converted to AVL Tree 
// ========================================
// 
// In this assignment, we will implement 
// an AVL tree which can also do standard 
// Binary Search Tree adding and removal.  
// 
// The Node class and AVL class are both 
// partly defined for you. 
// 
// ***************************************
// You can add new variables and functions 
// in both classes. However, you should 
// implement the following given functions 
// because they will be called in the main 
// function to test part of your implementation. 
// (Check "main" function for more details.)
// 1. PreTraverse()
// 2. GetRoot()
// 3. Add()
// 4. Remove()
// 5. Add_AVL()
// 6. Remove_AVL()
// 7. Height()
// Other given functions are only recommended. 
// ***************************************
// 

#include <iostream>
using namespace std;

class Node {
private:
	int key;
	Node* p_left; 
	Node* p_right;
	Node* p_parent;
public:
	void Set_key(int x);
	int Get_key();
	void Set_left(Node *p);
	void Set_right(Node* p);
	void Set_parent(Node* p);
	Node* Get_left();
	Node* Get_right();
	Node* Get_parent();
	Node();
};

// -------------------
// Node Implementation
// -------------------
void Node::Set_key(int x) { 
    key = x; 
}
int Node::Get_key() { 
    return key; 
}
void Node::Set_left(Node *p) { 
    p_left = p; 
}
void Node::Set_right(Node* p) { 
    p_right = p; 
}
void Node::Set_parent(Node* p) { 
    p_parent = p; 
}
Node* Node::Get_left() { 
    return p_left; 
}
Node* Node::Get_right() { 
    return p_right; 
}
Node* Node::Get_parent() { 
    return p_parent; 
}
Node::Node() {
	key = 0;
	p_left = p_right = p_parent = NULL;
}

class AVL {
private:
	
	// This is root of the AVL
	Node* root;

	//preorder traverse helper
	void PreTraverse(Node* p) {
        // base case
		if (p == NULL){
            return;
        }
        // print root, left, right
		cout << p->Get_key();
		PreTraverse(p->Get_left());
		PreTraverse(p->Get_right());
	}

	//bst insert helper
	Node* BST_Insert(Node* cur, Node* p) {
        // base case
		if (cur == NULL){
            return p;
        }
        // insert in left or right subtree
		if (p->Get_key() < cur->Get_key()) {
            // left subtree
			Node* nl = BST_Insert(cur->Get_left(), p);
            // set left child
			cur->Set_left(nl);
            // set parent
			if (nl) {
                nl->Set_parent(cur);
            }
		}

		else {
            // right subtree
			Node* nr = BST_Insert(cur->Get_right(), p);
            // set right child
			cur->Set_right(nr);
            // set parent
			if (nr) {
                nr->Set_parent(cur);
            }
		}
        // return current node
		return cur;
	}

	//max in subtree helper
	Node* Subtree_Max(Node* p) {
        // go to rightmost node and return
		while (p && p->Get_right()) {
            p = p->Get_right();
        }
		return p;
	}

	// min in subtree helper
	Node* Subtree_Min(Node* p) {
        // go to leftmost node and return
		while (p && p->Get_left()) {
            p = p->Get_left();
        }
		return p;
	}

	//remove node in BST helper
	Node* BST_Remove(Node* cur, int key) {
        // base case
		if (cur == NULL) {
            return NULL;
        }
        // search in left or right subtree
		if (key < cur->Get_key()) {
            // left subtree
			Node* nl = BST_Remove(cur->Get_left(), key);
            // set left child
			cur->Set_left(nl);
            // set parent
			if (nl) nl->Set_parent(cur);
		}
		else if (key > cur->Get_key()) {
			Node* nr = BST_Remove(cur->Get_right(), key);
			cur->Set_right(nr);
			if (nr) nr->Set_parent(cur);
		}
         
		else {
			// found target
			if (cur->Get_left() == NULL && cur->Get_right() == NULL) {
				return NULL;
			}
            // only right child
			else if (cur->Get_left() != NULL) {
				Node* rep = Subtree_Max(cur->Get_left());
				cur->Set_key(rep->Get_key());
				Node* nl = BST_Remove(cur->Get_left(), rep->Get_key());
				cur->Set_left(nl);
				if (nl) nl->Set_parent(cur);
            
			}
            // only left child
			else {
				Node* rep = Subtree_Min(cur->Get_right());
				cur->Set_key(rep->Get_key());
				Node* nr = BST_Remove(cur->Get_right(), rep->Get_key());
				cur->Set_right(nr);
				if (nr) nr->Set_parent(cur);
			}
		}
		return cur;
	}

	// helper: rebalance upward
	void Rebalance_Up(Node* p) {
        // go up to root
		while (p != NULL) {
			string v = CheckViolation(p);
            // check violation type and perform rotations
			if (v == "LL") {
				p = Rotate_cw(p);
			}
            // right heavy
			else if (v == "RR") {
				p = Rotate_cc(p);
			}
            // left-right
			else if (v == "LR") {
				Rotate_cc(p->Get_left());
				p = Rotate_cw(p);
			}
            // right-left
			else if (v == "RL") {
				Rotate_cw(p->Get_right());
				p = Rotate_cc(p);
			}
            // move up
			p = p->Get_parent();
		}
	}

public:

	// This function implements pre-order 
	// traverse, starting from the root, 
	// and prints the traveres sequence 
	// (with no space between nodes). 
	// You can use the function you implemented 
	// in HW10 (by slightly modifying it). 
	// This function is only used to test 
	// if your updated AVL/BST is correct. 
	void PreTraverse() {
		PreTraverse(root);
	}

	// This function returns the root node. 
	// Normally we don't need it. Here we 
	// just it to evaluate your Height function.
	// See main function test case 5 for details.
	Node* GetRoot() {
		return root;
	}

	// This function performs search 
	// on the current AVL with "key".
	// It returns address of a matching 
	// node, or returns NULL if no 
	// matching node is found. 
	Node* Search(int key) {
		Node* cur = root;
        // search for the key
		while (cur) {
			if (key == cur->Get_key()) return cur;
            // move left
			else if (key < cur->Get_key()) cur = cur->Get_left();
            // move right
			else cur = cur->Get_right();
		}
		return NULL;
	}

	// This function adds a new node at 
	// "p" to AVL without considering 
	// any violation of AVL property. 
	// (So just standard BST adding.)
	// It can return a proper address. 
	Node* Add(Node* p) {
		p->Set_left(NULL);
		p->Set_right(NULL);
		p->Set_parent(NULL);
		if (root == NULL) {
			root = p;
			return p;
		}
		root = BST_Insert(root, p);
		root->Set_parent(NULL);
		return p;
	}

	// This function removes a node with 
	// "key" from AVL without considering 
	// any violation of AVL property. 
	// (So just standard BST removal.) 
	// For simplicty, to fill holes, let 
	// us only use the recursive algorithm 
	// that looks for the max/min node in 
	// the left/right subtress of the hole. 
	// It can return a proper address.
	Node* Remove(int key) {
		root = BST_Remove(root, key);
		if (root) root->Set_parent(NULL);
		return root;
	}

	// This function adds a new node 
	// stored at "p" to AVL and performs 
	// necessary rotations to restore 
	// the AVL property.
	void Add_AVL(Node* p) {
		p->Set_left(NULL);
		p->Set_right(NULL);
		p->Set_parent(NULL);
		if (root == NULL) {
			root = p;
			return;
		}
		Node* cur = root;
		Node* parent = NULL;
        // find position to insert
		while (cur) { 
			parent = cur;
            // find position to insert
			if (p->Get_key() < cur->Get_key()) cur = cur->Get_left();
            // set left child
			else cur = cur->Get_right();
		}
        // insert node
		if (p->Get_key() < parent->Get_key()) parent->Set_left(p);
        // set right child
		else parent->Set_right(p);
		p->Set_parent(parent);
        // rebalance from parent upwards
		Rebalance_Up(parent);
		while (root && root->Get_parent()) root = root->Get_parent();
	}

	// This function removes a node 
	// with "key" from AVL and performs 
	// necessary rotations to restore 
	// the AVL property.
	void Remove_AVL(int key) {
		Node* target = Search(key);
		Node* start_from = target ? target->Get_parent() : NULL;
        // remove node
		root = BST_Remove(root, key);
		if (root) root->Set_parent(NULL);
        // rebalance from parent upwards
		if (start_from) Rebalance_Up(start_from);
		while (root && root->Get_parent()) root = root->Get_parent();
	}

	// This function returns height 
	// of a subtree rooted at "p". 
	int Height(Node* p) {
		if (p == NULL) return 0;
		int lh = Height(p->Get_left());
		int rh = Height(p->Get_right());
		return (lh > rh ? lh : rh) + 1;
	}

	// This function performs clock-wise
	// rotation at node "p". It only does
	// rotation and does not consider 
	// the type of violation scenario.
	// It can return a proper address.
	Node* Rotate_cw(Node* p) {
		if (p == NULL) return p;
		Node* q = p->Get_left();
		if (q == NULL) return p;

		Node* parent = p->Get_parent();
		Node* q_right = q->Get_right();

		q->Set_right(p);
		p->Set_parent(q);
		p->Set_left(q_right);
		if (q_right) q_right->Set_parent(p);

		q->Set_parent(parent);
		if (parent == NULL) root = q;
		else {
			if (parent->Get_left() == p) parent->Set_left(q);
			else parent->Set_right(q);
		}

		return q;
	}

	// This function performs counter 
	// clock-wise rotation at node "p". 
	// It only does rotation and does 
	// not consider the type of violation 
	// scenario. It can return a proper address.
	Node* Rotate_cc(Node* p) {
		if (p == NULL) return p;
		Node* q = p->Get_right();
		if (q == NULL) return p;

		Node* parent = p->Get_parent();
		Node* q_left = q->Get_left();

		q->Set_left(p);
		p->Set_parent(q);
		p->Set_right(q_left);
		if (q_left) q_left->Set_parent(p);

		q->Set_parent(parent);
		if (parent == NULL) root = q;
		else {
			if (parent->Get_left() == p) parent->Set_left(q);
			else parent->Set_right(q);
		}

		return q;
	}

	// This function checks if there 
	// is a violation at a node p, and 
	// return the violation scenario. 
	// It returns a string of 
	// - "NO" if no violation 
	// - "LL" if it is left-left violation 
	// - "RR" if it is right-right violation
	// - "LR" if it is left-right violation
	// - "RL" if it is right-left violation 
	string CheckViolation(Node* p) {
		if (p == NULL) return "NO";
		int hl = Height(p->Get_left());
		int hr = Height(p->Get_right());
		int bf = hl - hr;
        
		if (bf > 1) {
			Node* L = p->Get_left();
			int hll = Height(L ? L->Get_left() : NULL);
			int hlr = Height(L ? L->Get_right() : NULL);
			if (hll >= hlr) return "LL";
			else return "LR";
		}
		else if (bf < -1) {
			Node* R = p->Get_right();
			int hrl = Height(R ? R->Get_left() : NULL);
			int hrr = Height(R ? R->Get_right() : NULL);
			if (hrr >= hrl) return "RR";
			else return "RL";
		}
		return "NO";
	}

	// This function intiailizes root = NULL.
	AVL() {
		root = NULL;
	}
};


// --------------
// Main Function 
// --------------
int main()
{
	// mode_test: indicate which function to test 
	// model_avl: if 1, we do adding with AVL property
	//            if 0, we do adding without AVL property 
	//            (so just standard bst adding)
	// key_temp:  keys of nodes to add to AVL initially
	// key_search:key of node for search/remove
	int mode_test, mode_avl, key_temp, key_search;
	
	Node* temp;

	// This is our AVL tree object.
	AVL tree;

	cin >> mode_test >> mode_avl >> key_search;

	while (cin >> key_temp) {

		temp = new Node;
		temp->Set_key(key_temp);

		if (mode_avl == 0)
		{
			tree.Add(temp);
		}
		else if (mode_avl == 1) {
			tree.Add_AVL(temp);
		}
	}

	// Mode 0: test "Add" function.
	if (mode_test == 0) {
		tree.PreTraverse();
	}
	// Mode 1: test "Add_AVL" function
	else if (mode_test == 1) {
		tree.PreTraverse();
	}
	// Mode 2: test "Search" function 
	else if (mode_test == 2) {
		temp = tree.Search(key_search);
		if (temp == NULL) {
			cout << -1;
		}
		else {
			cout << temp->Get_key();
		}		
	}
	// Mode 3: test "Remove" function 
	else if (mode_test == 3) {
		tree.Remove(key_search);
		tree.PreTraverse();
	}
	// Mode 4: test "Remove_AVL" function 
	else if (mode_test == 4) {
		tree.Remove_AVL(key_search);
		tree.PreTraverse();
	}
	// Mode 5: test "Height" function 
	else if (mode_test == 5) {
		cout << tree.Height(tree.GetRoot());
	}

	return 0;
}

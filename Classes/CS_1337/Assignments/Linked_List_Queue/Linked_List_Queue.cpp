#include <iostream>

using namespace std;

struct Node{
    int num;
    Node *next;
};

void enQueue(int n, Node *&head)
{
    //append into queue
    Node *ptr = new Node;
    ptr->num = n;
    ptr->next = nullptr;

    if (head == nullptr){
        head = ptr;
    }
    else{
        Node *curr = head;
        while (curr->next != nullptr){
            curr = curr->next;
        }
        curr->next = ptr;
    }
}

void printQueue(Node *head)
{
    Node *ptr = head;
    while (ptr!=nullptr){
        cout << ptr->num << " ";
        ptr = ptr->next;
    }
    cout << endl;
}

Node *deQueue(Node *&head)
{
    // delete from the head and move next
    Node *ptr = head;
    head = head->next;
    ptr->next = nullptr;
    //printQueue(head);
    return ptr;
    //delete the node
}

int main()
{
    //have a ptr to head and ptr to current
    Node *head = nullptr;
    
    enQueue(1, head);
    enQueue(2, head);
    enQueue(3, head);
    enQueue(4, head);
    enQueue(5, head);
    enQueue(6, head);
    enQueue(7, head);

    printQueue(head);
    delete deQueue(head);
    printQueue(head);
    return 0;
}

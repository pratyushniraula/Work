#include <iostream>
using namespace std;

struct node
{
    int num;
    node *next;
};

void addNode(node *&);
void deleteNode(node *&, int);
void printList(node *);

int main()
{
    int menu;
    node *head = nullptr;

    do
    {
        cout << "1. Add Node\n"
            << "2. Delete Node\n"
            << "3. Print List\n"
            << "4. Quit\n\n"
            << "Please pick an operation for the linked list: ";
        cin >> menu;

        if (menu == 1)
            addNode(head);
        else if (menu == 2)
        {
            int num2del;
            cout << "What number would you like to delete";
            cin >> num2del;
            deleteNode(head, num2del);
        }
        else if (menu == 3)
            printList(head);

    } while (menu != 4);

}


void printList(node *head)
{
    node *ptr = head;
    while (ptr != nullptr)
    {
        cout << ptr->num << " ";
        ptr = ptr->next;
    }
    cout << endl;
}
void addNode(node *&head)
{
    node *A = new node, *ptr = head;

    cout << "Enter the value for the node: ";
    cin >> A->num;
    A->next = nullptr;

    ptr = head;
    if (head == nullptr)
    {
         head = A;
         return;
    }
    else if (A->num < head->num)
    {
        A->next = head;
        head = A;
        return;
    }
    else
    {
        while (ptr->next != nullptr)
        {
            if (ptr->next->num > A->num)
            {
                A->next = ptr->next;
                ptr->next = A;
                return;
            }
            ptr = ptr->next;
        }
    }

    //if (ptr->next == nullptr)
        ptr->next = A;
}

void deleteNode(node *&head, int del)
{
    node *ptr = head;

    if (head == nullptr)
    {
        cout << "Nothing to delete";
        return;
    }
    else if (del == head->num)
    {
        head = head->next;
        ptr->next = nullptr;
        delete ptr;
        return;
    }
    else
    {
        while (ptr->next != nullptr)
        {
            if (ptr->next->num == del)
            {
                node *hold = ptr->next;
                ptr->next = ptr->next->next;
                hold->next = nullptr;
                delete hold;
                return;
            }
            ptr = ptr->next;
        }

    }
    cout << "Number is not in list";
}

//works?

    void removeByPoints(node *&head, int position){
    node *ptr = head;
    if (head == nullptr)
    {
        cout << "Nothing to remove";
        return;
    }
    else if (position == 0)
    {
        head = head->next;
        ptr->next = nullptr;
        delete ptr;
        return;
    }
    else{
        for (int i = 0; i < position - 1; i++){
            ptr = ptr->next;
            if (ptr == nullptr){
                cout << "Invalid position";
                return;
            }
        }
        node *hold = ptr->next;
        ptr->next = ptr->next->next;
        hold->next = nullptr;
        delete hold;
        return;
    }
}

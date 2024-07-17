class node:
    def __init__(self, data, next):
        self.data = data
        self.next = next

    def __str__(self):
        return f'{"my data is:"} {self.data} {"and the next node that I point to is"} {self.next}{"\n"}'

def addnode(*root, newnode):
    #adds a node to the head
    newnode.next = root
    root = newnode


    
    

def main():
    counter = 0
    choice = input("would you like to add, read, or delete a node? (1 for add 2 for read 3 for delete 0 for exit: )")
    if choice == 1:
        print("what would you like to have the node hold")

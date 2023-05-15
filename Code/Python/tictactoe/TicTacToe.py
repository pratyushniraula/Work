#way to represent board
"""directions, type in 2 numbers seperated by a space, for the game loop ask for player x and the player o, check if some other variable is already there, after it is put dpwn check for win conditions and stuff, but also check for stalemates"""
#if win condition does not work make sure to loop and restart

def vertical check
class tic:
    wincondition = False
    a = [0,0,0,0,0,0,0,0,0]
    j = 0
    for i in range(3):   
        print(str(a[j]) + " " + str(a[j+1]) + " " + str(a[j+2]))
        j += 3

    #make a method inside of the class
    #check win condition
    
        

    while(not wincondition):
        #wincondition
        if(xwin(a)):
            print("X wins")

        owin(a):
            print("O wins")

        #x player's turn
        x = input("x go put something: ")
        
        if(a[x] != 0):
            print("stop messing around")
            x = input("x go put something: ")

        else:
            a[x] == 'x'

        #redisplay the puzzle
        j = 0
        for i in range(3):   
            print(str(a[j]) + " " + str(a[j+1]) + " " + str(a[j+2]))
            j += 3

       #O player's turn
        o = input("o go put something: ")
        
        if(a[x] != 0):
            print("stop messing around")
            x = input("x go put something: ")

        else:
            a[o] == 'o'

        #redisplay the puzzle
        j = 0
        for i in range(3):   
            print(str(a[j]) + " " + str(a[j+1]) + " " + str(a[j+2]))
            j += 3
          #something added here
#while else statement

count = 0
print("while loop starts here:")
while(count < 3):
    print("before iteration in loop: ", count)
    count += 1
    print("after iteration in loop: ", count, "\n")
else: 
    print("holy wow look at this, we are in an else block")

#for loop
n = 4
print("\nwe are now in the for loop")
for i in range(0,n):
    print(i)


bruh = ["hi", "hello", "bye"]
print("\n")
for i in range(len(bruh)):
    print(bruh[i])
else:
    print("\nwe have reached the else block in the second for loop")
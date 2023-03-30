print("hello everyone")
# get input of a and b 
a = int(input("Enter a: "))
b = int(input("Enter b: "))


# function to add two numbers
def add(a,b):
    return a+b
# function to subtract two numbers
def subtract(a,b):
    return a-b
# function to multiply two numbers
def multiply(a,b):
    return a*b
# function to divide two numbers
def divide(a,b):
    return a/b
# function to calculate power of a number
def power(a,b):
    return a**b

# print the result
print("Addition of a and b is: ", add(a,b))
print("Subtraction of a and b is: ", subtract(a,b))
print("Multiplication of a and b is: ", multiply(a,b))
print("Division of a and b is: ", divide(a,b))
print("Power of a and b is: ", power(a,b))
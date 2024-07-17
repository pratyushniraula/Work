class book:
    def __init__(self, title, numberOfPages):
        self.title = title
        self.numberOfPages = numberOfPages
        self.bruh = "12"
    def __str__(self):
        return f'{"hello this is something "}{12} {"did this work"} {self.title}'
   
    def dosum(self, INTEGER):
        print(INTEGER)

something = book("rah", 432)

print(something)

something.dosum(123)
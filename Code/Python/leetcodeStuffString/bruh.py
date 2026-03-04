#string with words and spaces no other types of characters. Full sentences without punctuations
#function that takes a string and 

#add everything to a long ass list
def maxletterperword(s):
    big =[]
    big = list(s)
    cha = ''
    longest = 0
    b = {}
    for l in s:
        if l == ' ':
            b.clear()
        b[l] = b.get(l, 0) + 1
        if b[l] > longest:
            cha = l
        longest = max(longest, b[l])

    return longest, cha



    

print(maxletterperword("bob randy tonoo"))
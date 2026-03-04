class Villager:
    def __init__(self, name, species, personality, catchphrase, neighbor=None):
        self.name = name
        self.species = species
        self.personality = personality
        self.catchphrase = catchphrase
        self.furniture = []
        self.neighbor = neighbor
    def greet_player(self, player_name):
        return f"{self.name}: Hey there, {player_name}! How's it going, {self.catchphrase}!"
    def set_catchphrase(self, phrase):
        if len(phrase) <= 20 and self.phrase_checker(phrase):
            self.catchphrase = phrase
            print("Catchphrase Updated!")
        else:
            print("Invalid catchphrase")

    def phrase_checker(self, phrase):
        for i in phrase:
            if not i.isalpha() and not i.isspace():
                return False
        return True
    
    def add_item(self, item_name):
        valid = ["acoustic guitar", "ironwood kitchenette", "rattan armchair", "kotatsu", "cacao tree"]
        if item_name in valid:
            self.furniture.append(item_name)
    def print_inventory(self):
        if self.furniture == []:
            print("No furniture in inventory.")
        d = {}
        for item in self.furniture:
            if item in d:
                d[item] += 1
            else:
                d[item] = 1
        print(d)
        
def of_personality_type(townies, personality_type):
    personality=[]
    for villager in townies:
        if villager.personality == personality_type:
            personality.append(villager)
    return personality
def message_received(start_villager, target_villager):
    
    neighbor = start_villager.neighbor
    for friend in neighbor:
        if neigbor == target_villager:
            return print("Message received")
    
    

isabelle = Villager("Isabelle", "Dog", "Normal", "what's up?")
tom_nook = Villager("Tom Nook", "Raccoon", "Cranky", "yes, yes")
kk_slider = Villager("K.K. Slider", "Dog", "Lazy", "dig it")
isabelle.neighbor = tom_nook
tom_nook.neighbor = kk_slider
print(message_received(isabelle, kk_slider))
print(message_received(kk_slider, isabelle))
"""
bones = Villager("bones","dog", "lazy","yip yip")
f = Villager("f","bog", "lazy","yip yip")
b = Villager("b","sog", "sfdjkl","yip yip")
g = Villager("g","fog", "lazy","yip yip")
townies = [bones, f, b, g]
lazy_townies = of_personality_type(townies, "lazy")
print(len(lazy_townies))
for i in lazy_townies:
    print(i.name)"""
# print(bones.greet_player("bob"))
# print()
# bones.set_catchphrase("ruff it up")
# print(bones.greet_player("sammy"))
# print()
# bones.set_catchphrase("123")
# print(bones.greet_player("sammy"))
# print()
# bones.set_catchphrase("")
# print(bones.greet_player("sammy"))
# print()
# bones.set_catchphrase("flsnjdlfknsdl fdnslkdn")
# print(bones.greet_player("sammy"))
# print(bones.furniture)
# bones.add_item("acoustic guitar")
# print(bones.furniture)
# bones.add_item("dflsjnd")
# print(bones.furniture)
# bones.add_item("cacao tree")
# print(bones.furniture)
# bones.add_item("cacao tree")
# print(bones.furniture)
# bones.add_item("cacao tree")
# print(bones.furniture)
# bones.add_item("cacao tree")
# print(bones.furniture)
# bones.add_item("cacao tree")
# print(bones.furniture)

# bones.print_inventory()
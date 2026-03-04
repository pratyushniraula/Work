import math
from collections import defaultdict, Counter

def read_data(path):
    with open(path) as f:
        lines = [l.strip() for l in f if l.strip()]
    return [ln.split(',') for ln in lines]

mush_train = read_data("/mnt/data/mush_train.data")
mush_test  = read_data("/mnt/data/mush_test.data")

def entropy(labels):
    total = len(labels)
    c = Counter(labels)
    return -sum((cnt/total)*math.log2(cnt/total) for cnt in c.values())

def info_gain(rows, attr_idx):
    base = entropy([r[0] for r in rows])
    parts = defaultdict(list)
    for r in rows:
        parts[r[attr_idx]].append(r)
    rem = 0.0
    n = len(rows)
    for subset in parts.values():
        rem += (len(subset)/n) * entropy([s[0] for s in subset])
    return base - rem

class Node:
    def __init__(self, attr=None, label=None, gain=None):
        self.attr = attr
        self.label = label
        self.children = {}
        self.gain = gain

def build_tree(rows, attrs):
    labels = [r[0] for r in rows]
    if len(set(labels)) == 1:
        return Node(label=labels[0], gain=0.0)
    if not attrs:
        maj = Counter(labels).most_common(1)[0][0]
        return Node(label=maj, gain=0.0)

    # pick attribute with max info gain, break ties by smaller index
    gains = [(info_gain(rows, a), a) for a in attrs]
    gains.sort(key=lambda x: (-x[0], x[1]))
    best_gain, best_attr = gains[0]

    node = Node(attr=best_attr, gain=best_gain)
    parts = defaultdict(list)
    for r in rows:
        parts[r[best_attr]].append(r)
    new_attrs = [a for a in attrs if a != best_attr]
    for v, subset in parts.items():
        node.children[v] = build_tree(subset, new_attrs)
    return node

attrs = list(range(1, 23))  # cols 1..22 are attributes, col 0 is the class
tree = build_tree(mush_train, attrs)

def predict(tree, row):
    while tree.label is None:
        v = row[tree.attr]
        if v in tree.children:
            tree = tree.children[v]
        else:
            return 'e'      # safe fallback
    return tree.label

def accuracy(tree, data):
    correct = sum(1 for r in data if predict(tree, r) == r[0])
    return correct / len(data)

train_acc = accuracy(tree, mush_train)
test_acc  = accuracy(tree, mush_test)

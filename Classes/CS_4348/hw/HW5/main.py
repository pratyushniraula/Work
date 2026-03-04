#Pratyush Niraula
# B-tree implementation in Python question 5


class BTreeNode:
    def __init__(self, t, leaf):
        self.t = t  # Minimum degree
        self.leaf = leaf  # Is true when node is leaf
        self.keys = []  # List of keys
        self.children = []  # List of child pointers

class BTree:
    def __init__(self, t):
        self.t = t
        self.root = BTreeNode(t, leaf=True)

    def traverse(self):
        #Depth-first, in-order traversal of the B-tree.
        self._traverse(self.root)

    def _traverse(self, node):
        # Traverse keys and children
        for i in range(len(node.keys)):
            if not node.leaf:
                self._traverse(node.children[i])
            print(node.keys[i], end=' ')
        if not node.leaf:
            self._traverse(node.children[len(node.keys)])

    def insert(self, k):
        root = self.root
        # If root is full, split it
        if len(root.keys) == 2 * self.t - 1:
            new_root = BTreeNode(self.t, leaf=False)
            new_root.children.append(root)
            self._split_child(new_root, 0)
            self.root = new_root
            self._insert_nonfull(new_root, k)
        else:
            self._insert_nonfull(root, k)

    def _insert_nonfull(self, node, k):
        i = len(node.keys) - 1
        if node.leaf:
            node.keys.append(None)
            # Insert key in sorted position
            while i >= 0 and k < node.keys[i]:
                node.keys[i + 1] = node.keys[i]
                i -= 1
            node.keys[i + 1] = k
        else:
            # Find the child to descend into
            while i >= 0 and k < node.keys[i]:
                i -= 1
            i += 1
            # Split child if full
            if len(node.children[i].keys) == 2 * self.t - 1:
                self._split_child(node, i)
                if k > node.keys[i]:
                    i += 1
            self._insert_nonfull(node.children[i], k)

    def _split_child(self, parent, i):
        t = self.t
        y = parent.children[i]
        z = BTreeNode(t, leaf=y.leaf)
        # Median key moves up
        mid_key = y.keys[t - 1]
        # New node z gets the last t-1 keys of y
        z.keys = y.keys[t:]
        y.keys = y.keys[:t - 1]
        # If not leaf, move child pointers
        if not y.leaf:
            z.children = y.children[t:]
            y.children = y.children[:t]
        # Insert z into parent's children
        parent.children.insert(i + 1, z)
        # Insert median key into parent
        parent.keys.insert(i, mid_key)

# Create B-tree with minimum degree 4 and insert keys
bt = BTree(t=4)
keys_to_insert = [7, 9, 4, 8, 5, 6, 3, 2, 10, 11, 12, 13, 14, 15, 16, 17]
for key in keys_to_insert:
    bt.insert(key)

# Traverse and print the B-tree
bt.traverse()


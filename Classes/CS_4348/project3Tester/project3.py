import sys
import os
import struct
import csv

# Constants
BLOCK_SIZE = 512
MAGIC = b"4348PRJ3"
MAGIC_PAD = MAGIC.ljust(8, b"\x00")
MIN_DEGREE = 10
MAX_KEYS = 2 * MIN_DEGREE - 1
MAX_CHILDREN = MAX_KEYS + 1
HEADER_FORMAT = ">8sQQ"  # magic, root_id, next_block_id
NODE_HEADER_FORMAT = ">QQQ"  # block_id, parent, num_keys
KEY_FORMAT = ">Q"  # 8-byte unsigned
PTR_FORMAT = ">Q"

class BTreeNode:
    def __init__(self, block_id, parent=0):
        self.block_id = block_id
        self.parent = parent
        self.keys = []
        self.values = []
        self.children = []  # block IDs

    def serialize(self):
        # header
        data = struct.pack(NODE_HEADER_FORMAT, self.block_id, self.parent, len(self.keys))
        # keys
        for i in range(MAX_KEYS):
            val = self.keys[i] if i < len(self.keys) else 0
            data += struct.pack(KEY_FORMAT, val)
        # values
        for i in range(MAX_KEYS):
            val = self.values[i] if i < len(self.values) else 0
            data += struct.pack(KEY_FORMAT, val)
        # children
        for i in range(MAX_CHILDREN):
            val = self.children[i] if i < len(self.children) else 0
            data += struct.pack(PTR_FORMAT, val)
        # pad
        data += b"\x00" * (BLOCK_SIZE - len(data))
        return data

    @classmethod
    def deserialize(cls, data):
        header_size = struct.calcsize(NODE_HEADER_FORMAT)
        block_id, parent, num_keys = struct.unpack(NODE_HEADER_FORMAT, data[:header_size])
        node = cls(block_id, parent)
        offset = header_size
        # keys
        for _ in range(MAX_KEYS):
            (k,) = struct.unpack(KEY_FORMAT, data[offset:offset+8])
            if len(node.keys) < num_keys:
                node.keys.append(k)
            offset += 8
        # values
        for _ in range(MAX_KEYS):
            (v,) = struct.unpack(KEY_FORMAT, data[offset:offset+8])
            if len(node.values) < num_keys:
                node.values.append(v)
            offset += 8
        # children
        for _ in range(MAX_CHILDREN):
            (c,) = struct.unpack(PTR_FORMAT, data[offset:offset+8])
            if len(node.children) < num_keys + 1:
                node.children.append(c)
            offset += 8
        return node

class BTree:
    def __init__(self, filename):
        self.filename = filename
        if not os.path.exists(filename):
            raise FileNotFoundError(f"Index file {filename} not found.")
        self.fp = open(filename, 'r+b')
        self._read_header()

    @staticmethod
    def create(filename):
        if os.path.exists(filename):
            print(f"Error: file {filename} already exists.")
            sys.exit(1)
        with open(filename, 'w+b') as f:
            # write header
            root_id = 0
            next_block = 1
            header = struct.pack(HEADER_FORMAT, MAGIC_PAD, root_id, next_block)
            f.write(header)
            f.write(b"\x00" * (BLOCK_SIZE - len(header)))
        print(f"Created index file {filename}.")

    def _read_header(self):
        self.fp.seek(0)
        header = self.fp.read(BLOCK_SIZE)
        magic, self.root_id, self.next_block = struct.unpack(HEADER_FORMAT, header[:struct.calcsize(HEADER_FORMAT)])
        if magic.rstrip(b"\x00") != MAGIC:
            print("Error: invalid index file.")
            sys.exit(1)

    def _write_header(self):
        self.fp.seek(0)
        header = struct.pack(HEADER_FORMAT, MAGIC_PAD, self.root_id, self.next_block)
        self.fp.write(header)
        # rest unchanged

    def _read_node(self, block_id):
        self.fp.seek(block_id * BLOCK_SIZE)
        data = self.fp.read(BLOCK_SIZE)
        return BTreeNode.deserialize(data)

    def _write_node(self, node):
        self.fp.seek(node.block_id * BLOCK_SIZE)
        self.fp.write(node.serialize())

    def search(self, key):
        if self.root_id == 0:
            print("Error: tree is empty.")
            return
        return self._search_recursive(self.root_id, key)

    def _search_recursive(self, block_id, key):
        node = self._read_node(block_id)
        # find position
        i = 0
        while i < len(node.keys) and key > node.keys[i]:
            i += 1
        if i < len(node.keys) and key == node.keys[i]:
            print(f"Found: {key} -> {node.values[i]}")
            return
        if len(node.children) == 0 or node.children[i] == 0:
            print("Error: key not found.")
            return
        return self._search_recursive(node.children[i], key)

    def insert(self, key, value):
        # if empty tree
        if self.root_id == 0:
            # create root
            root = BTreeNode(self.next_block)
            self.root_id = root.block_id
            self.next_block += 1
            root.keys = [key]
            root.values = [value]
            root.children = []
            self._write_header()
            self._write_node(root)
            print(f"Inserted {key}:{value} as root.")
            return
        root = self._read_node(self.root_id)
        if len(root.keys) == MAX_KEYS:
            # split
            new_root = BTreeNode(self.next_block)
            self.next_block += 1
            new_root.children = [self.root_id]
            root.parent = new_root.block_id
            self._write_node(root)
            self.root_id = new_root.block_id
            self._write_header()
            self._split_child(new_root, 0)
            self._insert_nonfull(new_root, key, value)
        else:
            self._insert_nonfull(root, key, value)

    def _split_child(self, parent, idx):
        t = MIN_DEGREE
        child = self._read_node(parent.children[idx])
        new_child = BTreeNode(self.next_block)
        self.next_block += 1
        new_child.parent = parent.block_id
        # move keys/values
        new_child.keys = child.keys[t:]
        new_child.values = child.values[t:]
        # move children
        if child.children:
            new_child.children = child.children[t:]
        # reduce child
        child.keys = child.keys[:t-1]
        child.values = child.values[:t-1]
        child.children = child.children[:t]
        # insert in parent
        parent.keys.insert(idx, child.keys[t-1])
        parent.values.insert(idx, child.values[t-1])
        parent.children.insert(idx+1, new_child.block_id)
        # write nodes
        self._write_node(child)
        self._write_node(new_child)
        self._write_node(parent)

    def _insert_nonfull(self, node, key, value):
        if not node.children:
            # leaf
            idx = len(node.keys) - 1
            node.keys.append(0)
            node.values.append(0)
            while idx >= 0 and key < node.keys[idx]:
                node.keys[idx+1] = node.keys[idx]
                node.values[idx+1] = node.values[idx]
                idx -= 1
            node.keys[idx+1] = key
            node.values[idx+1] = value
            self._write_node(node)
            print(f"Inserted {key}:{value} in leaf {node.block_id}.")
        else:
            idx = len(node.keys) - 1
            while idx >= 0 and key < node.keys[idx]:
                idx -= 1
            idx += 1
            child = self._read_node(node.children[idx])
            if len(child.keys) == MAX_KEYS:
                self._split_child(node, idx)
                if key > node.keys[idx]:
                    idx += 1
                child = self._read_node(node.children[idx])
            self._insert_nonfull(child, key, value)

    def print_all(self):
        if self.root_id == 0:
            return
        self._print_recursive(self.root_id)

    def _print_recursive(self, block_id):
        node = self._read_node(block_id)
        for i, k in enumerate(node.keys):
            if node.children and node.children[i] != 0:
                self._print_recursive(node.children[i])
            print(f"{k},{node.values[i]}")
        if node.children and node.children[-1] != 0:
            self._print_recursive(node.children[-1])

    def extract(self, outfile):
        with open(outfile, 'w', newline='') as csvfile:
            writer = csv.writer(csvfile)
            self._extract_recursive(self.root_id, writer)
        print(f"Extracted to {outfile}.")

    def _extract_recursive(self, block_id, writer):
        if block_id == 0:
            return
        node = self._read_node(block_id)
        for i, k in enumerate(node.keys):
            if node.children and node.children[i] != 0:
                self._extract_recursive(node.children[i], writer)
            writer.writerow([k, node.values[i]])
        if node.children and node.children[-1] != 0:
            self._extract_recursive(node.children[-1], writer)

    def load_csv(self, csvfile):
        if not os.path.exists(csvfile):
            print(f"Error: CSV file {csvfile} not found.")
            sys.exit(1)
        with open(csvfile, newline='') as f:
            reader = csv.reader(f)
            for row in reader:
                k, v = map(int, row)
                self.insert(k, v)

    def close(self):
        self.fp.close()


def main():
    if len(sys.argv) < 3:
        print("Usage: project3 <command> args...")
        sys.exit(1)
    cmd = sys.argv[1].lower()
    if cmd == "create":
        if len(sys.argv) != 3:
            print("Usage: project3 create <file>")
            sys.exit(1)
        BTree.create(sys.argv[2])
    else:
        idx = BTree(sys.argv[2])
        if cmd == "insert":
            k = int(sys.argv[3])
            v = int(sys.argv[4])
            idx.insert(k, v)
        elif cmd == "search":
            k = int(sys.argv[3])
            idx.search(k)
        elif cmd == "load":
            idx.load_csv(sys.argv[3])
        elif cmd == "print":
            idx.print_all()
        elif cmd == "extract":
            idx.extract(sys.argv[3])
        else:
            print(f"Unknown command {cmd}")
        idx.close()

if __name__ == "__main__":
    main()
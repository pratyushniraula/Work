//Wilson Cheng   wxc220002
//Pratyush Niraula   pxn210033 


import java.util.Random;

class HashTable {
   private static final int INITIAL_SIZE = 10;
   private static final double LOAD_FACTOR_THRESHOLD = 2.0;

   private class Node {
      int data;
      Node next;

      public Node(int data) {
         this.data = data;
         this.next = null;
      }
   }

   private class LinkedList {
      Node head;

      public void insert(int data) {
         Node newNode = new Node(data);
         newNode.next = head;
         head = newNode;
      }
   }

   private LinkedList[] table;
   private int size;
   private int itemCount;

   public HashTable() {
      size = INITIAL_SIZE;
      table = new LinkedList[size];
      for (int i = 0; i < size; i++) {
         table[i] = new LinkedList();
      }
      itemCount = 0;
   }

   private int hash(int key) {
      return key % size;
   }

   private void rehash() {
      int newSize = size * 2;
      LinkedList[] newTable = new LinkedList[newSize];

      for (int i = 0; i < newSize; i++) {
         newTable[i] = new LinkedList();
      }

      for (LinkedList list : table) {
         Node current = list.head;
         while (current != null) {
            int newIndex = current.data % newSize;
            newTable[newIndex].insert(current.data);
            current = current.next;
         }
      }

      table = newTable;
      size = newSize;
   }

   public void insert(int data) {
      itemCount++;
      if ((double) itemCount / size >= LOAD_FACTOR_THRESHOLD) {
         rehash();
      }

      int index = hash(data);
      table[index].insert(data);
   }

   public void print() {
      for (int i = 0; i < size; i++) {
         System.out.print("Bucket " + i + ": ");
         Node current = table[i].head;
         while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
         }
         System.out.println();
      }
   }
}

public class Main {
   public static void main(String[] args) {
      HashTable hashTable = new HashTable();
      Random rand = new Random(5);
      int value;

      for (int i = 0; i < 19; i++) {
         value = rand.nextInt(40);
         hashTable.insert(value);
      }
      System.out.println("Hash Table before rehash:");
      hashTable.print();

      System.out.println("Hash Table after first rehash:");
      hashTable.insert(24);
      hashTable.print();
      
      for (int i = 0; i < 20; i++) {
         value = rand.nextInt(40);
         hashTable.insert(value);
      }
      
      System.out.println("Hash Table after second rehash:");
      hashTable.print();
   }
}

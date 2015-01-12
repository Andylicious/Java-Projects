// $Id: prioqueue.java,v 1.68 2013-05-11 20:26:28-07 - - $

import java.util.Comparator;
import java.util.NoSuchElementException;
import static java.lang.System.*;

class prioqueue<value_t> {
   private Comparator<value_t> comparator;
   private value_t[] queue = null;
   private int last = -1;
   private final int ROOT = 0;

   // Functions to adjust indices between parent and child.
   private int parent (int child) { return (child - 1) / 2; }
   private int lchild (int parent) { return 2 * parent + 1; }
   private int rchild (int parent) { return 2 * parent + 2; }

   // Capacity inspection functions.
   private boolean full() { return last == queue.length - 1; }
   public boolean empty() { return last < ROOT; }

   // Double the size of the array if it is full.
   private void realloc() {
      value_t[] new_queue = new_array (queue.length * 2 + 1);
      arraycopy (queue, 0, new_queue, 0, queue.length);
      queue = new_queue;
   }

   // Allocate array either in ctor or in realloc.
   @SuppressWarnings ("unchecked")
   private value_t[] new_array (int size) {
      return (value_t[]) new Object[size];
   }

   // Exchange two elements of the queue array.
   private void swap (int node1, int node2) {
      value_t tmp = queue[node1];
      queue[node1] = queue[node2];
      queue[node2] = tmp;
   }

   // Use comparator to compare two elements of the queue array.
   private int compare (int node1, int node2) {
      return comparator.compare (queue[node1], queue[node2]);
   }


   //
   // Ctor with a default capacity.
   //
   public prioqueue (Comparator<value_t> comparator) {
      this (comparator, 16);
   }

   //
   // Ctor with a caller specified capacity.
   //
   public prioqueue (Comparator<value_t> comparator, int capacity) {
      this.comparator = comparator;
      queue = new_array (capacity);
   }

   // 
   // Insert a new value into the priority queue as the last element,
   // then percolate it upward toward the root.
   //
   public void insert (value_t value) {
      if(full()) realloc();
      this.queue[++last] = value;
      int child = last;
      while(child>ROOT){
       if(compare(parent(child),child) < 0) break;
        swap(parent(child),child);
        child = parent(child);
      }
   }

   //
   // Remove the smallest value from the root, move the last value
   // to the root, then percolate it downward toward the leaf nodes.
   //
   public value_t deletemin() {
     if (empty()) throw new NoSuchElementException();
       value_t result = null;
       result = queue[ROOT];
       queue[ROOT] = queue[last];
       last--;
       int parent = ROOT;
       int smallerchild;
       while(lchild(parent) <= last){
         if(rchild(parent) > last) {
           smallerchild = lchild(parent);
         }else{
            if(compare(lchild(parent),rchild(parent)) > 0){
              smallerchild = rchild(parent);
            }else{
               smallerchild = lchild(parent);
             }
          }
         if(compare(parent,smallerchild) <= 0) break;
           swap(parent,smallerchild);
           parent = smallerchild;  
         }
      return result;
   }
   }



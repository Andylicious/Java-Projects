// $Id: codetree.java,v 1.79 2013-05-13 12:27:25-07 - - $

import static java.lang.System.*;
import java.util.Comparator;

class codetree {
   public static final int UBYTE_RANGE =
               Byte.MAX_VALUE - Byte.MIN_VALUE + 1;

   private int ubyte;
   private int freq;
   private codetree left;
   private codetree right;
   //
   // Check whether a character is a printable ASCII (7-bit)
   // graphic, or control or non-ASCII character.
   //
   public static boolean isgraph (int abyte) {
      return 0x21 <= abyte && abyte <= 0x7E;
   }
   //
   // Provide a convenient way of printing a codetree node.
   //
   public String toString() {
      String visubyte = String.format
             (isgraph(ubyte) ? "%c" : "\\x%02X", ubyte);
      return String.format ("codetree{'%s',%d,@%x,@%x}", visubyte, freq,
             identityHashCode (left), identityHashCode (right));
   }
   //
   // Comparator class and unique object made from it, which
   // compares nodes by frequency if different, and uses ubyte
   // if frequencies are the same.
   //
   private static class comparator implements Comparator<codetree> {
      public int compare (codetree code1, codetree code2) {
         int cmp = Integer.compare (code1.freq, code2.freq);
         if (cmp != 0) return cmp;
         return Integer.compare (code1.ubyte, code2.ubyte);
      }
   }
   private static final comparator comparator = new comparator();
   //
   // Distinguish leaf nodes from interior nodes.
   //
   public boolean is_leaf() {
      return left == null && right == null;
   }
   //
   // Leaf ctor taking ubyte and freq.
   //
   public codetree (int ubyte, int freq) {
      this (ubyte, freq, null, null);
   }
   //
   // Interior ctor taking ubyte, freq, and two children.
   //
   public codetree (int ubyte, int freq,
                    codetree left, codetree right) {
      this.ubyte = ubyte;
      this.freq = freq;
      this.left = left;
      this.right = right;
   } 
   //
   // Given an array of frequencies, create a Huffman tree from
   // the array.
   // (1) Load the freq table into a priority queue.
   // (2) Convert the priority queue into a single tree.
   //
   public static codetree make_codetree (int[] frequencies) {
   //loop to create priority queue
      prioqueue<codetree> queue = new prioqueue<codetree> (comparator);
      for(int i = 0; i < frequencies.length; i++){
            if(frequencies[i] == 0){
             out.print("");
            }else{
               codetree freq = new codetree(i,frequencies[i]);
               queue.insert(freq);
             }
      } 
      //loop to create huffman tree
      for(;;){
        codetree leftchild = queue.deletemin();
        if(queue.empty())  return leftchild;
          codetree rightchild = queue.deletemin();
          codetree tree = new codetree(leftchild.ubyte, 
          leftchild.freq + rightchild.freq,
          leftchild,rightchild);
          queue.insert(tree);         
      }       
   }
   //
   // Recursive helper function called by make_encoding to create
   // an encoding for each ubyte.
   //
   private static void encode (String[] encoding,
                       String prefix, codetree root) {
      if(root.is_leaf()) encoding[root.ubyte] = prefix;
        else{
          encode(encoding, prefix +"0", root.left);
          encode(encoding, prefix +"1", root.right);
        }
   }
   //
   // Convert a Huffman tree into an array of encoding strings.
   //
   public String[] make_encoding() {
      String[] encoding = new String[UBYTE_RANGE];
      encode (encoding, "", this);
      etclib.debug ("return %s%n", (Object) encoding);
      return encoding;
   }
}


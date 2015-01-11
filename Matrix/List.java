
public class List {
   private class Node {
	   // Fields
	   Object data;
	   Node prev;
	   Node next;
	   //Constructor
	   Node(Object data) { this.data = data; next = null; }
	   // toString: Overrides Object's toString method
	              public boolean equals(Object obj){
             if(!(obj instanceof Node) ) return false;
             if(obj == this) return true;
             Node N = (Node)obj;
       
            Node M = this;
            return N.data.equals(M.data);
           }
	   // toString: Overrides Object's toString method
	   public String toString() {
             String str = "";
           //  System.out.println("this.data is " + this.data);
             str +=this.data.toString();
             return str;
           }
   }
   
   //Fields
   private Node front;
   private Node back;
   private Node curr;
   private int length;
   
      public String toString(){
     String str = "";
     Node N = front;
     N = N.next;
     for(; N !=null; N = N.next){
       str +=N.toString() + " ";
     }
   return str;
  }
   //Constructor, initialize values
   List() { front = back = curr = null; length = 0; }
   
   
//////////ACCESS FUNCTIONS///////////////////////////////////////////////////////
   
   /*length() : returns length of current list*/
   int length() {
	   return length;
   }
   
   /*isEmpty() : returns true if stack is empty, false otherwise*/
   boolean isEmpty() {
	   return length == 0;
   }
   
   /*offEnd() : returns true if current is undefined*/
   
   boolean offEnd() {
	   if (this.curr == null) return true;
	   else return false;
   }
   
   /*getIndex() : returns position of current element if defined*/
   int getIndex() {
	   if (!this.offEnd()) {
		   int returnindex = 0;
		   Node indexcurr = curr;
		   //decrement number of times to reach the front of list
		   while (indexcurr != front) { 
			   returnindex++; 
			   indexcurr = indexcurr.prev; 
		   }
		   return returnindex;
	   } else return -1; //otherwise return -1
   }
   
   /*getFront() : returns front element.
    * PRE: !isEmpty();
    */
   Object getFront() {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: getFront() ---> empty List");
	   }
	   return front.data;
   }
   
   /*getBack() : returns back element
    * PRE: !isEmpty();
    */
   Object getBack() {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: getBack() ---> empty List");
	   }
	   return back.data;
   }

   Object getElement() {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: getElement() ---> empty List");
	   }
	   if (this.offEnd()) {
		   throw new RuntimeException("List Error: getElement() ---> null pointer");
	   }
	   return curr.data;
   }
   
   /*equals(List L) : returns true if this List and L are same sequence
    * of integers.  Ignores current elements.
    */
   
   boolean equals(List L) {
	   boolean flag = true;
	   Node N = this.front;
	   Node M = L.front;
	   
	   if (this.length==L.length) {
		   while (flag && N!=null) {
			   flag = (N.data==M.data);
			   N = N.next;
			   M = M.next;
		   }
		   return flag;
	   } else {
		   return false;
	   }
   }
   
   
      //MANIPULATION PROCEDURES////////////////////////////////////////////////
   
   /*makeEmpty() : Sets List to empty state.
    * POST: isEmpty()
    */
   void makeEmpty() {
	   Node tmp;
	   while (front != null) {
		   this.deleteFront();
	   }
	   length = 0;
	   assert (this.isEmpty()) : "Implementation Error: makeEmpty failed\n";
   }
   
   /*moveTo(int i) : moves current element marker to position i in list.
    * PRE: i <= length() - 1 || i>= 0'
    */
   void moveTo(int i) {
	   if (0 <= i && i <= (length() - 1)) {
		   Node newcurr = front;
		   int newcount = 0;
		   while (newcount < i) {
			   newcurr = newcurr.next;
			   newcount++;
		   }
		   curr = newcurr;
	   } else {
		   curr = null;
	   }
	   
   }
   
   /*movePrev() : moves current element marker to position i in list
    * PRE: !isEmpty(), !offEnd
    */
   void movePrev() {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: movePrev() ---> empty List");
	   }
	   moveTo(getIndex() - 1);
   }
   
   /*moveNext() : moves current element marker to position i in list
    * PRE: !isEmpty(), !offEnd()
    */
   void moveNext() {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: moveNext() ---> empty List");
	   }
	   moveTo(getIndex() + 1);
   }
   
   /*prepend(int data) : inserts new element before front element
    * POST: !isEmpty();
    */
   void prepend(Object data) {
	   Node tmp = new Node(data);
	   if (this.isEmpty()) { front = back = tmp; }
	   else {
	       tmp.next = front;
	       front.prev = tmp;
	       front = tmp;
	   }
	   length++;
	   assert (!isEmpty()) : "Implementation Error: prepend() failed\n";
   }

   /*append(int data) : inserts new element after back element
    * POST: !isEmpty();
    */
   void append(Object data) {
	   Node tmp = new Node(data);
	   if (this.isEmpty()) { front = back = tmp; }
	   else {
		   tmp.prev = back;
		   back.next = tmp;
		   back = tmp;
	   }
	   length++;
	   assert (!isEmpty()) : "Implementation Error: prepend() failed\n";
   }
   
   /*insertBefore(int data) : inserts new element before current element
    * PRE: !isEmpty(), !offEnd();
    */
   void insertBefore(Object data) {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: deleteFront() ---> empty List");
	   }
	   
	   if (this.offEnd()) {
		   throw new RuntimeException("List Error: insertBefore() ---> null pointer\n");
	   }
	   
	   length++;

	   Node tmp = new Node(data);
	   tmp.next = curr;
	   if (curr != front) {
		   curr.prev.next = tmp;
		   tmp.prev = curr.prev;
	   }
	   curr.prev = tmp;
	   if (curr == front) front = tmp;
	   assert (!isEmpty()) : "Implementation Error: prepend() failed !isEmpty test\n";
	   assert (!offEnd()) : "Implementation Error: prepend failed !offEnd test\n";
   }
   
   /*insertAfter(int data) : inserts new element after current element
    * PRE: !isEmpty(), !offEnd();
    */
   void insertAfter(Object data) {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: insertAfter() ---> empty List");
	   }
	   if (this.offEnd()) {
		   throw new RuntimeException("List Error: insertAfter() ---> null pointer\n");
	   }
	   
	   length++;
	   Node tmp = new Node(data);
	   
	   tmp.prev = curr;

	   if (curr != back) {
		   curr.next.prev = tmp;
		   tmp.next = curr.next;
	   }
	   curr.next = tmp;
	   
	   if (curr == back) back = tmp;

	   assert (!isEmpty()) : "Implementation Error: prepend() failed\n";
	   assert (!offEnd()) : "Implementation Error: prepend failed !offEnd test\n";
   }
   
   /*deleteFront() : deletes front element
    * PRE: !isEmpty();
    */
   void deleteFront() {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: deleteFront() ---> empty List");
	   }
	   
	   length--;
	   if (front == back) {
	      front = null;
	   } else {
	      front = front.next;
		  front.prev = null;
	   }
   }
   
   /*deleteBack() : deletes back element
    * PRE: !isEmpty();
    */
   void deleteBack() {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: deleteFront() ---> empty List");
	   }
	   length--;
	   back = back.prev;
	   back.next = null;

   }
   
   /*delete() : deletes back element
    * PRE: !isEmpty() !offEnd(); POST: offEnd();
    */
   void delete() {
	   if (this.isEmpty()) {
		   throw new RuntimeException("List Error: deleteFront() ---> empty List");
	   }
	   if (this.offEnd()) {
		   throw new RuntimeException("List Error: current pointer is null\n");
	   }
	   
	   //If current pointer is not in the front or back
	   if (curr != front  && curr != back) {
	   
	      Node tmp1 = curr.prev;
		  Node tmp2 = curr.next;
	      tmp1.next = tmp2;
		  tmp2.prev = tmp1;
		  curr = null;
		  length--;
	   }
	   
	   //If current point is in front, call deleteFront()
	   if (curr == front) {
	      deleteFront();
	   }
	   
	   if (curr == back) {
	      deleteBack();
	   }
	   curr = null;
	   if (!this.offEnd()) {
		   throw new RuntimeException("List Error: delete() does not nullify curr\n");
	   }
   }
   
       //OTHER METHODS/////////////////////////////////////////////////////////
   
   /*copy() : returns new list identical to this list.  Curr is undefined in new list.*/
   List copy() {
	   List newlist = new List();
	   Node N = this.front;
	   
	   while (N != null) {
		   newlist.append(N.data);
		   N = N.next;
	   }
	   return newlist;
   }
}

/*Andrew Lien
 * ahlien
 * pa1
 * Tantalo
 *CS101
*/


class List{
  public enum position{FIRST, PREVIOUS, FOLLOWING, LAST};
  
  private class node{
  //fields
    int data;
    node prev;
    node next;
    //constructor
    node(int data){ this.data = data; next = null; prev = null; }
    //toString
    public String toString() { return String.valueOf(data); }
  }
  
  private node first = null;
  private node current = null;
  private node last = null;
  private int currentposition = 0;
  
  //Access functions
  public int length(){
    int position = 0;
    if(current == null){
      return 0;
    }
    node tmp = first;
    while(tmp!=null){
      position++;
      tmp = tmp.next;
    }
    return position;
  }
  
  public int getIndex(){
    int position = 0;
    if(current == null){
      return currentposition = -1;
    }
    node tmp = first;
    while(tmp!=current){
      position++;
      tmp = tmp.next;
    }
    currentposition = position;
    return currentposition;
  }
  
  public int front(){
    return first.data;
  }
  
  public int back(){
    return last.data;
  }
  
  public int getElement(){
    if(first == null) throw new java.util.NoSuchElementException();
    return current.data;
  }
  
  public boolean equals(List L){
    boolean flag = true;
    node N = this.first;
    node M = L.first;
    
      while(flag && N!=null){
        flag = (N.data == M.data);
        N = N.next;
        M = M.next;
      }
      return flag;     
  }  
  
  void clear(){
  node tmp = first;
  moveTo(0);
  while(tmp!=null){
    delete();
    moveNext();
    tmp = tmp.next;
    }
  }
  
  void moveTo(int i){
    node tmp = first;
    currentposition = 0;
    if( i <= length()-1 && i >= 0){
      for(int a = 0; a < i; a++){
        tmp = tmp.next;
        currentposition++;
      }
    }else{
       current = null;
     }
     current = tmp;
  }
  
  void movePrev(){
    if(current == first) {current = null; return;}
      current = current.prev;
    return;
  }
  
  void moveNext(){
    if(getIndex() == length()-1){ current = null;  return;}
      else {current = current.next; return;}
  }
  
  void prepend(int data) {
    node tmp = new node (data);
    //check for an empty list
    if(first == null){
      tmp.prev = null;
      tmp.next = null;
      first = tmp;
      last = tmp;
      current = tmp;
      return;
    }
    if(current == first){
      current.prev = tmp;
      tmp.next = current;
      tmp.prev = null;
      current = tmp;
      first = tmp;
    }
  }
  
  void append(int data){
    node tmp = new node(data);
    tmp.data = data;
    //check for an empty list
    if(first == null){
      tmp.prev = null;
      tmp.next = null;
      first = tmp;
      last = tmp;
      current = tmp;
      return;
    }
    if(current == last){
      current.next = tmp;
      tmp.prev = current;
      tmp.next = null;
      current = tmp;
      last = tmp;
    }
  }
  
  void insertBefore(int data){
    node tmp = new node(data);
    tmp.data = data;
    
    //check for an empty list
    if(first == null){
      tmp.prev = null;
      tmp.next = null;
      first = tmp;
      last = tmp;
      current = tmp;
      return;
    }
  
    //Take into account if current is first on list
    if(current == first){
      current.prev = tmp;
      tmp.next = current;
      tmp.prev = null;
      current = tmp;
      first = tmp;
    } else {
        node prev_curr = current.prev;
        current.prev = tmp;
        tmp.next = current;
        prev_curr.next = tmp;
        tmp.prev = prev_curr;
        current = tmp;
      }
  }
  
  void insertAfter(int data){
    node tmp = new node(data);
    tmp.data = data;
    
    //check for an empty list
    if(first == null){
      tmp.prev = null;
      tmp.next = null;
      first = tmp;
      last = tmp;
      current = tmp;
      return;
    }
  
  //take into account if current is last on list
    if(current == last){
      current.next = tmp;
      tmp.prev = current;
      tmp.next = null;
      current  = tmp;
      last = tmp;
    }else{
      node next_curr = current.next;
      current.next = tmp;
      tmp.prev = current;
      next_curr.prev = tmp;
      tmp.next = next_curr;
      current = tmp;
     }
  }
  
  void deleteFront(){
    node tmp = first.next;
    first = first.next;
    tmp.prev = null;
  }
  
  void deleteBack(){
    node tmp = last.prev;
    last = last.prev;
    tmp.next = null;
  }
  
  void delete(){
    node pre = current.prev;
    node next = current.next;
    if (current == null){ //no elements in the list
       System.err.println("no lines in file");
    }else if (first == last){
       first = null;
       last = null;
       current = null;
    }else if (current == first){//the current is the first node
       current = first.next; //let the current be next
       first = current; //let the first value be the current value
       current.prev = null; //let new current's prev point to null
    }else if (current == last){ //case if position is last
       current = last.prev;
       last = current;
       current.next=null;
    }else {//all other cases
       node afterCurrent = current.next; //create new node that will
       node prevCurrent = current.prev;//represent nodes to the LR
       prevCurrent.next = afterCurrent; 
       afterCurrent.prev = prevCurrent; 
       current = afterCurrent;
    }
  }
   List copy(){
    List Q = new List();
    node N = this.first;
  
    while(N!=null){
      Q.append(N.data);
      N=N.next;
    }
    return Q;
  }
  public String toString(){
    String str = " ";
    for(node curr = first; curr != null; curr=curr.next){
      str += curr.toString() + " ";
    }
    str = str.trim(); //trim the extra space at beginning
      return str;
  }
}

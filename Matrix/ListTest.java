 
public class ListTest {
	public static void main (String[] args) {		
		//Adding numbers to front
		System.out.println("prepend()");
		List outList = new List();
		outList.prepend(3);
		outList.prepend(2);
		outList.prepend(1);
		outList.prepend(69);
		
		//Print
		printList(outList);
		
		//Adding numbers to back
		System.out.println("append()");
		
		outList.append(45);
		outList.append(62);
		
		//Print
		printList(outList);
		
		System.out.println("moveTo() & insertBefore --");
		
		//Set current to front and add number
		outList.moveTo(0);
		outList.insertBefore(27);
		
		//Print
		printList(outList);
		
		//Add to front again
		System.out.println("prepend() ");
		outList.prepend(34);
		
		//Print
		printList(outList);
		
		//Move curr pointer twice then print.
		System.out.println("moveNext() & movePrev() & insertBefore");
		
		outList.moveNext();
		outList.moveNext();
		outList.insertBefore(95);
		printList(outList);
		
		//Delete front
		System.out.println("deleteFront() -- Delete element in front of list.");
		
		outList.deleteFront();
		printList(outList);
		
		//Delete back
		System.out.println("deleteBack() -- Delete element in back of list.");
		
		outList.deleteBack();
		printList(outList);
		
		//Delete current
		System.out.println("delete() --");
		
		
		outList.moveNext();
		outList.moveNext();
		outList.delete();
		printList(outList);
		
		//make a copy of a list and see if the new one is equal
		System.out.println("copy() -- Copy the list and compare new list to old one, see if it equals:");
		
		List outList2 = outList.copy();
		printList(outList2);
		
		if (outList2.equals(outList)) System.out.println("equals() -- expected true if printed");
		
		System.out.println("complete");
		System.exit(0);
	}
	
	public static void printList(List L) {
		System.out.println();
		System.out.println("The List in question:");
		L.moveTo(0);
		for (int ix = 0; ix < L.length(); ix++) {
			System.out.printf("%d ", L.getElement());
			if (ix < (L.length() - 1)) L.moveNext();
		}
		
		L.moveTo(0);
		System.out.println();
		System.out.println();
	}
}


import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.lang.*;
class Lex{
  public static void main(String[] args) throws IOException{
  if(args.length == 0 || args.length == 1){ System.out.println("Usage: Lex [input file] [output file]"); System.exit(1); }
    List aList = new List(); //creates a new list 
    //counts lines in file
    int linesRead = countLinesInFile(args); 
    //Create array w/ size linesRead
    String[] lexlist = new String[linesRead]; 
    //function to insert string elements in file to array
    lexlist = insertArray(lexlist,args[0]);
    //append array into a doubly linked list
    for(int i = 0; i < lexlist.length; i++){
      aList.append(i);
    }
    //sort array 
    sortArrayAndList(lexlist, aList, args);
  }
  
    
  static void sortArrayAndList(String[] lexlist, List aList, String[] args){
   //run through the length of the array
    for(int i = 0; i < lexlist.length; i++){
    //insertion sort algorithm similar to tantalo's pseudocode
      String tmp = lexlist[i];
      int a = i-1;
    //set the list to whatever 'i' is
      aList.moveTo(i);
    //save the element into an int
      int elem = aList.getElement();
     //insertion sort pseudocode
      while(a>=0 && tmp.compareTo(lexlist[a]) < 0){
     //since you've already saved the index, delete current element
        aList.delete();
      //move array to the right(orleft, im not sure) by one
        lexlist[a+1] = lexlist[a];
      //move the list cursor to the right(orleft im not sure) by one
        aList.movePrev();
      //pseudocode algorithm
        a = a-1; 
      //now, to preserve the list w/ the array, insert before where cursor is now
        aList.insertBefore(elem);
        //  System.out.println("Current position is " + aList.getIndex());
      }
      lexlist[a+1] = tmp;
    }
    //try to figure out how to move arrays & the list together.
    printArray(lexlist, args, aList);
    // System.out.println(aList);
  }
   
   
  static void printArray(String[] lexlist, String[] args, List aList){
  try{
      PrintWriter out = new PrintWriter(new FileWriter(args[1]));
          out.println("Sorted Indice List is: " + aList);
          aList.moveTo(0);
    for(int i = 0; i < lexlist.length; i++){
    // System.out.println(lexlist[i]);
      int a = aList.getElement();
      out.print("List[" + a + "] = ");
      out.println(lexlist[i]);
      aList.moveNext();
    }
    out.close();
  }catch(Exception e){
     System.err.println("Error: couldn't write file"); System.exit(1);
  }
  } 
  static String[] insertArray(String[] lexlist, String filename){
   try{
      //Open File
      FileInputStream fis = new FileInputStream(filename);
      //get the object of datainputstream
      DataInputStream in = new DataInputStream(fis);
      BufferedReader buffRead = new BufferedReader(
                                 new InputStreamReader(in));
      String currentLine;
      //read in file line by line
      int i = 0;
      while((currentLine = buffRead.readLine()) != null){
        lexlist[i] = currentLine;
        i++;
      }
     // System.out.println(i + " lines have been read");
      in.close();
      return lexlist;
      } catch(Exception e){
          System.err.println("Error opening file"); System.exit(1);
         }  
      return lexlist;
  } 
  static int countLinesInFile(String[] args){
    int linesReadInFile = 0;
    try{
      Scanner in = new Scanner(new File(args[0]));
      while(in.hasNextLine()){
        linesReadInFile++;
         String nextLine = in.nextLine();
      }
      in.close();
     } catch(Exception e){
         System.err.println("Error, can not read in file"); System.exit(1);
       }
     return linesReadInFile;  
   }   
   
}  
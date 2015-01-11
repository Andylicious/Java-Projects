/* Andrew Lien
 * ahlien
 * CMPS 101 pa 3
 * 
 */
import java.io.*;
import java.util.Scanner;

public class Sparse {
  public static void main (String[] args) throws IOException {
		
  //FILEWRITER INITALIZATION
  PrintWriter fileOutput = new PrintWriter(new FileWriter(args[1]));
		
  //SCANNER VARIABLES//
  String line = null;
  String[] inputVar = null;
  Scanner fileinput = new Scanner(new File(args[0]));
		
  //INPUT VARIABLES//
  int collectedSize = 0;
  int AnonZero = 0;
  int BnonZero = 0;
		
		
  //check for file argument.  if none exists, exit program
  if(args.length < 2){
    System.out.printf("Usage: FileIO infile outfile%n");
	System.exit(1);
  }
		
		
  //scan first line for variables (assume properly formatted input
  line = fileinput.nextLine();
  inputVar = line.split("\\s+"); //split line around whitespace
  collectedSize = Integer.parseInt(inputVar[0]);
  AnonZero = Integer.parseInt(inputVar[1]);
  BnonZero = Integer.parseInt(inputVar[2]);
		
		//CREATE MATRICES FROM INPUT FILE
  Matrix A = createMatrix(collectedSize, AnonZero, fileinput, line, inputVar);
  Matrix B = createMatrix(collectedSize, BnonZero, fileinput, line, inputVar);
  Matrix SM  = A.scalarMult(1.5);
  Matrix AM = B.add(A);
  Matrix Acop = A.copy();
  Matrix Bcop = B.copy();
  Matrix AA = A.add(Acop);
  Matrix BsubA = B.sub(A);
  Matrix AsubA = A.sub(Acop);
  Matrix TA = A.transpose();
  Matrix MAB = A.mult(B);
  Matrix MBB = B.mult(Bcop);
  printOutput(A, B, SM, AM, AA, BsubA, AsubA, TA, MAB, MBB, fileOutput);
		
  //close scanner and printwriter
  fileinput.close();
  fileOutput.close();
  System.exit(0);
  }
	
	
	// createMatrix() : returns new matrix with size and number of lines
	//    specified in file
  static Matrix createMatrix(int collectedSize, int NNZ, Scanner fileinput, String line, String[] inputVar) {
  Matrix M = new Matrix(collectedSize);
  int i, j;
  double x;
  line = fileinput.nextLine(); //skip blank line
		
  //get inputVars in the line, and place them in the matrix
  for(int ix = 0; ix < NNZ; ix++) {
 	inputVar = null;
	line = fileinput.nextLine()+" ";
	//System.out.println(line);
	inputVar = line.split("\\s+");
	i = Integer.parseInt(inputVar[0]);
	j = Integer.parseInt(inputVar[1]);
	x = Double.parseDouble(inputVar[2]);
	//System.out.printf("INSERT: %dth row,  %dth column, %f value\n", i, j, x); DEBUG
	M.changeEntry(i, j, x);
    }
	return M;
  }
	
	//void printOutput() : prints the entire output for PA3 specifications, using each
	// matrix as an argument
	static void printOutput(Matrix A, Matrix B, Matrix SM, Matrix AM, Matrix AA, 
			Matrix BsubA, Matrix AsubA, Matrix TA, Matrix MAB, Matrix MBB, PrintWriter fileOutput) {
		
		//PRINT THE NON-ZERO ENTRIES OF EACH MATRIX//
		fileOutput.printf("A has %d non-zero entries:\n", A.getNNZ());
		printSparse(A, fileOutput);
		
		fileOutput.printf("\nB has %d non-zero entries:\n", B.getNNZ());
		printSparse(B, fileOutput);
		
		fileOutput.printf("\n(1.5)*A =\n");
		printSparse(SM, fileOutput);
		
		fileOutput.printf("\nA+B =\n");
		printSparse(AM, fileOutput);
		
		fileOutput.printf("\nA+A =\n");
		printSparse(AA, fileOutput);
		
		fileOutput.printf("\nB-A =\n");
		printSparse(BsubA, fileOutput);
		
		fileOutput.printf("\nA-A =\n");
		printSparse(AsubA, fileOutput);
		
		fileOutput.printf("\nTranspose(A) =\n");
		printSparse(TA, fileOutput);
		
		fileOutput.printf("\nA*B =\n");
		printSparse(MAB, fileOutput);
		
		fileOutput.printf("\nB*B =\n");
		printSparse(MBB, fileOutput);
	}
	
	static void printSparse(Matrix M, PrintWriter fileOutput) {
		for (int itor = 1; itor < M.getSize() + 1; itor++) {
			
			//if there are entries in the row, print them out
			if (M.row[itor].length() > 1) {
				fileOutput.printf("%d: ", itor);
				for (int itor2 = 1; itor2 < M.row[itor].length(); itor2++) {
					fileOutput.printf("(%d, ", M.getCol(itor, itor2));
					fileOutput.printf("%.1f) ", M.getEntry(itor, itor2));
				}
			} else { continue; }
			fileOutput.println();
			continue;
		}
	}
}	//Andrew Lien

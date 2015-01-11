/* Andrew Lien
 * ahlien
 * CMPS 101 pa 3
 * 
 */
public class Matrix {
  /*BEING PRIVATE CLASS*/
  private class Entry {
    private int col;
    private double val;
		
    Entry(int x, double y) {
      col = x;
	  val = y;
    }
		
    // getEntry() return Entry val (double)
    double getEntry() {
	  return val;
	}
		
    // getcol() : return col val of Entry (int)
    int getcol() {
	  return col;
    }
	  //note: still can't get equals to work for some reason.
   public boolean equals(Object obj){
     if(!(obj instanceof Entry)) return false;
     if(obj == this) return true;
     Entry E = (Entry)obj;
     if(this.col == E.col && this.val == E.val) return true;
     return false;
    }
    public String toString(){
      String str = "(";
      str += String.valueOf(this.col) + ", ";
      str += String.valueOf(this.val) + ")";
     return str;
    }    
  }
   /*END PRIVATE CLASS*/	
	
   /*BEGIN FIELDS*/	
	private int size;
	private int NNZ = 0;
	List row[]; 
   /*END FIELDS*/

   /*BEGIN MATRIX CONSTRUCTION*/	
	Matrix(int n) {
		if (n < 1) {
			throw new RuntimeException("Matrix error: invalid input size");
		}
		
		size = n;
		row = new List[size+1];
		//array of list creation

		//create 0th blank entry for each row in list (0th row never used)
		this.makeZero();
	}
   /*END MATRIX CONSTRUCTION */	

	/*BEGIN ACCESS FUNCTIONS*/
	//  int getSize() : returns dimension of matrix (square)
	int getSize() {
		return size;
	}
	
	//getNNZ() : returns number of non-zero elements in matrix
	int getNNZ() {
//	System.out.println("The NNZ is " + NNZ);
		return NNZ;
	}
	
	//equals() : returns true if matrices are equal *NOT FINISHED*
	public boolean equals(Object obj) {
    if ( !(obj instanceof Matrix) ) return false;
    if (obj == this) return true;
    Matrix M = (Matrix)obj;
    
    boolean flag = true;
    List list1;
    List list2;
    if (this.size == M.size && this.NNZ == M.NNZ) {
      int i = 0;
      list1 = this.row[i];
      while ( flag && list1 != null && i < size ) {
        list1 = this.row[i];
        list2 = M.row[i];
        flag = list1.equals(list2);
        ++i;
      }
      return flag;
    }
    return false;
	}
	
	//
	int getCol(int i, int position) {
		int result;
		row[i].moveTo(position);
		Entry tmpEntry = (Entry)row[i].getElement();
		result = tmpEntry.getcol();
		row[i].moveTo(0);
		return result;
	}
	
	
	//getEntry() : matrix method to return entry val (double)
	double getEntry(int i, int position) {
		double result;
		row[i].moveTo(position);
		Entry tmpEntry = (Entry)row[i].getElement();
		result = tmpEntry.getEntry();
		row[i].moveTo(0); //reset curr pointer
		return result;
	}
	/*END ACCESS FUNCTIONS*/ 
	
	/*BEGIN MANIPULATION FUNCTIONS*/
	//void makeZero : makes matrix into the zero state
	void makeZero() {
		//set all vals to null
		for (int i = 0; i < size + 1; i++) {
			this.row[i] = null;
			Entry tmpentry = new Entry(0, 0);
			List tmplist = new List();
			tmplist.prepend(tmpentry);
			row[i] = tmplist;
		}
		NNZ = 0;
	}
	
	//Matrix copy() : returns new identical matrix
	Matrix copy() {
		Matrix matrixCopy = new Matrix(size);
		
		//for every row...
		for (int currRow = 1; currRow < size + 1; currRow++) {
			matrixCopy.row[currRow] = null;
			matrixCopy.row[currRow] = this.row[currRow].copy();
			matrixCopy.setNNZ(this.getNNZ()); //set NNZ val
		}
		
		return matrixCopy;
	}
	
	//void changeEntry(int i, int j, double x) : changes entry in matrix ADT
	void changeEntry(int i, int j, double x) {
	  /*check for valid input*/
	  if (i < 1 || i > this.getSize()) {
	    throw new RuntimeException("Matrix error: changeEntry calls out of bounds of matrix\n");
	   }
		
		//find position to insert
		Entry newEntry;
		Entry currEntry;
		for (int itor = 0; itor < row[i].length(); itor++) {
			
			//move curr pointer of row to itor
			row[i].moveTo(itor);
			currEntry = (Entry)row[i].getElement();
			
			// if nothing has been added to the front yet,
			// or no larger col has been found
			if (row[i].length() == (itor + 1) && currEntry.getcol() < j && x != 0.0) {
				newEntry = new Entry(j, x);
				row[i].append(newEntry);
				NNZ++;
				break;
			}
			
			// if cols are equal, replace node with
			// new node
			if (currEntry.getcol() == j) {
				newEntry = new Entry(j, x);
				
				//if cols are equal and curr points to rear node
				if (row[i].getIndex() == row[i].length() - 1) {
					row[i].deleteBack();
					
					//if cols are equal and there is a zero entry
					if (x != 0.0) { 
						row[i].append(newEntry); 
					} else {
						NNZ--;
					}
				} else {
					row[i].delete();
					if (x != 0.0) {
						row[i].moveTo(itor); //reset curr pointer
						row[i].insertBefore(newEntry);
					} else {
						NNZ--;
					}
				}
				//NNZ unaffected if x != 0.0
				break;
			}
			
			// if col found is greater than input
			if (currEntry.getcol() > j && x != 0.0) {
				newEntry = new Entry(j, x);
				row[i].insertBefore(newEntry);
				NNZ++;
				break;
			}
		}	
	}
		//dot() : returns double of vector dot product of two lists
	private static double dot(List P, List Q) {
		double answer = 0;
		
		//traverse through list P, find identical cols in Q
		for (int pitorator = 0; pitorator < P.length(); pitorator++) {
			P.moveTo(pitorator);
			for (int qtorator = 0; qtorator < Q.length(); qtorator++) {
				Q.moveTo(qtorator);
				Entry PE = (Entry)P.getElement();
				Entry QE = (Entry)Q.getElement();
				
				//check cols, if equal add product to answer
				if (PE.getcol() == QE.getcol()) {
					answer += PE.getEntry() * QE.getEntry();
				}
			}
		}
		
		return answer;
	}
	
	//Matrix scalarMult(double x) : returns new matrix that is a multiple of x
	Matrix scalarMult(double x) {
		Matrix scalarMatrix = new Matrix(this.getSize());
		
		//iterate through rows, insert new entry
		//of x
		for (int itor = 1; itor < size + 1; itor++) {
			if (this.row[itor].length() == 1) { continue; }
			
			//iterate through cols, insert new entry
			for (int icol = 1; icol < this.row[itor].length(); icol++) {
				this.row[itor].moveTo(icol);
				Entry tmpEntry = (Entry)this.row[itor].getElement();
				scalarMatrix.changeEntry(itor, tmpEntry.getcol(), tmpEntry.getEntry() * x);
			}
		}
		return scalarMatrix;
	}
	
	//Matrix add(Matrix M) : returns new matrix that is the addition of this and M
	Matrix add(Matrix M) {
		
		//validate dimensions
		if (M.getSize() != this.getSize()) {
			throw new RuntimeException("Matrix error: two matrices added with different dimensions\n");
		}
		Matrix addMatrix = this.copy();
		
		//entries for comparison
		Entry thisEnt, mEnt;
		
		//iterator row
		for (int irow = 1; irow < M.getSize() + 1; irow++) {
			//if nothing exists..
			if (M.row[irow].length() == 1) { continue; }
			
			//FOR EVERY col
			for (int icol = 1; icol < M.row[irow].length(); icol++) {
				M.row[irow].moveTo(icol);    //move M pointer to proper col
				
				//ITERATE 
				for (int thisCol = 1; thisCol < this.row[irow].length(); thisCol++) {
					this.row[irow].moveTo(thisCol);  //move this pointer to proper col
					
					//compare 
					thisEnt = (Entry)this.row[irow].getElement();
					mEnt = (Entry)M.row[irow].getElement();
					
					//check for eq col
					if (thisEnt.getcol() == mEnt.getcol()) {
						double newEntry = thisEnt.getEntry() + mEnt.getEntry();
						addMatrix.changeEntry(irow, mEnt.getcol(), newEntry);
						break;
					}
					
					//if col in m dne
					if (thisEnt.getcol() > mEnt.getcol()) {
						addMatrix.changeEntry(irow, mEnt.getcol(), mEnt.getEntry());
						break;
					}
					
					//check for entries
					if (thisCol == (this.row[irow].length() - 1) && thisCol < M.row[irow].length() - 1) {
						thisCol++;
						while (thisCol < M.row[irow].length()) {
							addMatrix.changeEntry(irow, mEnt.getcol(), mEnt.getEntry());
							thisCol++;
						}
					}
				}
			}
		}
		return addMatrix;
	}


	//Matrix transpose() : returns new matrix that is transpose of current matrix
	
	Matrix transpose() {
		Matrix transMatrix = new Matrix(this.getSize());
		
		//for every row in current matrix, insert col in row and row in col
		for (int irow = 1; irow < this.getSize() + 1; irow++) {
			
			for (int icol = 1; icol < this.row[irow].length(); icol++) {
				this.row[irow].moveTo(icol);
				Entry tmpEntry = (Entry)this.row[irow].getElement();
				transMatrix.changeEntry(tmpEntry.getcol(), irow, tmpEntry.getEntry());
			}
			
		}
		return transMatrix;
	}
		//Matrix sub(Matrix M) : returns new matrix that is the subtraction of this and M
	Matrix sub(Matrix M) {
		
		//validate dimensions
		if (M.getSize() != this.getSize()) {
			throw new RuntimeException("Matrix error: two matrices added with different dimensions\n");
		}
		Matrix subMatrix = this.copy();
		
		//entries for comparison
		Entry thisEnt, mEnt;
		
		
		for (int irow = 1; irow < M.getSize() + 1; irow++) {
			//if nothing exists
			if (M.row[irow].length() == 1) { continue; }
			//FOR EVERY col IN M
			for (int icol = 1; icol < M.row[irow].length(); icol++) {
				M.row[irow].moveTo(icol);    //move M pointer to proper col
				
				//ITERATE 
				for (int thisCol = 1; thisCol < this.row[irow].length(); thisCol++) {
					this.row[irow].moveTo(thisCol);  //move this pointer to proper col
					
					//compare the entires
					thisEnt = (Entry)this.row[irow].getElement();
					mEnt = (Entry)M.row[irow].getElement();
					
					//if cols are equal
					if (thisEnt.getcol() == mEnt.getcol()) {
						double newEntry = thisEnt.getEntry() - mEnt.getEntry();
						subMatrix.changeEntry(irow, mEnt.getcol(), newEntry);
						break;
					}
					
					//if col in M DNE
					if (thisEnt.getcol() > mEnt.getcol()) {
						subMatrix.changeEntry(irow, mEnt.getcol(), 0 - mEnt.getEntry());
						break;
					}
					
					//check for entries
					if (thisCol == (this.row[irow].length() - 1) && thisCol < M.row[irow].length() - 1) {
						thisCol++;
						while (thisCol < M.row[irow].length()) {
							subMatrix.changeEntry(irow, mEnt.getcol(), 0 - mEnt.getEntry());
							thisCol++;
						}
					}
				}
			}
		}
		return subMatrix;
	}
	//Matrix mult(Matrix M) : returns new matrix that is mult of current matrix and M
	
	Matrix mult(Matrix M) {
		Matrix multMatrix = new Matrix(this.getSize());
		
		//transpose matrix M
		Matrix transM = M.transpose();
		
		//traverse through rows, take dot product, and insert
		for (int trow = 1; trow < this.getSize() + 1; trow++) {
			if (this.row[trow].length() == 1) {
				continue;
			} else {
				for (int mrow = 1; mrow < transM.getSize() + 1; mrow++) {
					multMatrix.changeEntry(trow, mrow, dot(this.row[trow], transM.row[mrow]));
				}
			}
		}
		
		return multMatrix;
	}
	
	
	//void setNNZ() : for matrix copy fcn, to set nnz fcn
	void setNNZ(int inputNNZ) {
		this.NNZ = inputNNZ;
	}
	
/*END MANIPULATION FUNCTIONS*/

//other other other
	  public String toString(){
    String str = ""; 
    for(int i = 2; i <=size; ++i){
      if(row[i-1].length()>1){
        str += String.valueOf(i-1) + ": ";
        str += row[i-1].toString() + "\n";
    //    System.out.println(str + "is the str");
      }
      //only for nonzero vals
    
    }
   return str;
  }
  
}


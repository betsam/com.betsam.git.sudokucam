package com.betsamsoft.sudokucam.algorithms;



public abstract class SudokuSolverClass {
	
	int[][] mRiddleMatrix;	// matrix with the matrix to solve (empty fields have to be zero)
	int[][] mCheckMatrix;	// matrix to solve and check
	boolean[][] mImmutableMatrix; // matrix which marks the given fields
	int[][] mSolutionMatrix;	// matrix only with the solution fields (given fields are zero)
	boolean mRiddleMatrixCorrupt;	// control flags states if RiddleMatrix is corrupt/unsolvable

	int mDim;	// array dimension
	int mMax;	// maximum array index

	
	/**
	 * Constructor
	 * 
	 * prepares the riddle matrix, the check matrix and the immutable matrix
	 * 
	 * @param _RiddleMatrix
	 */
	public SudokuSolverClass(int[][] _RiddleMatrix) {
		mRiddleMatrix = clone2DArray(_RiddleMatrix);	// save RiddleMatrix
		mCheckMatrix = clone2DArray(_RiddleMatrix);	// prepare Checkmatrix
		mImmutableMatrix = initImmutableMatrix(_RiddleMatrix); // mark given fields

		mDim = mCheckMatrix.length;	// array dimension
		mMax = mDim-1;	// maximum array index

		mRiddleMatrixCorrupt =! checkMatrix();		// check if RiddleMatrix is ok
	}
	
	
	/**
	 * Attention!: 
	 * The clone() command does work for deep copies with one dimensional arrays
	 * but not with two dimensional arrays! Therefore we have to copy them our self!
	 * 
	 * @param  _orig	2 dim array
	 * @return clone 	2 dim array (deep copy)
	 */
	public int[][] clone2DArray(int[][] _orig) {
		int dim = _orig.length;
		int[][] clone = new int[dim][];
		for(int i=dim-1; i>=0; i--) {
			clone[i] = _orig[i].clone();
		}
		return clone;
	}
	
	
	/**
	 * Initialize Immutable Matrix
	 * 
	 * This function returns a boolean Matrix in which all given fields are marked with "true"
	 * all other fields are marked with "false". This shall help us for a quick check whether a
	 * particular field is protected or not.
	 * 
	 * @param 	_riddleMatrix:	2dim given matrix
	 * @return	bMatrix:		2dim immutable matrix
	 */
	private boolean[][] initImmutableMatrix(int[][] _riddleMatrix) {
		int dim = _riddleMatrix.length;
		
		boolean[][] bMatrix = new boolean[dim][dim];
		
		// check each entry for values bigger than zero and mark them in the immutable Matrix
		int max = dim-1;
		for(int row=max; row>=0; row--) {
			for(int col=max; col>=0; col--) {
				if(_riddleMatrix[row][col] != 0) {
					bMatrix[row][col] = true;
				} else {
					bMatrix[row][col] = false;
				}
			}
		}
		return bMatrix;
	}
	

	/**
	 * Fill Solution Matrix
	 * 
	 * Copies all the solved fields from the check matrix to the solution matrix except the
	 * given/protected fields.
	 * 
	 */
	protected void fillSolutionMatrix() {
		mSolutionMatrix = new int[mDim][mDim];
		
		// check each entry for values bigger than zero and mark them in the immutable Matrix
		for(int row=mMax; row>=0; row--) {
			for(int col=mMax; col>=0; col--) {
				if(mImmutableMatrix[row][col] == false) {
					mSolutionMatrix[row][col] = mCheckMatrix[row][col];
				} else {
					mSolutionMatrix[row][col] = 0;
				}
			}
		}
	}
	
	
	/**
	 * Check if Sudoku is solved successfully
	 * 
	 * 1. checks if all CheckMatrix cells are not zero anymore
	 * 2. checks if each cell is valid
	 * 
	 * @return true if solved, otherwise false
	 */
	protected boolean checkIfSolved() {
				
		// 1. check if current cell value is zero
		for(int row=mMax; row>=0; row--) {
			for(int col=mMax; col>=0; col--) {
				
				if(mCheckMatrix[row][col] == 0) {
					return false;
				}
			}
		}
		
		// 2. check if all cell values are valid
		return checkMatrix();
	}
	
	
	/**
	 * Check Sudoku rules at given index!
	 * 
	 * Approves if the requested value to check is valid at the requested index, i.e. the
	 * checked value does not violate the Sudoku rules!
	 * 
	 * \note: it is obvious that the row, column and section loops are identical
	 * 		  and therefore all checks could be realized within the same loop
	 * 		  but like this the tasks are nicely separated and the influence on the
	 * 		  timing is negligible.
	 * 
	 * @param _row:	row index in check matrix
	 * @param _col: column index in check matrix
	 * @param _val:	value to check (within check matrix)
	 * @return true if the value is valid, otherwise false
	 */
	public boolean checkCell(int _row, int _col, int _val) {
				
		// check row
		for( int i=mMax; i>=0; i--) {
			if(mCheckMatrix[_row][i] == _val) { return false; }
		}
		
		// check column
		for( int i=mMax; i>=0; i--) {
			if(mCheckMatrix[i][_col] == _val) { return false; }
		}	
		
		// check section
		for( int i=mMax; i>=0; i--) {
			if(mCheckMatrix[(_row/3)*3+(i/3)][(_col/3)*3+(i%3)] == _val) { return false; }
		}
		
		// if we got here the entry is valid
		return true;
	}
	
		
	/**
	 * Check if CheckMatrix is ok
	 * 
	 * @return	:	true if CheckMatrix is ok, otherwise false
	 */
	public boolean checkMatrix() {
		
		for(int row=mMax; row>=0; row--) {
			for(int col=mMax; col>=0; col--) {
				
				if(mCheckMatrix[row][col] != 0) {
					
					// for the check we need to clear the current cell in the matrix
					// otherwise we get always fail from the check() function because
					// check() includes the current position
					int cell_val = mCheckMatrix[row][col];
					mCheckMatrix[row][col] = 0;
					
					if( checkCell(row,col, cell_val) == false ) {
						// undo the previous clear
						mCheckMatrix[row][col] = cell_val;
						return false;
					}
					
					// undo the previous clear
					mCheckMatrix[row][col] = cell_val;
				}
			}
		}
		return true;
	}
	
	
	public abstract boolean solve();
}

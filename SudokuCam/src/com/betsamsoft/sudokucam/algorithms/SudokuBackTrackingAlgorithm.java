package com.betsamsoft.sudokucam.algorithms;

import android.util.Log;


public class SudokuBackTrackingAlgorithm extends SudokuSolverClass {

	public SudokuBackTrackingAlgorithm(int[][] _RiddleMatrix) {
		super(_RiddleMatrix);
	}
	
	
	/**
	 * Solve the Sudoku with self made Backtracking Algorithm
	 * 
	 * \todo: condition to check if the algorithm got stuck in an endless loop is missing!
	 */
	@Override
	public boolean solve() {
		
		// Our class constructor checks if the given RiddleMatrix is ok or corrupt.
		// if the RiddleMatrix is corrupt we don't even have to try
		if( mRiddleMatrixCorrupt == true ) {
			Log.d("SudokuBruteForceArray", "RiddleMatrix corrupt/undsolvable!");
			return false;
		}

		
		boolean res = true;	// to store the last result of the check function
		
		// Check all fields within the check matrix.
		// 
		// 1. Skip all given/immutable fields
		// 2. if we are on the way back check if field maximum reached
		// 3. If there is a possible solution for the current field store the number and
		// 	  go to the next field. 
		// 4. If there is no possible solution for the current field
		// 	  reset the current field to zero and go one field backwards and increase the number, aso.
		for(int row=mMax; row>=0; row--) {
			for(int col=mMax; col>=0; col--) {
				
				// 1. skip Immutable Fields
				if( mImmutableMatrix[row][col] == true ) {
					
					// check if we go backwards in the list
					if( res == true ) {
						continue; 
					} else {
						col+=1;
						if(col > mMax) {
							col=0;
							row+=1;
							if(row > mMax) {
								// if we end here the sudoku has no valid solution
								return false;
							}
						}
						// lets start again from the top
						// because the next field could be a immutable field too
						col+=1;
						continue;
					}
				}
				
				// 2. check if field-maximum reached
				// e.g. if we just went back to a field which has already
				// 9 in it
				if( mCheckMatrix[row][col] > 8 ) {
					mCheckMatrix[row][col] = 0;
					col+=1;
					if(col > mMax) {
						col=0;
						row+=1;
						if(row > mMax) {
							// if we end here the sudoku has no valid solution
							return false;
						}
					}
					
					// lets start again from the top
					// because this could be a immutable field now
					col+=1;
					continue;
				}
				
				
				// 3. increment field and check
				res = false;
				for(int i=mCheckMatrix[row][col]+1; i<=9; i++) {
					res = checkCell(row,col,i);
					if( res == true) {
						mCheckMatrix[row][col] = i;
						break;
					}
				}
				
				// 4. go one step back if check failed
				if(res == false) {
					mCheckMatrix[row][col] = 0; 
					col+=1;
					if(col > mMax) {
						col=0;
						row+=1;
						if(row > mMax) {
							return false;
						}
					}
					// lets start again from the top
					col+=1;
					continue;
				}
			}
		}
		
		// Check if Sudoku solved successfully
		if( checkIfSolved() == false ) {
			return false;
		}
		
		// Sudoku solved successfully!
		// fill solution matrix
		fillSolutionMatrix();
		return true;
	}
}

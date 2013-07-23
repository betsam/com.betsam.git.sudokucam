package com.betsamsoft.sudokucam.algorithms;

import android.util.Log;


public class SudokuBackTrackingRecursiveAlgorithm extends SudokuSolverClass {

	public SudokuBackTrackingRecursiveAlgorithm(int[][] _RiddleMatrix) {
		super(_RiddleMatrix);
	}
	
	
	/**
	 * Solve the Sudoku with recursive Backtrack Algorithm
	 */
	@Override
	public boolean solve() {
		
		// Our class constructor checks if the given RiddleMatrix is ok or corrupt.
		// if the RiddleMatrix is corrupt we don't even have to try
		if( mRiddleMatrixCorrupt == true ) {
			Log.d("SudokuBruteForceArray", "RiddleMatrix corrupt/undsolvable!");
			return false;
		}

		// start condition for recursive solver, lets go!
		solveRecursive(mMax,mMax);
				
		
		// Check if Sudoku solved successfully
		if( checkIfSolved() == false ) {
			return false;
		}
		
		// Sudoku solved successfully!
		// fill solution matrix
		fillSolutionMatrix();
		return true;
	}
	
	
	/**
	 * Solve Sudoku Recursively
	 * 
	 * @param row
	 * @param col
	 * 
	 * @return true if valid number found or finished, false otherwise
	 */
	public boolean solveRecursive(int row, int col) {
		
		// check array index
		if( col < 0 ) {
			
			// go to next line
			row--;
			col=mMax;
			
			if(row < 0) {
				// Sudoku end reached
				return true;
			}
		}
			
		// check if current cell is given
		if( mCheckMatrix[row][col] > 0 ) {
			// go to next cell
			return solveRecursive(row,col-1);
		}
		
		// current cell is empty
		// try all numbers beginning with '1'
		for(int i=1; i<=9; i++) {
			if( checkCell(row,col,i) == true ) {
				// we found a possible solution for the current cell
				mCheckMatrix[row][col] = i;
				// go to next cell
				if( solveRecursive(row,col-1) == true ) {
					return true;
				}
			}
		}
		
		// there is no valid number for the current cell
		// clear cell again and go one step back
		mCheckMatrix[row][col] = 0;
		return false;
	}
}

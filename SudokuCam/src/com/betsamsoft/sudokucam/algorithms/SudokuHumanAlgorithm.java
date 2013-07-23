package com.betsamsoft.sudokucam.algorithms;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import android.util.Log;

import com.betsamsoft.sudokucam.utils.My3DArrayList;



public class SudokuHumanAlgorithm extends SudokuSolverClass {

	My3DArrayList mValidNumberList;
	TreeSet<Integer> mSectorChangeList;	// we use TreeSet because we wan't each sector marked only once
	
	
	public SudokuHumanAlgorithm(int[][] _RiddleMatrix) {
		super(_RiddleMatrix);
	}
	
	
	/**
	 * Solve the Sudoku with the Human Algorithm
	 * 
	 * \todo: condition to check if the algorithm got stuck in an endless loop is missing!
	 */
	@Override
	public boolean solve() {
		
		// Our class constructor checks if the given RiddleMatrix is ok or corrupt.
		// if the RiddleMatrix is corrupt we don't even have to try
		if( mRiddleMatrixCorrupt == true ) {
			Log.d("SudokuHumanAlgorithm", "RiddleMatrix corrupt/undsolvable!");
			return false;
		}
		
		
		
		// 1. Create dynamic List with all valid entries for each Sudoku cell
		//		- for all given numbers (immutable) and found unique solutions the list will be the 'null'-pointer
		mValidNumberList = new My3DArrayList(mDim,mDim,0);
		
		for(int row=mMax; row>=0; row--) {
			for(int col=mMax; col>=0; col--) {
				
				// skip Immutable Fields
				if( mImmutableMatrix[row][col] == true ) {
					continue; 
				}
				
				// create List with all valid numbers for the current cell
				for(int i=1; i<=9; i++) {
					if( checkCell(row,col,i) == true ) {
						mValidNumberList.add(row, col, i);
					}
				}
			}
		}
		
		// 2. Process Data
		//		- if the list has a length of '1' a unique solutions has been found!
		
		// Create Sector change List
		mSectorChangeList = new TreeSet<Integer>();
		
		for(int row=mMax; row>=0; row--) {
			for(int col=mMax; col>=0; col--) {
				
				// 3. if the current list has a length of '1' we already found the solution of the current cell
				checkListSize(row, col);
				
				// 4. check if there is a unique number in the current Sector
				if( (row%3 == 2) && (col%3 == 2) ) {
					checkSectorSolution(row, col);
				}
				
				// 5. Check if any sector changed
				while( mSectorChangeList.isEmpty() == false) {
					Integer s = mSectorChangeList.first();
					
					// recalculate sector information -> see addToSectorChangeList() if you are confused about the mDim!
					int rS = s/mDim;
					int cS = s%mDim;
					
					// calculate coordinates for checkmatrix
					int r = (rS*3);
					int c = (cS*3);
					checkSectorSolution(r,c);
					
					// remove processed entry
					mSectorChangeList.remove(s);
				}
			}
		}
		
		// 6. Check if Sudoku solved successfully
		if( checkIfSolved() == false ) {
			return false;
		}
		
		// Sudoku solved successfully!
		// fill solution matrix
		fillSolutionMatrix();
		return true;
	}
	
	
	/**
	 * Add Item which represents one of the sectors to the mSectorChangeList
	 * to tell the main loop in which sectors have been made any changes.
	 * 
	 * @param _row	:	current row
	 * @param _col	:	current column
	 */
	private void addToSectorChangeList(int _row, int _col) {
		
		// calculate sector
		int rS = (_row/3);
		int cS = (_col/3);
		
		// because mSectorChangeList is a TreeSet which does not allow ArrayLists we have to create 
		// an enumerable type. The costs are that we have to calculate rS and cS after extracting the created type.
		Integer s  = rS*mDim+cS;
		
		// add to SectorChangeList
		mSectorChangeList.add(s);
	}
	
	
	/**
	 * Check All ValidNumberLists within the current Sector
	 * 
	 * Important!
	 * Make sure that this command is only called if all nine cells within the specific Sector have
	 * a ValidNumberList, otherwise this function produces errors in the Sudoku!
	 * 
	 * 
	 * If there is a cell with a unique number in its ValidNumberList then this unique number
	 * is the solution this particular cell 
	 * 
	 * 1. To evaluate if there exists a unique number in the current sector we count all the number 'one' the 'twos' of
	 * all ValidNumberLists within this particular sector 
	 * 
	 * 2. We check if we there exists a number which we counted only once
	 * 		if yes we have to figure out which one. To do so we use the remove function which works only with 
	 * 		the List we are looking for.
	 * 
	 * 3. Once found a solution, we store the solution in the checkMatrix and remove its value from all affected
	 * 		ValidNumberLists.
	 * 
	 * @param _row	:	current row
	 * @param _col	:	current column
	 */
	private void checkSectorSolution(int _row, int _col) {

		// prepare empty list
		//List<Object> sectorList = new ArrayList<Object>(); 
		int[] sectorCount = new int[mDim];

		// create sectorList with all Lists from the current sector
		for(int i=mMax; i>=0; i--) {
			
			int r = (_row/3)*3+(i/3);
			int c = (_col/3)*3+(i%3);
			
			List<Object> cellValidNumberList = mValidNumberList.get(r, c);
			
			Iterator<Object> sectorCountItr = cellValidNumberList.iterator();
			while( sectorCountItr.hasNext() ) {
				Integer val = (Integer) sectorCountItr.next();
				if( val != null) {
					sectorCount[val-1]++;
				}
			}
		}
		
		// Search for all possible numbers of current sector
		int sectorSolution = 0;
		
		for(int i=mMax; i>=0; i--) {
			
			if( sectorCount[i] == 1 ) 
			{
				// we know that there is a solution in this sector and its value but not for which specific cell
				sectorSolution = i+1;
				
				// find cell within sector
				for(int j=mMax; j>=0; j--) 
				{	
					int r = (_row/3)*3+(j/3);
					int c = (_col/3)*3+(j%3);
					
					if( mValidNumberList.get(r,c).remove((Integer) sectorSolution) == true ) {
						// cell found
						mCheckMatrix[r][c] = sectorSolution;
						mValidNumberList.get(r,c).clear();
						
						// Check all previous list which this cell affects and remove the found solution from the lists
						removeCellSolutionFromValidNumberList(r, c, sectorSolution);
					}
				}
			}
		}
	}
	
	
	/**
	 * Check List Size
	 * 
	 * If current CellValidNumberList size equals '1' then we found the solution of the current cell!
	 * We then write the found cell solution in the CheckMatrix and remove it from all previous ValidNumberLists
	 * 
	 * @param _row	:	current row
	 * @param _col	:	current column
	 */
	private void checkListSize(int _row, int _col) {
		
		List<Object> CellValidNumberList = mValidNumberList.get(_row, _col);
		
		if( CellValidNumberList.size() == 1 ) {
			int cellSolution = (Integer) CellValidNumberList.get(0);
			mCheckMatrix[_row][_col] = cellSolution;
			mValidNumberList.clear(_row,_col);
			
			// 4. Check all previous list which this cell affects and remove the found solution from the lists
			removeCellSolutionFromValidNumberList(_row, _col, cellSolution);
		}		
	}
	
	
	/**
	 * Remove Cell Solution From ValidNumberList
	 * 
	 * Remove current cellSolution from all previous ValidNumberLists which
	 * are located in the same row, column, or sector.
	 * If the remove command was successful we check again the list size of the current cell.
	 * If the current ValidNumberList size equals '1' we just do the same again from here on and
	 * remove this cellSolution as well from all affected Lists, aso..
	 * 
	 * checkListSize() and this function call each other recursively. The number of recursion calls is limited to the maximum of 9 calls. 
	 * It is therefore guaranteed that the recursion is not critical.
	 * 
	 * @param _row	:	current row
	 * @param _col	:	current column
	 * @param _cellSolution	:	current cellSolution
	 */
	private void removeCellSolutionFromValidNumberList(int _row, int _col, int _cellSolution) {
		
		// check row
		for( int i=mMax; i>=0; i--) {
			if( mValidNumberList.get(_row,i).remove((Object) _cellSolution) == true ) {
				checkListSize(_row, i);
				addToSectorChangeList(_row, i);
			}
		}
		
		// check column
		for( int i=mMax; i>=0; i--) {
			if( mValidNumberList.get(i,_col).remove((Object) _cellSolution) == true ) {
				checkListSize(i, _col);
				addToSectorChangeList(i, _col);
			}
		}	
		
		// check section
		for( int i=mMax; i>=0; i--) {
			
			int r = (_row/3)*3+(i/3);
			int c = (_col/3)*3+(i%3);
			
			if( mValidNumberList.get(r,c).remove((Object) _cellSolution) == true ) {
				checkListSize(r,c);
				addToSectorChangeList(r,c);
			}
		}
	}
}

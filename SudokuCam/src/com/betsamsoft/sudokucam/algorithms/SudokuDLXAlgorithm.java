package com.betsamsoft.sudokucam.algorithms;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import android.util.Log;

import com.betsamsoft.sudokucam.algorithms.dlx.Cell;
import com.betsamsoft.sudokucam.algorithms.dlx.Node;
import com.betsamsoft.sudokucam.algorithms.dlx.SolutionListener;
import com.betsamsoft.sudokucam.algorithms.dlx.StandardSudoku;
import com.betsamsoft.sudokucam.algorithms.dlx.SudokuSolver;
import com.betsamsoft.sudokucam.utils.My3DArrayList;



public class SudokuDLXAlgorithm extends SudokuSolverClass {

	My3DArrayList mValidNumberList;
	TreeSet<Integer> mSectorChangeList;	// we use TreeSet because we wan't each sector marked only once
	
	
	public SudokuDLXAlgorithm(int[][] _RiddleMatrix) {
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
		
		
		// Prepare riddleMatrix for DLX solver
		String puzzle = "";
		
		for(int row=mMax; row>=0; row--) {
			for(int col=mMax; col>=0; col--) {	
				Integer val = mRiddleMatrix[row][col];
				puzzle = puzzle + val.toString();
			}
		}
		
		// Solve with DLX
		StandardSudoku stdSudoku = new StandardSudoku(puzzle);
        SudokuSolver solver = new SudokuSolver(stdSudoku);
        solver.addSolutionListener(new SolutionListener() {
            public boolean solutionFound(final List<Node> solutionNodes) {
            	
                Iterator<Node> iterator = solutionNodes.iterator();
                while (iterator.hasNext()) {
                    Node node = (Node) iterator.next();
                    int index = node.applicationData / mDim;
                    int value = node.applicationData % mDim + 1;
                    mCheckMatrix[((mDim*mDim-1)-index)/mDim][((mDim*mDim-1)-index)%mDim] = value;
                }
                
                return true;	// tell the solver to stop after first found solution
            }
        });
        solver.placeGivens(stdSudoku.getOriginalPuzzle());
        solver.solve();
		
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

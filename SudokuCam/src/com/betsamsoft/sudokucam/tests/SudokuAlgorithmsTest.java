package com.betsamsoft.sudokucam.tests;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.betsamsoft.sudokucam.algorithms.SudokuBackTrackingAlgorithm;
import com.betsamsoft.sudokucam.algorithms.SudokuBackTrackingRecursiveAlgorithm;
import com.betsamsoft.sudokucam.algorithms.SudokuDLXAlgorithm;
import com.betsamsoft.sudokucam.algorithms.SudokuHumanAlgorithm;

public class SudokuAlgorithmsTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
// 20min, Do 27.12.12, leicht
//		int[][] testRiddle = {	{3,9,0,  1,0,6,  0,4,7},
//								{4,0,0,  0,0,0,  0,0,2},
//								{0,0,0,  2,4,5,  0,0,0},
//								
//								{6,0,9,  0,0,0,  7,0,3},
//								{0,0,3,  0,0,0,  1,0,0},
//								{7,0,8,  0,0,0,  6,0,4},
//								
//								{0,0,0,  9,5,4,  0,0,0},
//								{9,0,0,  0,0,0,  0,0,6},
//								{5,8,0,  6,0,7,  0,3,1}
//							};
		
		// 20min, Do 27.12.12, mittel
//		int[][] testRiddle = {	{0,8,0,  7,0,1,  0,0,0},
//								{0,0,2,  0,0,0,  0,0,3},
//								{0,0,3,  4,8,0,  5,7,0},
//								
//								{2,0,0,  0,0,0,  1,0,5},
//								{0,0,7,  0,0,0,  2,0,0},
//								{3,0,6,  0,0,0,  0,0,4},
//								
//								{0,2,8,  0,7,6,  3,0,0},
//								{6,0,0,  0,0,0,  4,0,0},
//								{0,0,0,  9,0,5,  0,2,0}
//							};
		
//		// schwierig
//		int[][] testRiddle = {	{0,8,0,  0,0,1,  0,0,0},
//								{0,0,2,  0,0,0,  0,0,3},
//								{0,0,0,  4,8,0,  5,7,0},
//								
//								{2,0,0,  0,0,0,  1,0,5},
//								{0,0,7,  0,0,0,  2,0,0},
//								{3,0,6,  0,0,0,  0,0,0},
//								
//								{0,2,8,  0,7,6,  3,0,0},
//								{6,0,0,  0,0,0,  4,0,0},
//								{0,0,0,  9,0,5,  0,0,0}
//							};
								
		// almost impossible
		int[][] testRiddle = {	{0,0,0,  0,0,1,  0,0,0},
								{0,0,2,  0,0,0,  0,0,3},
								{0,0,3,  4,8,0,  5,7,0},
								
								{0,0,0,  0,0,0,  1,0,5},
								{0,0,7,  0,0,0,  2,0,0},
								{3,0,6,  0,0,0,  0,0,4},
								
								{0,0,8,  0,7,0,  0,0,0},
								{6,0,0,  0,0,0,  4,0,0},
								{0,0,0,  0,0,0,  0,0,0}
							};
		
		
		String TAG = "SudokuAlgorithmsTest";
		String ALGORITHM_NAME;
		boolean res;

		// ------------------------------------------------------------------
		ALGORITHM_NAME = "BackTracking";
		// ------------------------------------------------------------------
		SudokuBackTrackingAlgorithm bta = new SudokuBackTrackingAlgorithm(testRiddle);
		Log.d(TAG, ALGORITHM_NAME +" Start");
		res = bta.solve();	// \todo: solve riddle in own thread!
		Log.d(TAG, ALGORITHM_NAME +" End");
		if( res == true ) {
			Log.d(TAG, ALGORITHM_NAME +" Algorithm successful!");
		}
		else {
			Log.d(TAG, ALGORITHM_NAME +" Algorithm failed!");
		}
		// ------------------------------------------------------------------
		
		
		// ------------------------------------------------------------------
		ALGORITHM_NAME = "BackTrackingRecursive";
		// ------------------------------------------------------------------
		SudokuBackTrackingRecursiveAlgorithm btar = new SudokuBackTrackingRecursiveAlgorithm(testRiddle);
		Log.d(TAG, ALGORITHM_NAME +" Start");
		res = btar.solve();	// \todo: solve riddle in own thread!
		Log.d(TAG, ALGORITHM_NAME +" End");
		if( res == true ) {
			Log.d(TAG, ALGORITHM_NAME +" Algorithm successful!");
		}
		else {
			Log.d(TAG, ALGORITHM_NAME +" Algorithm failed!");
		}
		// ------------------------------------------------------------------
		
		
		// ------------------------------------------------------------------
		ALGORITHM_NAME = "HumanAlgorithm";
		// ------------------------------------------------------------------
		SudokuHumanAlgorithm ha = new SudokuHumanAlgorithm(testRiddle);
		Log.d(TAG, ALGORITHM_NAME +" Start");
		res = ha.solve();	// \todo: solve riddle in own thread!
		Log.d(TAG, ALGORITHM_NAME +" End");
		if( res == true ) {
			Log.d(TAG, ALGORITHM_NAME +" successful!");
		}
		else {
			Log.d(TAG, ALGORITHM_NAME +" failed!");
		}
		// ------------------------------------------------------------------
				
		
		// ------------------------------------------------------------------
		ALGORITHM_NAME = "DLXAlgorithm";
		// ------------------------------------------------------------------
		SudokuDLXAlgorithm dlx = new SudokuDLXAlgorithm(testRiddle);
		Log.d(TAG, ALGORITHM_NAME +" Start");
		res = dlx.solve();	// \todo: solve riddle in own thread!
		Log.d(TAG, ALGORITHM_NAME +" End");
		if( res == true ) {
			Log.d(TAG, ALGORITHM_NAME +" successful!");
		}
		else {
			Log.d(TAG, ALGORITHM_NAME +" failed!");
		}
		// ------------------------------------------------------------------
		
		
		finish();
	}
}

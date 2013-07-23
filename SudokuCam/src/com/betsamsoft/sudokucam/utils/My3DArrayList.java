package com.betsamsoft.sudokucam.utils;

import java.util.ArrayList;
import java.util.List;

public class My3DArrayList {
	
    private List<List<List<Object>>> mList;

    /**
     * Constructor
     */
    public My3DArrayList(int _dim1, int _dim2, int _dim3) {
        this.mList = generate3DArrayList(_dim1, _dim2, _dim3);
    }

    /**
     * generate3DArrayList
     * 
     * @param _dim1
     * @param _dim2
     * @param _dim3
     * @return	3DArrayList
     */
    public List<List<List<Object>>> generate3DArrayList(int _dim1, int _dim2, int _dim3) {
        List<List<List<Object>>> list = new ArrayList<List<List<Object>>>();
        for(int i = 0; i < _dim1; i++) {
            List<List<Object>> list2 = new ArrayList<List<Object>>();
            for(int j = 0; j < _dim2; j++) {
                List<Object> list3 = new ArrayList<Object>(_dim3);
                list2.add(list3);
            }
            list.add(list2);
        }
        return list;
    }

    /**
     * Get third dim List
     * 
     * @param _i	:	1D location
     * @param _j	:	2D location
     * @return List
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public List<Object> get(int _i, int _j)	throws NullPointerException, ArrayIndexOutOfBoundsException {
        return mList.get(_i).get(_j);
    }

    /**
     * Get ListElement
     * 
     * @param _i	:	1D location
     * @param _j	:	2D location
     * @param _k	:	3D location
     * @return ListObject
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public Object get(int _i, int _j, int _k)	throws NullPointerException, ArrayIndexOutOfBoundsException {
        return mList.get(_i).get(_j).get(_k);
    }

    /**
     * Add ListElement at the end of the list
     * 
     * @param _i	:	1D location
     * @param _j	:	2D location
     * @param _o	:	ListObject to add
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public void add(int _i, int _j, Object _o)	throws NullPointerException, ArrayIndexOutOfBoundsException {
    	mList.get(_i).get(_j).add(_o);
    }

    /**
     * Add ListElement at specific location
     * 
     * @param _i	:	1D location
     * @param _j	:	2D location
     * @param _k	:	3D location
     * @param _o	:	ListObject to add
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public void add(int _i, int _j, int _k, Object _o)	throws NullPointerException, ArrayIndexOutOfBoundsException {
    	mList.get(_i).get(_j).add(_k, _o);
    }

    /**
     * Clear List
     * 
     * @param _i	:	1D location
     * @param _j	:	2D location
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public void clear(int _i, int _j)	throws NullPointerException, ArrayIndexOutOfBoundsException {
    	mList.get(_i).get(_j).clear();
    }

}


//public class Main {
//    public static void main(String[] args) {
//        My3DArray x = new My3DArray();
//        x.set("Hello World", 0, 0, 0);
//        System.out.println(x.get(0, 0, 0));
//        System.exit(0);
//    }
//}
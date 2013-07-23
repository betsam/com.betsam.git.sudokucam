package com.betsamsoft.sudokucam.utils;

import java.util.ArrayList;
import java.util.List;

public class My2DArrayList {
	
    private List<List<Object>> mList;

    /**
     * Constructor
     */
    public My2DArrayList(int _dim1, int _dim2) {
        this.mList = generate2DArrayList(_dim1, _dim2);
    }

    /**
     * generate2DArrayList
     * 
     * @param _dim1
     * @param _dim2
     * @return	2DArrayList
     */
    public List<List<Object>> generate2DArrayList(int _dim1, int _dim2) {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for(int i = 0; i < _dim1; i++) {
            List<Object> list2 = new ArrayList<Object>(_dim2);
            list.add(list2);
        }
        return list;
    }

    /**
     * Get ListElement
     * 
     * @param _i	:	1D location
     * @param _j	:	2D location
     * @return ListObject
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public Object get(int _i, int _j)	throws NullPointerException, ArrayIndexOutOfBoundsException {
        return mList.get(_i).get(_j);
    }

    /**
     * Add ListObject to array
     * 
     * @param _i	:	1D location
     * @param _o	:	ListObject
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public void add(List<Object> _o)	throws NullPointerException, ArrayIndexOutOfBoundsException {
    	mList.add(_o);
    }
    
    /**
     * Add ListItem at the end of the list
     * 
     * @param _i	:	1D location
     * @param _o	:	ListItem
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public void add(int _i, Object _o)	throws NullPointerException, ArrayIndexOutOfBoundsException {
    	mList.get(_i).add(_o);
    }
    
    /**
     * Add ListItem to specific location in the list
     * 
     * @param _i	:	1D location
     * @param _j	:	2D location
     * @param _o	:	ListItem
     * @throws NullPointerException
     * @throws ArrayIndexOutOfBoundsException
     */
    public void add(int _i, int _j, Object _o)	throws NullPointerException, ArrayIndexOutOfBoundsException {
    	mList.get(_i).add(_j, _o);
    }
}


//public class Main {
//    public static void main(String[] args) {
//        My3DArray x = new My2DArrayList();
//        x.set("Hello World", 0, 0, 0);
//        System.out.println(x.get(0, 0, 0));
//        System.exit(0);
//    }
//}
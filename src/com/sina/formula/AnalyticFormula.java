package com.sina.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AnalyticFormula {
	
	public DimensionTree analytic(String formula)
	{
		  // 1.判断string当中有没有非法字符       
        String temp;// 用来临时存放读取的维度字符串       
        // 2.循环开始解析字符串，当字符串解析完，且符号栈为空时，则计算完成       
        StringBuffer dimension = new StringBuffer();// 用来临时存放数字字符串(当为多位数时)       
        StringBuffer string = new StringBuffer().append(formula);// 用来保存，提高效率       
        DimensionTree tree  = new DimensionTree();
        Stack<String> stack = new Stack<String>();
        Stack<List<String>> dimensionStack = new Stack<List<String>>();
        Stack<ConcurrentLinkedQueue<BitOperate>> operateStack = new Stack<ConcurrentLinkedQueue<BitOperate>>();
    	List<Dimension> dimensionList = new ArrayList<Dimension>();
    	ConcurrentLinkedQueue<BitOperate> operateList = new ConcurrentLinkedQueue<BitOperate>();
        while (string.length() != 0) {  

            temp = string.substring(0, 1); 
            string.delete(0, 1);       
            // 判断temp，当temp为操作符时       
            if (isDimension(temp)) {       
                // 1.此时的tempNum内即为需要操作的数，取出数，压栈，并且清空tempNum       
                if (!"".equals(dimension.toString())) {       
                	Dimension d = new Dimension();
                	d.setName(dimension.toString());
                	dimensionList.add(d);
                } 
                switch (temp.charAt(0)){
                case '&':
                	operateList.add(new AndOperate());
            	case '|':
                	operateList.add(new OrOperate());
            	case '(':
            		stack.add("(");
            		
            	case ')':
            		stack.pop();
            		
      
    	       } 
     
            } else       
                // 当为非操作符时       
            	dimension = dimension.append(temp);// 将读到的这一位数接到以读出的数后(当不是个位数的时候)       
        }       
            
		
		return tree;
	}

 
	     
	   private boolean isDimension(String temp) {       
	        return temp.matches("[(,),&,|]");       
		}  
}

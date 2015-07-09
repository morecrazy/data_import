package com.sina.formula;

public class CheckPriority {   
	   public static int getPriority(String s){   
	       if(null == s){   
	           return 0;   
	       }   
	       if(s.equals("*") || s.equals("/")){ //* / 是1级   
	           return 2;   
	       }else if(s.equals("+") || s.equals("-")){ // + -是0级   
	           return 1;   
	        }else{   
	            return -1;   
	        }   
	    }   
	}  

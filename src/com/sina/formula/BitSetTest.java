package com.sina.formula;

import com.sina.formula.java.util.SinaBitSet;

public class BitSetTest {
	
	public  static void main(String[] args)throws Exception
	{
		SinaBitSet s1 = new SinaBitSet(10000);
		SinaBitSet s2 = new SinaBitSet(10000);
		s1.set(8888);
		s2.set(9999);
		System.out.println(System.nanoTime());
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		s1.or(s2);
		s1.and(s2);
		System.out.println(s2.get(9999));
		System.out.println(System.nanoTime());
	}

}

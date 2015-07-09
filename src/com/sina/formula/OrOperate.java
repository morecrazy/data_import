package com.sina.formula;

import java.util.BitSet;

public class OrOperate implements BitOperate{
	public BitSet or(BitSet a, BitSet b)
	{
		 a.or(b);
		 return a;
	}
	@Override
	public BitSet operate(BitSet a, BitSet b) {
		return or( a, b);
	}

}

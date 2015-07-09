package com.sina.formula;

import java.util.BitSet;

public class AndOperate implements BitOperate{
	public BitSet and(BitSet a, BitSet b)
	{
		 a.and(b);
		 return a;
	}

	@Override
	public BitSet operate(BitSet a, BitSet b) {
		return and( a, b);
	}

}

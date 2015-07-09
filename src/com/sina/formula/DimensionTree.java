package com.sina.formula;

import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DimensionTree {
	ConcurrentLinkedQueue<BitOperate> operateList = null;
	List<Dimension> dimensionList = null;
	List<DimensionTree> treeList = null;
	BitSet bitSet = null;
	
	public BitSet operate()
	{
		if(dimensionList!=null && operateList!=null)
		{
			Dimension temp = null;
			for(Dimension d : dimensionList)
			{
				if(temp == null)
				{
					temp = d;
				}
				else
				{
					BitOperate operate = operateList.poll();
					BitSet set = operate.operate(temp.getLastBitSet(), d.getLastBitSet());
					temp.setLastBitSet(set);
				}
			}
			if(temp != null)
			{
				bitSet = temp.getLastBitSet();
			}
		}
		if(treeList!=null && operateList!=null)
		{
			DimensionTree temp = null;
			for(DimensionTree d : treeList)
			{
				d.operate();
				if(temp == null)
				{
					temp = d;
				}
				else
				{
					BitOperate operate = operateList.poll();
					BitSet set = operate.operate(temp.getBitSet(), d.getBitSet());
					temp.setBitSet(set);
				}
			}
			if(temp != null)
			{
				bitSet = temp.getBitSet();
			}
		}
		if(dimensionList.size()==1)
		{
			bitSet = dimensionList.get(0).getLastBitSet();
		}
		return bitSet ;
	}
	public ConcurrentLinkedQueue<BitOperate> getOperateList() {
		return operateList;
	}
	public void setOperateList(ConcurrentLinkedQueue<BitOperate> operateList) {
		this.operateList = operateList;
	}
	public List<Dimension> getDimensionList() {
		return dimensionList;
	}
	public void setDimensionList(List<Dimension> dimensionList) {
		this.dimensionList = dimensionList;
	}
	public BitSet getBitSet() {
		return bitSet;
	}
	public void setBitSet(BitSet bitSet) {
		this.bitSet = bitSet;
	}
	public List<DimensionTree> getTreeList() {
		return treeList;
	}
	public void setTreeList(List<DimensionTree> treeList) {
		this.treeList = treeList;
	}
	

}

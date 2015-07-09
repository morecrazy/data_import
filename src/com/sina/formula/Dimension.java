package com.sina.formula;

import java.util.ArrayList;
import java.util.BitSet;

public class Dimension {
	BitSet lastBitSet = null ;
	String name = null;
	ArrayList<TagEntity> tagList = new ArrayList<TagEntity>();
	
	public BitSet getLastBitSet() {
		return lastBitSet;
	}
	public void setLastBitSet(BitSet lastBitSet) {
		this.lastBitSet = lastBitSet;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<TagEntity> getTagList() {
		return tagList;
	}
	public void setTagList(ArrayList<TagEntity> tagList) {
		this.tagList = tagList;
	}
	
	

}

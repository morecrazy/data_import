package com.sina.util;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapreduce.Partitioner;


public class TextPair implements WritableComparable<TextPair> {
	private Text first;
	private Text second;
	public TextPair(){
		set(new Text(),new Text());
	}
	public TextPair(String first,String second){
		this.set(new Text(first), new Text(second));
	}
	public void set(Text first,Text second){
		this.first=first;
		this.second=second;
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		first.readFields(in);
		second.readFields(in);

	}

	@Override
	public void write(DataOutput out) throws IOException {
		first.write(out);
		second.write(out);

	}
	@Override
	public int hashCode(){
		return first.hashCode()*163+second.hashCode();
	}
	@Override
	public String toString(){
		return first + "\t"+second;
	}

	public Text getFirst(){
		return first;
	}
	public Text getSecond(){
		return second;
	}
	@Override
	public int compareTo(TextPair arg0) {
		TextPair tp=(TextPair)arg0;
		int cmp = first.compareTo(tp.first);
		if(cmp!=0){
			return cmp;
		}
		return second.compareTo(tp.second);
	}
	
	public static class FirstComparator extends WritableComparator{
		private static final Text.Comparator TEXT_COMPARATOR=new Text.Comparator();
		protected FirstComparator() {
			super(TextPair.class);
		}
		@Override
		public int compare(byte[] b1,int s1,int l1,
							byte[] b2,int s2,int l2){
			try {
				int firstL1=WritableUtils.decodeVIntSize(b1[s1])+readVInt(b1,s1);
				int firstL2=WritableUtils.decodeVIntSize(b2[s2])+readVInt(b2,s2);
				return TEXT_COMPARATOR.compareBytes(b1, s1, firstL1, b2, s2, firstL2);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}
		@Override
		public int compare(WritableComparable a, WritableComparable b) {
			if(a instanceof TextPair && b instanceof TextPair){
				return ((TextPair)a).getFirst().compareTo(((TextPair) b).getFirst());
			}
			return super.compare(a, b);
			  }
	}
	
	public static class KeyPartitioner extends Partitioner<TextPair, Text>{
		public  int getPartition(TextPair key, Text value, int numPartitions){
			return (key.getFirst().hashCode() & Integer.MAX_VALUE) % numPartitions;
		}
	}
}

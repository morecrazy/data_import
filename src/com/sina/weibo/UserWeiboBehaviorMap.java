package com.sina.weibo;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.log4j.Logger;

import com.sina.util.MurmurHash3;
import com.sina.website.UserTagMap;


public class UserWeiboBehaviorMap extends Mapper<LongWritable, Text, Text, Text>{
	protected Logger logger = Logger.getLogger(UserTagMap.class);
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException,InterruptedException {
			super.setup(context);
			Configuration conf = context.getConfiguration();
			FileSystem fs = FileSystem.get(conf);
	}

	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		if(value!=null){
			String line = value.toString().trim();
			if (!"".equals(line)) {
				String[] values = line.split("\001");
				if(values!=null && values.length>0)
				{
					String memberId = values[0];
					context.write(new Text(memberId), value);
					
				}	
			}
		}
	}
		
}

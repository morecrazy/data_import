package com.sina.website;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.mtarget.mapred.MTDefaultDriver;
import com.sina.util.*;

public class UserTagImportRedisDriver extends MTDefaultDriver{

	public static String INPUT_FILE = "";
	public static String CONFIG_PATH="";
	public static String TRY_TIMES="1";
	public static String JEDIS_USER_EXPIRED="";
	public static String URL_REGION="";
	public static String USER_REGION="";
	public static String URL_REDIS_NAME="";
	public static String USER_REDIS_NAME="";
	public static String PATTERN_STRING="";
	public static String USER_TAG_THRESHOLD="";
	public static String HOST_PATH="/user/fengming/host.txt";
	public static Logger logger = org.apache.log4j.Logger.getLogger(UserTagImportRedisDriver.class);


	public UserTagImportRedisDriver() {
	}

	private void setParam() {

		conf.set("URL_REGION",URL_REGION);
		conf.set("USER_REGION",USER_REGION);
		conf.set("URL_REDIS_NAME",URL_REDIS_NAME);
		conf.set("USER_REDIS_NAME",USER_REDIS_NAME);
		conf.set("PATTERN_STRING",PATTERN_STRING);
		conf.set(Constant.TRY_TIMES, TRY_TIMES);
		conf.set(Constant.HOST_PATH, HOST_PATH);
		conf.set(Constant.JEDIS_USER_EXPIRED,JEDIS_USER_EXPIRED);
		conf.set(Constant.USER_TAG_THRESHOLD,USER_TAG_THRESHOLD);
	}

	public int run(String[] args) throws Exception {
		Job job = new Job(getConf(),"UserTagImportDriver");
		this.conf = job.getConfiguration();

		job.setJobName("USER_TAG_IMPORT");
		job.setJarByClass(UserTagImportRedisDriver.class);
		job.setMapperClass(UserTagImportRedisMap.class);
		//job.setInputFormatClass(TextInputFormat.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		
		String outputpath="/user/website"+DateUtils.getYestodayDate("/yyyy/MM/dd/");
		FileSystem fs = FileSystem.get(this.conf);
		fs.delete(new Path(outputpath), true);
		FileOutputFormat.setOutputPath(job, new Path(outputpath));
		
		TextInputFormat.setInputPaths(job, INPUT_FILE);
		job.setReducerClass(UserTagImportRedisReduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		setParam();
		job.getConfiguration().setInt("io.sort.mb", 1);
		job.waitForCompletion(true);
		return 0;
	}
	
	public void run() throws Exception {
		ToolRunner.run(new Configuration(), this, null);
	}

}

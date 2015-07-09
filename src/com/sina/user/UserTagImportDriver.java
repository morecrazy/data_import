package com.sina.user;

import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.mtarget.mapred.MTDefaultDriver;


public class UserTagImportDriver extends MTDefaultDriver {
	public static String INPUT_FILE = "";
	//public static String INPUT_FILE_TEST = "";
	public static String OUTPUT_PATH = "";
	public static String OUTPUT_TABLE = "userdirectional";
	public static String HBASE_CONFIG_FILE_PATH="";//"http://192.168.140.132:8080/ubuntuVM.xml";
	public static String COLUMN_LIST="ID DIRECTIONAL";
	public static String CONFIG_PATH="";
	public static String USER_REGION="USER_TAG_REGION";
	public static String USER_REDIS_NAME="REDIS1";
	public static String TRY_TIMES="1";
	public static String JEDIS_USER_EXPIRED="";
	public static String USER_TAG_THRESHOLD="";
	//public static String HBASE_EXPIRED_TIME="";
	public static String HOST_PATH="/home/fengming/host.txt";
	public static Logger logger = org.apache.log4j.Logger.getLogger(UserTagImportDriver.class);


	public UserTagImportDriver() {
	}

	private void setParam() {

		conf.set(Constant.CONFIG_PATH,CONFIG_PATH);
		conf.set("USER_REGION",USER_REGION);
		conf.set("USER_REDIS_NAME",USER_REDIS_NAME);
		conf.set(Constant.TRY_TIMES, TRY_TIMES);
		conf.set(Constant.HOST_PATH, HOST_PATH);
		conf.set(Constant.HOST_PATH, HOST_PATH);
		conf.set(Constant.JEDIS_USER_EXPIRED,JEDIS_USER_EXPIRED);
		conf.set(Constant.USER_TAG_THRESHOLD,USER_TAG_THRESHOLD);
	}

	public int run(String[] args) throws Exception {
		System.out.println("start1");
		Job job = new Job(getConf(),"UserDirectionalImportDriver");
		this.conf = job.getConfiguration();

		String hbaseConfig = HBASE_CONFIG_FILE_PATH;
		if (hbaseConfig != null && !"".equals(hbaseConfig)) {
			this.conf.addResource(new URL(hbaseConfig));
		}
		job.setJobName("USERDIRECTIONAL IMPORT");
		job.setJarByClass(UserTagImportDriver.class);
		job.setMapperClass(UserTagImportMap.class);
		job.setInputFormatClass(TextInputFormat.class);
		TextInputFormat.setInputPaths(job, INPUT_FILE);
		Path outpath = new Path(OUTPUT_PATH);
		TextOutputFormat.setOutputPath(job, outpath);
		job.setReducerClass(UserTagImportReduce.class);
		//TableMapReduceUtil.initTableReducerJob(OUTPUT_TABLE,UserTagImportReduce.class, job);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Put.class);

		setParam();
		job.getConfiguration().setInt("io.sort.mb", 1);
		job.waitForCompletion(true);
/*		job.waitForCompletion(false);
		
		Job job1 = new Job(getConf(),"test job control");
		this.conf = job.getConfiguration();
		System.out.println("start2");
		job1.setJobName("test job control");
		job1.setJarByClass(UserTagImportDriver.class);
		job1.setMapperClass(UserTagImportMap.class);
		job1.setInputFormatClass(TextInputFormat.class);
		TextInputFormat.setInputPaths(job1, INPUT_FILE_TEST);
		outpath = new Path("/data/usr/test");
		TextOutputFormat.setOutputPath(job1, outpath);
		job1.setReducerClass(UserTagImportReduce.class);
		//TableMapReduceUtil.initTableReducerJob(OUTPUT_TABLE,UserTagImportReduce.class, job);
		job1.setMapOutputKeyClass(Text.class);
		job1.setMapOutputValueClass(Text.class);
		job1.setOutputKeyClass(ImmutableBytesWritable.class);
		job1.setOutputValueClass(Put.class);

		job1.getConfiguration().setInt("io.sort.mb", 1);
		//job.waitForCompletion(true);
		job1.waitForCompletion(true);*/
		return 0;
	}
	
	public void run() throws Exception {
		ToolRunner.run(new Configuration(), this, null);
	}


}

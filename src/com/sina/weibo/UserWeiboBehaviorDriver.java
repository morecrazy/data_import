package com.sina.weibo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.mtarget.mapred.MTDefaultDriver;
import com.sina.util.DateUtils;
import com.sina.website.UserTagDriver;

public class UserWeiboBehaviorDriver extends MTDefaultDriver{
	public static Logger logger = org.apache.log4j.Logger.getLogger(UserWeiboBehaviorDriver.class);
	public static String BEHAVIOR_INPUT_FILE = "/file/tblog/behavior/";
	public static String CONFIG_PATH="";
	public static String TRY_TIMES="1";
	public static String JEDIS_USER_EXPIRED="";
	public static String USER_REGION="";
	public static String USER_REDIS_NAME="";
	public static String USER_TAG_THRESHOLD="";
	public static String HOST_PATH="/user/fengming/host.txt";
	


	public UserWeiboBehaviorDriver() {
	}

	private void setParam() {
		conf.set(Constant.CONFIG_PATH,CONFIG_PATH);
		conf.set(BEHAVIOR_INPUT_FILE,BEHAVIOR_INPUT_FILE);
		conf.set("USER_REGION",USER_REGION);
		conf.set("USER_REDIS_NAME",USER_REDIS_NAME);
		conf.set(Constant.TRY_TIMES, TRY_TIMES);
		conf.set("HOST_PATH", HOST_PATH);
		conf.set(Constant.JEDIS_USER_EXPIRED,JEDIS_USER_EXPIRED);
		conf.set(Constant.USER_TAG_THRESHOLD,USER_TAG_THRESHOLD);
		logger.info(HOST_PATH);
	}

	public int run(String[] args) throws Exception {
		
		this.conf = new Configuration();
		Job job = new Job(getConf(),"UserWeiboBehaviorDriver");
		this.conf = job.getConfiguration();
		job.setJobName("USER_BEHAVIOR_IMPORT");
		job.setJarByClass(UserWeiboBehaviorDriver.class);
		job.setMapperClass(UserWeiboBehaviorMap.class);
		
		//MultipleInputs.addInputPath(job, new Path(BEHAVIOR_INPUT_FILE+DateUtils.getYestodayDate("/14000003/yyyy/MM/dd/")), com.hadoop.mapreduce.LzoTextInputFormat.class, UserWeiboTurnMap.class);
		//MultipleInputs.addInputPath(job, new Path(BEHAVIOR_INPUT_FILE+DateUtils.getYestodayDate("/14000005/yyyy/MM/dd/")), com.hadoop.mapreduce.LzoTextInputFormat.class, UserWeiboCommentMap.class);
		//MultipleInputs.addInputPath(job, new Path(ATTENTION_INPUT_FILE), com.hadoop.mapreduce.LzoTextInputFormat.class, UserWeiboAttentionMap.class);
		job.setInputFormatClass(TextInputFormat.class);
		//job.setInputFormatClass();

		TextInputFormat.setInputPaths(job, BEHAVIOR_INPUT_FILE);
		String outputpath="/user/fengming/weibo";
		FileSystem fs = FileSystem.get(this.conf);
		this.conf.get("");
		fs.delete(new Path(outputpath), true);
		FileOutputFormat.setOutputPath(job, new Path(outputpath));
		job.setReducerClass(UserWeiboBehaviorReduce.class);
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

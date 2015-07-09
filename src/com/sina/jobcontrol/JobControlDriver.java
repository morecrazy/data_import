package com.sina.jobcontrol;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.mtarget.mapred.MTDefaultDriver;
import com.sina.enterprise.EnterpriseImportDriver;
import com.sina.enterprise.EnterpriseImportMap;
import com.sina.hadoop.MultipleInputs;
import com.sina.util.DateUtils;
import com.sina.website.UserTagDriver;
import com.sina.website.UserTagMap;
import com.sina.website.UserTagReduce;
import com.sina.weibo.UserWeiboAttentionMap;
import com.sina.weibo.UserWeiboBehaviorDriver;
import com.sina.weibo.UserWeiboBehaviorReduce;
import com.sina.weibo.UserWeiboCommentMap;
import com.sina.weibo.UserWeiboTurnMap;

public class JobControlDriver extends MTDefaultDriver{
	public static Logger logger = org.apache.log4j.Logger.getLogger(JobControlDriver.class);

	public static String BEHAVIOR_INPUT_FILE = "/file/tblog/behavior/";
	public static String ATTENTION_INPUT_FILE = "/file/tblog/fanslist/";
	public static String EN_INPUT_FILE = "/user/sinadata/enterprise/enterprise_user/";
	public static String TAG_INPUT_FILE = "/file/suda/suda_lzo/";
	public static String HOST_PATH="/user/fengming/host.txt";
	public static String URL_REDIS_NAME="";
	public static String URL_REGION="";
	public static String USER_REGION="";
	public static String USER_REDIS_NAME="";
	public static String PATTERN_STRING="";
	public static String TRY_TIMES="1";
	public static String JEDIS_USER_EXPIRED="";
	public static String USER_TAG_THRESHOLD="";


	public JobControlDriver() {
	}

	private void setParam() {

		conf.set("ATTENTION_INPUT_FILE",ATTENTION_INPUT_FILE);
		conf.set("BEHAVIOR_INPUT_FILE",BEHAVIOR_INPUT_FILE);
		conf.set("EN_INPUT_FILE",BEHAVIOR_INPUT_FILE);
		conf.set("TAG_INPUT_FILE",BEHAVIOR_INPUT_FILE);
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
		setParam();
		Job jobEn = new Job(getConf(),"EnterpriseImportDriver");
		jobEn.setJobName("Enterprise_IMPORT");
		jobEn.setJarByClass(EnterpriseImportDriver.class);
		jobEn.setMapperClass(EnterpriseImportMap.class);
		//job.setInputFormatClass(TextInputFormat.class);
		
		jobEn.setInputFormatClass(com.hadoop.mapreduce.LzoTextInputFormat.class);

		TextInputFormat.setInputPaths(jobEn, EN_INPUT_FILE+DateUtils.getYestodayDate("/yyyy/MM/dd/"));
		//job.setReducerClass(EnterpriseImportReduce.class);
		String outputpath="/user/fengming/enterprise";
		FileSystem fs = FileSystem.get(this.conf);
		fs.delete(new Path(outputpath), true);
		FileOutputFormat.setOutputPath(jobEn, new Path(outputpath));
		jobEn.setMapOutputKeyClass(Text.class);
		jobEn.setMapOutputValueClass(Text.class);
		jobEn.setOutputKeyClass(Text.class);
		jobEn.setOutputValueClass(Text.class);
		jobEn.waitForCompletion(true);
		
		Job jobTag = new Job(getConf(),"UserTagImportDriver");

		jobTag.setJobName("USER_TAG_IMPORT");
		jobTag.setJarByClass(UserTagDriver.class);
		jobTag.setMapperClass(UserTagMap.class);
		//job.setInputFormatClass(TextInputFormat.class);
		
		jobTag.setInputFormatClass(com.hadoop.mapreduce.LzoTextInputFormat.class);
		outputpath="/user/fengming/website";
		fs.delete(new Path(outputpath), true);
		FileOutputFormat.setOutputPath(jobEn, new Path(outputpath));
		TextInputFormat.setInputPaths(jobTag, TAG_INPUT_FILE+DateUtils.getYestodayDate("/yyyy/MM/dd/*/"));
		jobTag.setReducerClass(UserTagReduce.class);
		jobTag.setMapOutputKeyClass(Text.class);
		jobTag.setMapOutputValueClass(Text.class);
		jobTag.setOutputKeyClass(ImmutableBytesWritable.class);
		jobTag.setOutputValueClass(Put.class);
		jobTag.waitForCompletion(true);
		
		
		Job jobBe = new Job(getConf(),"UserWeiboBehaviorDriver");
		jobBe.setJobName("USER_BEHAVIOR_IMPORT");
		jobBe.setJarByClass(UserWeiboBehaviorDriver.class);
		//job.setMapperClass(UserWeiboBehaviorMap.class);
		
		MultipleInputs.addInputPath(jobBe, new Path(BEHAVIOR_INPUT_FILE+DateUtils.getYestodayDate("/14000003/yyyy/MM/dd/")), com.hadoop.mapreduce.LzoTextInputFormat.class, UserWeiboTurnMap.class);
		MultipleInputs.addInputPath(jobBe, new Path(BEHAVIOR_INPUT_FILE+DateUtils.getYestodayDate("/14000005/yyyy/MM/dd/")), com.hadoop.mapreduce.LzoTextInputFormat.class, UserWeiboCommentMap.class);
		MultipleInputs.addInputPath(jobBe, new Path(ATTENTION_INPUT_FILE), com.hadoop.mapreduce.LzoTextInputFormat.class, UserWeiboAttentionMap.class);

		outputpath="/user/fengming/weibo";
		fs.delete(new Path(outputpath), true);
		FileOutputFormat.setOutputPath(jobBe, new Path(outputpath));
		jobBe.setReducerClass(UserWeiboBehaviorReduce.class);
		jobBe.setMapOutputKeyClass(Text.class);
		jobBe.setMapOutputValueClass(Text.class);
		jobBe.setOutputKeyClass(Text.class);
		jobBe.setOutputValueClass(Text.class);
		
		jobBe.waitForCompletion(true);
		
		return 0;
	}
	
	public void run() throws Exception {
		ToolRunner.run(new Configuration(), this, null);
	}

}

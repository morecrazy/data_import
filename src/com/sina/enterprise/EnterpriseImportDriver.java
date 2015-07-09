package com.sina.enterprise;

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

public class EnterpriseImportDriver extends MTDefaultDriver{

	public static String INPUT_FILE = "/user/sinadata/enterprise/enterprise_user/";
	public static String CONFIG_PATH="";
	public static String TRY_TIMES="1";
	public static String JEDIS_USER_EXPIRED="";
	public static String URL_REGION="";
	public static String USER_REGION="";
	public static String URL_REDIS_NAME="";
	public static String USER_REDIS_NAME="";
	public static String USER_TAG_THRESHOLD="";
	public static String HOST_PATH="/user/fengming/host.txt";
	public static Logger logger = org.apache.log4j.Logger.getLogger(EnterpriseImportDriver.class);


	public EnterpriseImportDriver() {
	}

	private void setParam() {

		conf.set("USER_REGION",USER_REGION);
		conf.set("USER_REDIS_NAME",USER_REDIS_NAME);
		conf.set(Constant.TRY_TIMES, TRY_TIMES);
		conf.set(Constant.HOST_PATH, HOST_PATH);
		conf.set(Constant.JEDIS_USER_EXPIRED,JEDIS_USER_EXPIRED);
		conf.set(Constant.USER_TAG_THRESHOLD,USER_TAG_THRESHOLD);
	}

	public int run(String[] args) throws Exception {
		Job job = new Job(getConf(),"EnterpriseImportDriver");
		this.conf = job.getConfiguration();

		job.setJobName("Enterprise_IMPORT");
		job.setJarByClass(EnterpriseImportDriver.class);
		job.setMapperClass(EnterpriseImportMap.class);
		//job.setInputFormatClass(TextInputFormat.class);
		
		job.setInputFormatClass(TextInputFormat.class);

		TextInputFormat.setInputPaths(job, INPUT_FILE+DateUtils.getYestodayDate("/yyyy/MM/dd/"));
		//job.setReducerClass(EnterpriseImportReduce.class);
		String outputpath="/user/fengming/enterprise";
		FileSystem fs = FileSystem.get(this.conf);
		fs.delete(new Path(outputpath), true);
		FileOutputFormat.setOutputPath(job, new Path(outputpath));
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

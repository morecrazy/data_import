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

public class UserTagDriver extends MTDefaultDriver{

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
	public static Logger logger = org.apache.log4j.Logger.getLogger(UserTagDriver.class);


	public UserTagDriver() {
	}

	private void setParam() {

		//conf.set("URL_REGION",URL_REGION);
		//conf.set("USER_REGION",USER_REGION);
		//conf.set("URL_REDIS_NAME",URL_REDIS_NAME);
		//conf.set("USER_REDIS_NAME",USER_REDIS_NAME);
		conf.set("PATTERN_STRING",PATTERN_STRING);
		//conf.set(Constant.TRY_TIMES, TRY_TIMES);
		//conf.set(Constant.HOST_PATH, HOST_PATH);
		//conf.set(Constant.JEDIS_USER_EXPIRED,JEDIS_USER_EXPIRED);
		//conf.set(Constant.USER_TAG_THRESHOLD,USER_TAG_THRESHOLD);
	}

	public int run(String[] args) throws Exception {
		Job job = new Job(getConf(),"UserTagImportDriver");
		this.conf = job.getConfiguration();

		job.setJobName("USER_TAG_IMPORT");
		job.setJarByClass(UserTagDriver.class);
		job.setMapperClass(UserTagMap.class);
		//job.setInputFormatClass(TextInputFormat.class);
		
		job.setInputFormatClass(com.hadoop.mapreduce.LzoTextInputFormat.class);
		
		String outputpath="/user/fengming/website"+DateUtils.getYestodayDate("/yyyy/MM/dd/");
		FileSystem fs = FileSystem.get(this.conf);
		fs.delete(new Path(outputpath), true);
		FileOutputFormat.setOutputPath(job, new Path(outputpath));
		
		TextInputFormat.setInputPaths(job, getChannelImputPath(INPUT_FILE+DateUtils.getYestodayDate("/yyyy/MM/dd/")));
		logger.info(getChannelImputPath(INPUT_FILE+DateUtils.getYestodayDate("/yyyy/MM/dd/")));
		job.setReducerClass(UserTagReduce.class);
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
	public String getChannelImputPath(String path)
	{
		String re = "";
		re = path+"/2008/*.lzo";
		re = re+","+path+"/2010/*.lzo";
		re = re+","+path+"/astro/*.lzo";
		re = re+","+path+"/auto/*.lzo";
		re = re+","+path+"/baby/*.lzo";
		re = re+","+path+"/bbs/*.lzo";
		re = re+","+path+"/blog/*.lzo";
		re = re+","+path+"/bn/*.lzo";
		re = re+","+path+"/book/*.lzo";
		re = re+","+path+"/chinanba/*.lzo";
		re = re+","+path+"/chongqing/*.lzo";
		re = re+","+path+"/cul/*.lzo";
		re = re+","+path+"/dl/*.lzo";
		re = re+","+path+"/edu/*.lzo";
		re = re+","+path+"/eladies/*.lzo";
		re = re+","+path+"/ent/*.lzo";
		re = re+","+path+"/expo2010/*.lzo";
		re = re+","+path+"/finance/*.lzo";
		re = re+","+path+"/fj/*.lzo";
		re = re+","+path+"/games/*.lzo";
		re = re+","+path+"/gd/*.lzo";
		re = re+","+path+"/gongyi/*.lzo";
		re = re+","+path+"/heilongjiang/*.lzo";
		re = re+","+path+"/hn/*.lzo";
		re = re+","+path+"/house/*.lzo";
		re = re+","+path+"/hubei/*.lzo";
		re = re+","+path+"/hunnan/*.lzo";
		re = re+","+path+"/iask/*.lzo";
		re = re+","+path+"/ipad/*.lzo";
		re = re+","+path+"/jiangsu/*.lzo";
		re = re+","+path+"/liaoning/*.lzo";
		re = re+","+path+"/life/*.lzo";
		re = re+","+path+"/mail/*.lzo";
		re = re+","+path+"/mall/*.lzo";
		re = re+","+path+"/music/*.lzo";
		re = re+","+path+"/my2008/*.lzo";
		re = re+","+path+"/news/*.lzo";
		re = re+","+path+"/other/*.lzo";
		re = re+","+path+"/pet/*.lzo";
		re = re+","+path+"/photo/*.lzo";
		re = re+","+path+"/play/*.lzo";
		re = re+","+path+"/quanzi/*.lzo";
		re = re+","+path+"/sc/*.lzo";
		re = re+","+path+"/search/*.lzo";
		re = re+","+path+"/sh/*.lzo";
		re = re+","+path+"/shanxi/*.lzo";
		re = re+","+path+"/show/*.lzo";
		re = re+","+path+"/sinapay/*.lzo";
		re = re+","+path+"/space/*.lzo";
		re = re+","+path+"/sports/*.lzo";
		re = re+","+path+"/tblog/*.lzo";
		re = re+","+path+"/tech/*.lzo";
		re = re+","+path+"/uc/*.lzo";
		re = re+","+path+"/unipro/*.lzo";
		re = re+","+path+"/vblog/*.lzo";
		re = re+","+path+"/vic/*.lzo";
		re = re+","+path+"/www/*.lzo";
		re = re+","+path+"/yayun2010/*.lzo";
		re = re+","+path+"/yuyun2010/*.lzo";
		re = re+","+path+"/zhejiang/*.lzo";
		return re;
	}

}

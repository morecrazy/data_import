package com.sina.weibo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;
import com.sina.website.UserTagMap;


public class UserWeiboCommentMap extends Mapper<LongWritable, Text, Text, Text>{
	protected Logger logger = Logger.getLogger(UserTagMap.class);
	//public String PATTERN_STRING = "http://.*/2012[0-9-/]{4,6}/[0-9]{7,}\\.(html|shtml)";
	//private Pattern p = Pattern.compile(PATTERN_STRING);
	private JedisUtils jedis = null;
	private String userRegion = null;
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException,InterruptedException {
			super.setup(context);
			Configuration conf = context.getConfiguration();
			FileSystem fs = FileSystem.get(conf);

			String hostPath = conf.get(Constant.HOST_PATH);
			Path host = new Path(hostPath);
			ArrayList<String> hosts = new ArrayList<String>();
			if(fs.exists(host)){
				FSDataInputStream input = fs.open(host);
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				String line = null;
				while((line = reader.readLine())!=null){
					hosts.add(line);
				}
			}
			//logger.info(hosts);
			String userRedisName = conf.get("USER_REDIS_NAME");
			HashMap<String,String> map = new HashMap<String,String>();
			map.put(userRegion, userRedisName);
			RedisConfig.getInstance().setRegionRedisMap(map);
			RedisConfig.getInstance().loadhdfs(hosts);
			jedis = JedisUtils.getInstance();
	}

	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		String line =value.toString();
		String str[] = line.split("\t");
		if(str.length<10)return;
		String uid = str[2];
		String ext = str[9];
		String dateTime = str[0];
		int pos = ext.indexOf("rootuid=>");
		String mid = "";
		if(pos!=-1){
			int pos1 = ext.indexOf(",", pos+1);
			if(pos1!=-1){
				mid = ext.substring(pos+9, pos1);
			}else{
				mid = ext.substring(pos+9);
			}
			if(isActionEnterprise(mid))
			{
				String action = uid+"|2|"+mid+"|"+dateTime;
				context.write(new Text(uid), new Text(action));
			}
		}	
		
	}
	public boolean isActionEnterprise(String mid)
	{
		String re = (String)jedis.get(userRegion, mid);
		return re==null?false:true;
	}
		
}

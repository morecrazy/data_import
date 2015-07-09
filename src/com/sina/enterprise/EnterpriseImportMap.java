package com.sina.enterprise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.sina.util.MurmurHash3;

public class EnterpriseImportMap extends Mapper<LongWritable, Text, Text, Text>{
	protected Logger logger = Logger.getLogger(EnterpriseImportMap.class);
	private JedisUtils jedis = null;
	private String userRegion = null;
	private int expiredTime=Constant.DEFAULT_REDIS_EXPIRE_SECONDS;
	private int tryTime=1;
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
			String expiredStr = conf.get(Constant.JEDIS_USER_EXPIRED);
			if(expiredStr!=null && !expiredStr.isEmpty()){
				expiredTime = Integer.parseInt(expiredStr);		
			}
			userRegion = conf.get("USER_REGION");
			String userRedisName = conf.get("USER_REDIS_NAME");
			tryTime = Integer.parseInt(conf.get(Constant.TRY_TIMES));
			HashMap<String,String> map = new HashMap<String,String>();
			map.put(userRegion, userRedisName);
			RedisConfig.getInstance().setRegionRedisMap(map);
			RedisConfig.getInstance().loadhdfs(hosts);
			jedis = JedisUtils.getInstance();
			
	}

	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		if(value!=null){
			String line = value.toString().trim();
			if (!"".equals(line)) {
				String[] values = line.split("\t");
				if(values!=null && values.length>0){

					String userId = values[0];
					putRedis(userRegion,userId,userId);
				}	
			}
		}
	}
	
	public void putRedis(String region,String key,Object o)
	{
		for(int i=0;i<tryTime;i++){
			try{
				if(jedis.set(region, key, o,expiredTime)){
					break;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
		

}

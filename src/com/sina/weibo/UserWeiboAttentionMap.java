package com.sina.weibo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;
import com.sina.util.MurmurHash3;
import com.sina.website.UserTagMap;


public class UserWeiboAttentionMap extends Mapper<LongWritable, Text, Text, Text>{
	protected Logger logger = Logger.getLogger(UserTagMap.class);
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
		if(str.length<3)return;
		String uid = str[0];
		String dateTime = str[2];
		String enId = str[1];

		if(isActionEnterprise(enId))
		{
			String action = uid+"|1|"+enId+"|"+dateTime;
			context.write(new Text(uid), new Text(action));
		}

	}
	public boolean isActionEnterprise(String mid)
	{
		String re = (String)jedis.get(userRegion, mid);
		return re==null?false:true;
	}
		
}

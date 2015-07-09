package com.sina.weibo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.Content;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.MFPThirdPartyInfo;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;
@SuppressWarnings("unused")
public class UserWeiboBehaviorReduce  extends Reducer<Text, Text, Text, Text>  {
	public static Logger logger = org.apache.log4j.Logger.getLogger(UserWeiboBehaviorReduce.class);
	private String charset = "UTF-8";
	private JedisUtils jedis = null;
	private String userRegion = null;
	private int expiredTime=Constant.DEFAULT_REDIS_EXPIRE_SECONDS;
	private int num=Constant.DEFAULT_USER_TAG_THRESHOLD;
	private int tryTime=1;
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException,InterruptedException {
			super.setup(context);
			Configuration conf = context.getConfiguration();
			FileSystem fs = FileSystem.get(conf);
			//String path = conf.get(Constant.CONFIG_PATH);
			String numStr=conf.get(Constant.USER_TAG_THRESHOLD);
			if(numStr!=null && !numStr.isEmpty()){
				num =Integer.parseInt(numStr);
			}
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
			String userRedisName = conf.get("USER_REDIS_NAME");
			tryTime = Integer.parseInt(conf.get(Constant.TRY_TIMES));
			HashMap<String,String> map = new HashMap<String,String>();
			map.put(userRegion, userRedisName);
			RedisConfig.getInstance().setRegionRedisMap(map);
			RedisConfig.getInstance().loadhdfs(hosts);
			jedis = JedisUtils.getInstance();

	}
	
	public void reduce(Text key,
			Iterable<Text> values, Context context) throws IOException,
			InterruptedException {
		byte[] ubyte = (byte[])jedis.get(userRegion, key.toString());
		if(ubyte!=null)
		{
			MFPThirdPartyInfo user = MFPThirdPartyInfo.parseFrom(ubyte);
			MFPThirdPartyInfo.Builder builder = user.toBuilder();
			String userId = user.getUserId();
			for(Text t:values)
			{
				String s = t.toString();
				String v[] = s.split("\001");
				logger.info("v.leng:"+v.length);
				logger.info("memberId:"+v[0]);
				//String userId = v[0];
				String action = v[1];
				String enId = v[2];
				String dateTime = v[3];
	        	Content.Builder content = Content.newBuilder();
	        	content.setAction(action);
				content.setContent(enId);
				content.setWeight(1);
				builder.addActions(content);
	        	
	        }
			putRedis(userRegion,userId,builder.build().toByteArray());
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


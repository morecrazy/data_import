package com.sina.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import com.mtarget.mapred.Constant;
import com.mtarget.proto.data.UserInfoProto;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.Content;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.MFPThirdPartyInfo;
import com.mtarget.proto.data.UserInfoProto.UserInfo;
import com.sina.ads.algo.cookie.CookieITJsonFormatter;
import com.sina.ads.algo.cookie.CookieITProto;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;
import com.sina.website.UserTagImportRedisMap;
@SuppressWarnings("unused")
public class UserTagJsonImportReduce  extends Reducer<Text, Text, Text,Text>  {
	protected Logger logger = Logger.getLogger(UserTagImportRedisMap.class);
	private JedisUtils jedis = null;
	private HashMap<String,String> tagMap =null;
	private String userRegion = null;
	private int expiredTime=Constant.DEFAULT_REDIS_EXPIRE_SECONDS;
	private int num=Constant.DEFAULT_USER_TAG_THRESHOLD;
	private int tryTime=1;
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException,InterruptedException {
		super.setup(context);
		Configuration conf = context.getConfiguration();
		FileSystem fs = FileSystem.get(conf);
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
		
		String path = conf.get(Constant.CONFIG_PATH);
		tagMap = new HashMap<String,String>();
		loadProperty(tagMap,path+Constant.TAG_PROPERTIES,fs);
	}
	public void reduce(Text key,
			Iterable<Text> values, Context context) throws IOException,
			InterruptedException {
		

		//HashMap<String,TagVo> tagMap = new HashMap<String,TagVo>();
		//MFPThirdPartyInfo.Builder builder = MFPThirdPartyInfo.newBuilder();
		UserInfo.Builder builder = UserInfo.newBuilder();
		for(Text t:values)
		{
			CookieITProto.CookieIT cookieIT = CookieITJsonFormatter.parseCookieIT(t.toString());
			List<CookieITProto.ITInfo> itList = cookieIT.getItListList();
			for(CookieITProto.ITInfo itInfo: itList)
			{
				UserInfoProto.UserInfo.Tag.Builder tag = UserInfoProto.UserInfo.Tag.newBuilder();
//				tag.setTag(itInfo.getId());
//				tag.setSinaWeight(itInfo.getSinaWeight());
//				tag.setRelevance(itInfo.getRelevance());
//				tag.setWeiboWeight(itInfo.getWeiboWeight());
//				tag.setTimestamp(itInfo.getLastShowTimestamp());
				//tag.set(itInfo.getLastShowTimestamp());
				builder.addTags(tag);
			}
		}
		putRedis(userRegion,key.toString(),builder.build().toByteArray());
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
	public class TagVo implements Comparable<TagVo> 
	{
		private float weight = 0;
		private String name;
		public float getWeight() {
			return weight;
		}
		public void setWeight(float weight) {
			this.weight = weight;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public int compareTo(TagVo o) {
			int result;
			if (weight > o.getWeight()) {
				result = 1;
			} else if (weight == o.getWeight()) {
				result=0;
			} else {
				result = -1;
			}
			return result;
		}
	
	}
	private void loadProperty(HashMap<String,String> map,String file,FileSystem fs){
		try{
			Path path = new Path(file);
			if(fs.exists(path)){
				InputStream input =(InputStream) fs.open(path);
				Properties prop = new Properties();
				prop.load(input);
				Set<Entry<Object,Object>> set = prop.entrySet();
				if(set!=null){
					for(Entry<Object,Object> en:set){
						map.put((String)en.getKey(),(String)en.getValue());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
}

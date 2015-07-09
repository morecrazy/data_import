package com.sina.enterprise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.mtarget.mapred.Constant;
import com.mtarget.mapred.Entity;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.Content;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.MFPThirdPartyInfo;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;


@SuppressWarnings("unused")
public class EnterpriseImportReduce  extends Reducer<Text, Text, Text, Text> {
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
			String path = conf.get(Constant.CONFIG_PATH);
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
			userRegion = conf.get("USER_REGION");
			String urlRedisName = conf.get("URL_REDIS_NAME");
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
		String memberId = null;
		HashMap<String,TagVo> tagMap = new HashMap<String,TagVo>();
		for(Text t:values)
		{
			String s = t.toString();
			String v[] = s.split(":");
			String url = v[1];
			String userId = v[0];
			memberId = v[2];
			String tags = (String)jedis.get(userRegion, url);
			if(tags!=null)
			{
				String tagArray[] = tags.split("\t");
				if(tagArray!=null)
				{
					for(String tag:tagArray )
					{
						String name = tag.split(":")[0];
						float weight = 0;
						weight = Float.parseFloat(tag.split(":")[1]);
						if(tagMap.get(name)!=null)
						{
							TagVo vo = tagMap.get(name);
							vo.setWeight(vo.getWeight()+weight);
							tagMap.put(name, vo);
						}
						else
						{
							TagVo vo = new TagVo();
							vo.setName(name);
							vo.setWeight(weight);
						}
					}
				}
			}

		}
		MFPThirdPartyInfo.Builder builder = MFPThirdPartyInfo.newBuilder();
		float min = 0;
		TagVo [] arrayTag = new TagVo[tagMap.values().size()];
		tagMap.values().toArray(arrayTag);
		Arrays.sort(arrayTag);
        for(int i =0 ;i<num;i++){
        	TagVo e = arrayTag[i];
	        	Content.Builder content = Content.newBuilder();
				content.setContent(e.getName());
				content.setWeight(e.getWeight());
				builder.addTags(content);
        	
        }
		if(memberId!=null&&!memberId.equals(""))
		{
			builder.setUserId(key.toString());
			putRedis(userRegion,memberId,builder.build().toByteArray());
		}
		else
		{
			putRedis(userRegion,key.toString(),builder.build().toByteArray());
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
			//重写Comparable接口的compareTo方法 
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
}


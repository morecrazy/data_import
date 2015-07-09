package com.sina.website;

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
public class UserTagImportRedisReduce  extends Reducer<Text, Text, Text, Text> {
	private String charset = "UTF-8";
/*	private JedisUtils jedis = null;
	private String urlRegion = null;
	private String userRegion = null;
	private int expiredTime=Constant.DEFAULT_REDIS_EXPIRE_SECONDS;*/
	private int num=Constant.DEFAULT_USER_TAG_THRESHOLD;
	private int tryTime=1;
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException,InterruptedException {
			super.setup(context);
			Configuration conf = context.getConfiguration();
			FileSystem fs = FileSystem.get(conf);
	}
	
	public void reduce(Text key,
			Iterable<Text> values, Context context) throws IOException,
			InterruptedException {
		String memberId = null;
		String url = null;
		String value = "";
		for(Text t:values)
		{
			String s = t.toString();
			String v[] = s.split(":");
			if(url==null)
			url = v[1];
			else
			{
				url = url+"|"+v[1];
			}
			//String userId = v[0];
			if(v.length>2&&v[2]!=null&&!v[2].equals(""))
			{
				//sessionid | url \t url |memberid 
				memberId = v[2];
			}
		}
		if(memberId!=null&&!memberId.equals(""))
			value = key.toString()+"|"+url+"|"+memberId;
		else value = key.toString()+"|"+url;
				
		context.write(key, new Text(value));

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


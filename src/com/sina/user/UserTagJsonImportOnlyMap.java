package com.sina.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.mtarget.mapred.Constant;
import com.mtarget.proto.data.UserInfoProto;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.Content;
import com.mtarget.proto.data.MFPThirdPartyInfoProto.MFPThirdPartyInfo;
import com.mtarget.proto.data.UserInfoProto.UserInfo;
import com.sina.ads.algo.cookie.CookieITJsonFormatter;
import com.sina.ads.algo.cookie.CookieITProto;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;
@SuppressWarnings("unused")
public class UserTagJsonImportOnlyMap extends Mapper<LongWritable, Text, Text, Text> {
	private JedisUtils jedis = null;
	private HashMap<String,String> tagMap =null;
	private String userRegion = null;
	private int expiredTime=Constant.DEFAULT_REDIS_EXPIRE_SECONDS;
	private int num=Constant.DEFAULT_USER_TAG_THRESHOLD;
	private int tryTime=1;
	@SuppressWarnings("unchecked")
	protected void setup(Context context) throws IOException,
			InterruptedException {
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
		//tagMap = new HashMap<String,String>();
		//loadProperty(tagMap,path+Constant.TAG_PROPERTIES,fs);
	}

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		if(value!=null){
			String line = value.toString().trim();


			//HashMap<String,TagVo> tagMap = new HashMap<String,TagVo>();
			//MFPThirdPartyInfo.Builder builder = MFPThirdPartyInfo.newBuilder();
			UserInfo.Builder builder = UserInfo.newBuilder();

				CookieITProto.CookieIT cookieIT = CookieITJsonFormatter.parseCookieIT(line);
				List<CookieITProto.ITInfo> itList = cookieIT.getItListList();
				for(CookieITProto.ITInfo itInfo: itList)
				{
					UserInfoProto.UserInfo.Tag.Builder tag = UserInfoProto.UserInfo.Tag.newBuilder();
//					tag.setTag(itInfo.getId());
//					tag.setSinaWeight(itInfo.getSinaWeight());
//					tag.setRelevance(itInfo.getRelevance());
//					tag.setWeiboWeight(itInfo.getWeiboWeight());
//					tag.setTimestamp(itInfo.getLastShowTimestamp());
					//tag.set(itInfo.getLastShowTimestamp());
					builder.addTags(tag);
				}
			
			putRedis(userRegion,cookieIT.getCookieId(),builder.build().toByteArray());
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

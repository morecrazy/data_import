package com.sina.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mtarget.mapred.Constant;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;
@SuppressWarnings("unused")
public class UserWeiboBehaviorTest    {
	private JedisUtils jedis = null;
	private String userRegion = "USER_REGION";
	private String redisName = "REDIS1";
	private int expiredTime=Constant.DEFAULT_REDIS_EXPIRE_SECONDS;
	private int num=Constant.DEFAULT_USER_TAG_THRESHOLD;
	private int tryTime=1;
	
	
	protected void init(){

			ArrayList<String> hosts = new ArrayList<String>();
			hosts.add("124.238.236.130:6374:REDIS1");
			hosts.add("124.238.236.130:6375:REDIS1");
			hosts.add("124.238.236.130:6376:REDIS1");
			hosts.add("124.238.236.130:6377:REDIS1");
			hosts.add("124.238.236.130:6378:REDIS1");
			hosts.add("124.238.236.130:6379:REDIS1");

			//logger.info(hosts);

			HashMap<String,String> map = new HashMap<String,String>();
			map.put(userRegion, redisName);
			RedisConfig.getInstance().setRegionRedisMap(map);
			RedisConfig.getInstance().loadhdfs(hosts);
			jedis = JedisUtils.getInstance();

	}
	
	public void test(String s) {
		Object o = jedis.get(userRegion, s);
		System.out.println(o);

	}
	

	public static void main(String arg[])
	{
		UserWeiboBehaviorTest test = new UserWeiboBehaviorTest();
		test.init();
		test.test(arg[0]);
	}

}


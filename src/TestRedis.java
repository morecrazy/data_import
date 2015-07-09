import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mtarget.proto.data.UserInfoProto.UserInfo;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;


public class TestRedis {
	public static void main(String[] args) throws Exception{
		String mfm = "123.126.53.109_TOMCAT_LINE_IMP_201211081100";
		ArrayList<String> hosts = new ArrayList<String>();
		hosts.add("10.79.96.27:6370:ADSERVER_REDIS");
/*		hosts.add("10.77.6.77:6382:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6381:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6380:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6379:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6378:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6377:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6376:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6375:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6374:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6373:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6372:ADSERVER_REDIS");
		hosts.add("10.77.6.77:6371:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6382:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6381:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6380:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6379:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6378:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6377:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6376:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6375:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6374:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6373:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6372:ADSERVER_REDIS");
		hosts.add("10.77.6.78:6371:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6382:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6381:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6380:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6379:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6378:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6377:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6376:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6375:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6374:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6373:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6372:ADSERVER_REDIS");
		hosts.add("10.77.6.82:6371:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6382:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6381:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6380:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6379:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6378:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6377:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6376:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6375:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6374:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6373:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6372:ADSERVER_REDIS");
		hosts.add("10.77.6.83:6371:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6382:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6381:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6380:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6379:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6378:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6377:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6376:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6375:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6374:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6373:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6372:ADSERVER_REDIS");
		hosts.add("10.77.6.84:6371:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6382:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6381:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6380:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6379:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6378:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6377:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6376:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6375:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6374:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6373:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6372:ADSERVER_REDIS");
		hosts.add("10.77.6.85:6371:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6382:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6381:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6380:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6379:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6378:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6377:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6376:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6375:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6374:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6373:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6372:ADSERVER_REDIS");
		hosts.add("10.77.6.86:6371:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6382:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6381:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6380:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6379:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6378:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6377:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6376:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6375:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6374:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6373:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6372:ADSERVER_REDIS");
		hosts.add("10.77.6.87:6371:ADSERVER_REDIS");*/

		String region = "USER_TAG_REGION";
		String redisName = "ADSERVER_REDIS";
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(region, redisName);
		RedisConfig.getInstance().setRegionRedisMap(map);
		RedisConfig.getInstance().loadhdfs(hosts);
		JedisUtils jedis = JedisUtils.getInstance();
		jedis.set(region, "test", 189l);
		Object o  = jedis.get(region,"test");
		System.out.println(o);
		
/*		UserInfo user = UserInfo.parseFrom(ubyte);
		List<com.mtarget.proto.data.UserInfoProto.UserInfo.Tag> taglist = user.getTagsList();	
		for(com.mtarget.proto.data.UserInfoProto.UserInfo.Tag a:taglist)
		{
			System.out.println("tag:"+a.getTag()+" sinawaight:"+a.getSinaWeight());
		}
			
		int i = 0;
		int j = i >>> 1;
		System.out.println(j);
		System.exit(1);*/
		
	}
	

}

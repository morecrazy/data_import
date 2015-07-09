import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.google.protobuf.JsonFormat;
import com.mtarget.proto.data.UserInfoProto.UserInfo;
import com.sina.config.RedisConfig;
import com.sina.util.JedisUtils;


public class ImportRedisTestData {
	public static void main(String[] args) throws Exception{

		//String mfm = "0000006a.96894d14.4ff2546c.403973d1";
		//String mfm = "2.5721.29794.28756.32062.22435.17034.6483.85.15690.6522";
		String mfm = "123.126.53.109_TOMCAT_LINE_IMP_201211081120";
		//String mfm = "000000fc.8efb45c1.4fcacd57.14605253";
		String json = "{\"tags\": [{\"tag\": 20210,\"weibo_weight\": 0.0,\"timestamp\": 1341158400000,\"sina_weight\": 0.15996,\"relevance\": 1.0}, "+
"{\"tag\": 20109,\"weibo_weight\": 0.0,\"timestamp\": 1341158400000,\"sina_weight\": 0.36,\"relevance\": 1.0}, "+
"{\"tag\": 20006,\"weibo_weight\": 0.0,\"timestamp\": 1341158400000,\"sina_weight\": 0.185996,\"relevance\": 1.0}, "+
"{\"tag\": 20108,\"weibo_weight\": 0.0,\"timestamp\": 1341158400000,\"sina_weight\": 0.185996,\"relevance\": 1.0}]}";

		ArrayList<String> hosts = new ArrayList<String>();
		hosts.add("123.126.53.109:6374:ADSERVER_REDIS");
/*		hosts.add("123.126.53.109:6379:ADSERVER_REDIS");
		hosts.add("123.126.53.109:6378:ADSERVER_REDIS");
		hosts.add("123.126.53.109:6377:ADSERVER_REDIS");
		hosts.add("123.126.53.109:6376:ADSERVER_REDIS");
		hosts.add("123.126.53.109:6375:ADSERVER_REDIS");
		hosts.add("123.126.53.109:6374:ADSERVER_REDIS");*/
/*		hosts.add("10.79.96.27:6370:CENTER_REDIS");
		hosts.add("10.79.96.28:6370:CENTER_REDIS");*/
/*		hosts.add("10.79.96.32:6370:CENTER_REDIS");
		hosts.add("10.79.96.33:6370:CENTER_REDIS");
		hosts.add("10.79.96.34:6370:CENTER_REDIS");
		hosts.add("10.79.96.35:6370:CENTER_REDIS");
		hosts.add("10.79.96.36:6370:CENTER_REDIS");
		hosts.add("10.79.96.37:6370:CENTER_REDIS");
		hosts.add("10.79.96.38:6370:CENTER_REDIS");
		hosts.add("10.79.96.39:6370:CENTER_REDIS");
		hosts.add("10.79.96.40:6370:CENTER_REDIS");
		hosts.add("10.79.96.41:6370:CENTER_REDIS");*/
/*		hosts.add("10.77.6.77:6370:CENTER_REDIS");
		hosts.add("10.77.6.78:6370:CENTER_REDIS");*/
/*		hosts.add("10.77.6.82:6370:CENTER_REDIS"); 
		hosts.add("10.77.6.83:6370:CENTER_REDIS"); 
		hosts.add("10.77.6.84:6370:CENTER_REDIS"); 
		hosts.add("10.77.6.85:6370:CENTER_REDIS"); 
		hosts.add("10.77.6.86:6370:CENTER_REDIS"); 
		hosts.add("10.77.6.87:6370:CENTER_REDIS");*/
		

		
		
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
		
		
		/*
		hosts.add("10.79.96.32:6382:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6381:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6380:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6379:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6378:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6377:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6376:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6375:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6374:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6373:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6372:ADSERVER_REDIS");
		hosts.add("10.79.96.32:6371:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6382:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6381:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6380:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6379:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6378:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6377:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6376:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6375:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6374:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6373:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6372:ADSERVER_REDIS");
		hosts.add("10.79.96.33:6371:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6382:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6381:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6380:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6379:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6378:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6377:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6376:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6375:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6374:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6373:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6372:ADSERVER_REDIS");
		hosts.add("10.79.96.34:6371:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6382:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6381:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6380:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6379:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6378:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6377:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6376:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6375:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6374:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6373:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6372:ADSERVER_REDIS");
		hosts.add("10.79.96.35:6371:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6382:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6381:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6380:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6379:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6378:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6377:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6376:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6375:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6374:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6373:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6372:ADSERVER_REDIS");
		hosts.add("10.79.96.36:6371:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6382:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6381:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6380:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6379:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6378:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6377:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6376:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6375:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6374:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6373:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6372:ADSERVER_REDIS");
		hosts.add("10.79.96.37:6371:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6382:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6381:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6380:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6379:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6378:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6377:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6376:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6375:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6374:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6373:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6372:ADSERVER_REDIS");
		hosts.add("10.79.96.38:6371:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6382:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6381:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6380:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6379:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6378:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6377:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6376:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6375:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6374:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6373:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6372:ADSERVER_REDIS");
		hosts.add("10.79.96.39:6371:ADSERVER_REDIS");*/



		String region = "USER_TAG_REGION";
		String redisName = "ADSERVER_REDIS";
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(region, redisName);
		RedisConfig.getInstance().setRegionRedisMap(map);
		RedisConfig.getInstance().loadhdfs(hosts);
		JedisUtils jedis = JedisUtils.getInstance();
		
    	UserInfo.Builder builder = UserInfo.newBuilder();
        JsonFormat.merge(json, builder);
		/*byte[] ubyte = (byte[])jedis.get(region,mfm);
		if(ubyte!=null)
		{
			MFPThirdPartyInfo user = MFPThirdPartyInfo.parseFrom(ubyte);
			String userId = user.getUserId();
			for(Content  content:user.getActionsList())
			{
				content.getAction();
				System.out.println("enId:"+content.getContent());
			}
			System.out.println("userId:"+userId);
		}*/
		
		
/*		MFPThirdPartyInfo.Builder builder = MFPThirdPartyInfo.newBuilder();
		Content.Builder tag1 = Content.newBuilder();
		tag1.setAction("1");
		tag1.setContent("体育");
		tag1.setWeight(0.5f);
		builder.addTags(tag1);
					
		Content.Builder tag2 = Content.newBuilder();
		tag2.setAction("1");
		tag2.setContent("旅游");
		tag2.setWeight(0.5f);
		builder.addTags(tag2);
		
		Content.Builder content3 = Content.newBuilder();
		content3.setAction("0");
		content3.setContent("音乐");
		content3.setWeight(0.6f);
		builder.addTags(content3);
		
		Content.Builder content4 = Content.newBuilder();
		content4.setAction("1");
		content4.setContent("博客");
		content4.setWeight(0.7f);
		builder.addTags(content4);*/
		
/*		Content.Builder action1 = Content.newBuilder();
		action1.setAction("1");
		action1.setContent("1840562032");
		action1.setWeight(1f);
		builder.addActions(action1);
		
		Content.Builder action2 = Content.newBuilder();
		action2.setAction("1");
		action2.setContent("1871925961");
		action2.setWeight(1f);
		builder.addActions(action2);*/
		
/*		Content.Builder action3 = Content.newBuilder();
		action3.setAction("1");
		action3.setContent("1771925968");
		action3.setWeight(1f);
		builder.addActions(action3);
		
		Content.Builder action4 = Content.newBuilder();
		action4.setAction("1");
		action4.setContent("1771925967");
		action4.setWeight(1f);
		builder.addActions(action4);
		
		Content.Builder action5 = Content.newBuilder();
		action5.setAction("1");
		action5.setContent("1771925966");
		action5.setWeight(1f);
		builder.addActions(action5);*/
		
		
		
	        				
/*			for(int i=0;i<5;i++){
				try{
					if(jedis.set(region, mfm, builder.build().toByteArray(),1680000)){
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}*/
/*        long l = System.nanoTime();
        System.out.println(l);
			byte[] ubyte = (byte[])jedis.get(region,mfm);
			
			long t = System.nanoTime();
			System.out.println(l-t);
			UserInfo user = UserInfo.parseFrom(ubyte);
			
		System.out.println(user.getTagsCount());
		List<com.mtarget.proto.data.UserInfoProto.UserInfo.Tag> taglist = user.getTagsList();
		
		
		for(com.mtarget.proto.data.UserInfoProto.UserInfo.Tag a:taglist)
		{
			System.out.println("tag:"+a.getTag()+" sinawaight:"+a.getSinaWeight());
		}*/
        
        Object o = jedis.get(region,mfm);
        HashMap s = (HashMap)o;
        AtomicLong i = (AtomicLong)s.get("82");
		System.out.println("userId:"+i.get());
	}

}

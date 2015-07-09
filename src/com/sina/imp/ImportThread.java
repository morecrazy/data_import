package com.sina.imp;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.codehaus.jackson.map.ObjectMapper;

import com.sina.proto.data.UserInfoProto.UserInfo;
import com.sina.util.JedisUtils;

public class ImportThread implements Callable<String> {
	private JedisUtils jedis = null;
	private ArrayList<File> files = null;
	private String line = null;
	private String region = null;
	
	static ObjectMapper mapper =  new ObjectMapper();
	
	
	public  ImportThread(String line)
	{
		this.line = line;
		this.region = ImportConstant.USER_REGION;
	}
	public String call()
	{
		if(line!=null) 	
		{
			try {
				String pros [] = line.split("\t");
	    		UserInfo.Builder builder = UserInfo.newBuilder();
	    		UserInfoJson json = null;
	    		try
	    		{
	    			json = mapper.readValue(pros[1], UserInfoJson.class); 
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("ParseException: json format wrong " + pros[1]);
	    		}
	    		if(json!=null)
	    		{
	    			if (json.getGender() != null)
	    				builder.setGender(Integer.parseInt(json.getGender()));
		    		if(json.getAge_list()!=null)
		    		{
			    		for(Age age:json.getAge_list())
			    		{
			    			
			    			UserInfo.Age.Builder agebuilder = UserInfo.Age.newBuilder();
			    			agebuilder.setId(Integer.parseInt(age.getId()));
			    			agebuilder.setWeight(Float.parseFloat(age.getWeight()));
			    			builder.addAges(agebuilder);
			    		}
		    		}
		    		if(json.getIt_list()!=null)
		    		{
			    		for(int i=0;i<ImportConstant.TAG_NUM;i++)
			    		{
			    			if(json.getIt_list().size()>=(i+1))
			    			{
				    			UserInfo.Tag.Builder tagbuilder = UserInfo.Tag.newBuilder();
				    			tagbuilder.setId(Integer.parseInt(json.getIt_list().get(i).getId()));
				    			tagbuilder.setWeight(Float.parseFloat(json.getIt_list().get(i).getWeight()));
				    			builder.addTags(tagbuilder);
			    			}
			    		
		    			}
	    			}
		    		int i;
		    		if (json.getGender_prob()!=null) {
		    			for (Gender gender: json.getGender_prob()) {
		    				UserInfo.Gender.Builder genderbuilder = UserInfo.Gender.newBuilder();
		    				genderbuilder.setId(Integer.parseInt(gender.getId()));
		    				genderbuilder.setWeight(Float.parseFloat(gender.getWeight()));
		    				builder.addGenders(genderbuilder);
		    			}
		    		}
		    		
		    		if (json.getCrowd_list() != null) {
		    			i = 0;
		    			for (Crowd crowd: json.getCrowd_list()) {
		    				if (++i > ImportConstant.TAG_NUM) break;
		    				UserInfo.Group.Builder groupbuilder = UserInfo.Group.newBuilder();
		    				groupbuilder.setId(Integer.parseInt(crowd.getId()));
		    				groupbuilder.setWeight((Float.parseFloat(crowd.getWeight())));
		    				builder.addGroups(groupbuilder);
		    			}
		    		}
		    		
		    		if (json.getAttachment() != null) {
		    			i = 0;
		    			for (Attachment attachment: json.getAttachment()) {
		    				if (++i > ImportConstant.ATTACHMENT_NUM) break;
		    				UserInfo.Att.Builder attBuilder = UserInfo.Att.newBuilder();
		    				attBuilder.setType(attachment.getType());
		    				attBuilder.setId(attachment.getId());
		    				attBuilder.setWeight(Float.parseFloat(attachment.getWeight()));
		    				builder.addAtts(attBuilder);
		    			}
		    		}
					jedis = JedisUtils.getInstance();
					putRedis(region,pros[0],builder.build().toByteArray());
					num(pros[0]);
/*		    		byte [] b = builder.build().toByteArray();
		    		UserInfo u = UserInfo.parseFrom(b);
		    		System.out.println(u.getTagsList().get(0).getWeight());*/
		    		
	    		}
			} catch (Exception e) {
				System.out.println("ParseException: json format wrong " + line);
				e.printStackTrace();
			}
		}

		return "success";
	}

	public  void num(String key)
	{
		synchronized (ImportConstant.test)
		{
			//System.out.println("sessionNum: "+ImportConstant.sessionNum + "key : "+key );
			ImportConstant.sessionNum--;
		}
	}
	public void putRedis(String region,String key,Object o)
	{
		for(int i=0;i<1;i++){
			try{
				if(jedis.set(region, key, o,ImportConstant.EXPIRE_SECONDS)){
					break;
				}
			}catch(Exception e){
				//System.out.println("redis.clients.jedis.exceptions.JedisConnectionException: java.net.SocketTimeoutException: Read timed out");
				System.out.println("sessionNum: "+ImportConstant.sessionNum+" EXCEPTION::::"+e.getMessage()+"  try number"+i+"  key:"+ key);

			}
		}
	}

	public JedisUtils getJedis() {
		return jedis;
	}
	public void setJedis(JedisUtils jedis) {
		this.jedis = jedis;
	}
	public ArrayList<File> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

}

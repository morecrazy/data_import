package com.sina.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ImportLoadConfig {
	static Map<String, String> configMap = new HashMap<String, String>();
	public static void load(String config) {
		
		try {
			config = config==null?"/data1/temp/optimus/data_import/config.properties":config;
			Configuration configuration = new PropertiesConfiguration(config);
			Iterator<String> it = configuration.getKeys();
			while (it.hasNext()) {
				String key = it.next();
				if(key.equals("USER_REGION")&&configuration.getString(key)!=null)
				{
						ImportConstant.USER_REGION = configuration.getString(key);
				}
				if(key.equals("HOST_PATH")&&configuration.getString(key)!=null)
				{
					ImportConstant.HOST_PATH = configuration.getString(key);
				}
				if(key.equals("REDIS_NAME")&&configuration.getString(key)!=null)
				{
					ImportConstant.REDIS_NAME = configuration.getString(key);
				}
				if(key.equals("IMPORT_PATH")&&configuration.getString(key)!=null)
				{
					ImportConstant.IMPORT_PATH = configuration.getString(key);
				}
				if(key.equals("THRED_NUM")&&configuration.getString(key)!=null)
				{
					ImportConstant.THRED_NUM = configuration.getString(key);
				}
				if(key.equals("EXPIRE_SECONDS")&&configuration.getString(key)!=null)
				{
					ImportConstant.EXPIRE_SECONDS = configuration.getInt(key);
				}
				if(key.equals("TAG_NUM")&&configuration.getString(key)!=null)
				{
					ImportConstant.TAG_NUM = configuration.getInt(key);
				}
				if(key.equals("ATTACHMENT_NUM")&&configuration.getString(key)!=null)
				{
					ImportConstant.ATTACHMENT_NUM = configuration.getInt(key);
				}
				configMap.put(key, configuration.getString(key));
			}
		} catch (ConfigurationException e) {
			configMap.clear();
		}
	}

}

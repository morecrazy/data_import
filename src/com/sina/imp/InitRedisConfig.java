package com.sina.imp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sina.config.RedisConfig;

public class InitRedisConfig {
	public static void initConfig()
	{
		ArrayList<String> hosts = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(ImportConstant.HOST_PATH);
			
            BufferedReader br = new BufferedReader(fr);    
            String myreadline;    
            while (br.ready()) {
                myreadline = br.readLine();//读取一行
                hosts.add(myreadline);
            }
            br.close();
            br.close();
            fr.close();
	       } catch (IOException e) {
	            e.printStackTrace();
	        }

		HashMap<String,String> map = new HashMap<String,String>();
		map.put(ImportConstant.USER_REGION, ImportConstant.REDIS_NAME);
		RedisConfig.getInstance().setRegionRedisMap(map);
		RedisConfig.getInstance().loadhdfs(hosts);
	}

}

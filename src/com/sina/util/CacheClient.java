package com.sina.util;

import java.util.Map;


/**
 * 缓存接口,含本地缓存和远端缓存
 * 
 * @Title: MFP_SERVER
 * @FileName: CacheClient.java
 * @Description:
 * @Copyright: Copyright (c) 2008
 * @Company:
 * @author Sun Jue
 * @Create Date: 2011-6-15
 */
public interface CacheClient extends Constant {

	String REGION_LINK_KEY = "_";

	public boolean set(String region, String key, Object value);

	public Object get(String region, String key);

	public Map<String, Object> getMulti(String region, String[] keys);

	public boolean delete(String region, String key);

}

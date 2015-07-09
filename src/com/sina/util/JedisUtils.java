package com.sina.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

import com.sina.config.DefineConfig;
import com.sina.config.RedisConfig;

/**
 * Redis链接客户端，底层使用Jedis<BR>
 * “static final”方法供主控机使用，实例方法供AD_Server使用<BR>
 * “getBadRedisServer”方法不区分主控机和AD_Server
 * 
 * @Title: MFP_SERVER
 * @FileName: JedisUtils.java
 * @Description:
 * @Copyright: Copyright (c) 2007
 * @Company:
 * @author Sun Jue
 * @Create Date: 2011-6-8
 */
public final class JedisUtils implements CacheClient {

	private static final JedisUtils instance = new JedisUtils();

	private JedisUtils() {
	}

	public static final JedisUtils getInstance() {
		return instance;
	}

	// REDIS配置信息
	private static final RedisConfig redisConfig = RedisConfig.getInstance();

	/**
	 * 在指定的region中设置key及其对应的Value，并在一天后过期
	 */
	@Override
	public boolean set(String region, String key, Object value) {
		return set(
				region,
				key,
				value,
				DefineConfig.getInstance().getDefineDataInt(
						REDIS_EXPIRE_SECONDS, DEFAULT_REDIS_EXPIRE_SECONDS));
	}

	/**
	 * 在指定的region中设置key及其对应的Value，并在expireSec秒后过期
	 */
	public boolean set(String region, String key, Object value, int expireSec) {
		if (region == null || key == null) {
			return false;
		}

		ShardedJedisPool pool = redisConfig.getShardedJedisPool(redisConfig
				.getRedisName(region));
		if (pool == null) {
			return false;
		}
		ShardedJedis sj = pool.getResource();
		try{
			sj.setex(key.getBytes(), expireSec, Utils.convertObject2Byte(value));
		}
		catch(JedisException e)
		{
			//System.out.println("ffff:"+e.getMessage());
			pool.returnResource(sj);
			throw e;
		}
		pool.returnResource(sj);
		return true;
	}

	/**
	 * 从指定的region中取出key对应的Value
	 */
	@Override
	public Object get(String region, String key) {
		if (region == null || key == null) {
			return null;
		}

		ShardedJedisPool pool = redisConfig.getShardedJedisPool(redisConfig
				.getRedisName(region));
		if (pool == null) {
			return null;
		}
		ShardedJedis sj = pool.getResource();
		Object o = Utils.convertByte2Object(sj.get(key.getBytes()));
		pool.returnResource(sj);
		return o;
	}

	/**
	 * 从指定的region中批量取出key对应的Value
	 */
	@Override
	public Map<String, Object> getMulti(String region, final String[] keys) {
		if (region == null || keys == null || keys.length == 0) {
			return new HashMap<String, Object>(0);
		}

		Map<String, Object> result = new HashMap<String, Object>(keys.length);
		ShardedJedisPool pool = redisConfig.getShardedJedisPool(redisConfig
				.getRedisName(region));
		if (pool == null) {
			return result;
		}
		ShardedJedis sj = pool.getResource();
		ShardedJedisPipeline pip = new ShardedJedisPipeline() {
			@Override
			public void execute() {
				for (int i = 0; i < keys.length; i++) {
					this.get(keys[i]);
				}
			}
		};
		List<Object> lst = sj.pipelined(pip);
		int i = 0;
		for (Object o : lst) {
			result.put(keys[i++], Utils.convertByte2Object((byte[]) o));
		}
		pool.returnResource(sj);
		return result;
	}

	/**
	 * 从指定的redis中删除指定的key
	 */
	@Override
	public boolean delete(String region, String key) {
		if (region == null || key == null) {
			return false;
		}

		ShardedJedisPool pool = redisConfig.getShardedJedisPool(redisConfig
				.getRedisName(region));
		if (pool == null) {
			return false;
		}
		ShardedJedis sj = pool.getResource();
		long l = sj.del(key);
		pool.returnResource(sj);
		return l > 0;
	}

	/**
	 * 在指定的region中设置key及其对应的Value，并在一天后过期
	 */
	public static final boolean setValue(String redisName, String key,
			Object value) {
		return setValue(
				redisName,
				key,
				value,
				DefineConfig.getInstance().getDefineDataInt(
						REDIS_EXPIRE_SECONDS, DEFAULT_REDIS_EXPIRE_SECONDS));
	}

	/**
	 * 在指定的redis中设置key及其对应的Value，并在expireSec秒后过期
	 */
	public static final boolean setValue(String redisName, String key,
			Object value, int expireSec) {
		if (redisName == null || key == null) {
			return false;
		}

		ShardedJedisPool pool = redisConfig.getShardedJedisPool(redisName);
		if (pool == null) {
			return false;
		}
		ShardedJedis sj = pool.getResource();
		sj.setex(key.getBytes(), expireSec, Utils.convertObject2Byte(value));
		pool.returnResource(sj);
		return true;
	}

	/**
	 * 从指定的redis中取出key对应的Value
	 */
	public static final Object getValue(String redisName, String key) {
		if (redisName == null || key == null) {
			return null;
		}

		ShardedJedisPool pool = redisConfig.getShardedJedisPool(redisName);
		if (pool == null) {
			return null;
		}
		ShardedJedis sj = pool.getResource();
		Object o = Utils.convertByte2Object(sj.get(key.getBytes()));
		pool.returnResource(sj);
		return o;
	}

	/**
	 * 从指定的redis中批量取出key对应的Value
	 */
	public static final Map<String, Object> getMultiValue(String redisName,
			final String[] keys) {
		if (redisName == null || keys == null || keys.length == 0) {
			return new HashMap<String, Object>(0);
		}

		Map<String, Object> result = new HashMap<String, Object>(keys.length);
		ShardedJedisPool pool = redisConfig.getShardedJedisPool(redisName);
		if (pool == null) {
			return result;
		}
		ShardedJedis sj = pool.getResource();
		ShardedJedisPipeline pip = new ShardedJedisPipeline() {
			@Override
			public void execute() {
				for (int i = 0; i < keys.length; i++) {
					this.get(keys[i]);
				}
			}
		};
		List<Object> lst = sj.pipelined(pip);
		int i = 0;
		for (Object o : lst) {
			result.put(keys[i++], Utils.convertByte2Object((byte[]) o));
		}
		pool.returnResource(sj);
		return result;
	}

	/**
	 * 从指定的redis中删除指定的key
	 */
	public static final boolean deleteValue(String redisName, String key) {
		if (redisName == null || key == null) {
			return false;
		}

		ShardedJedisPool pool = redisConfig.getShardedJedisPool(redisName);
		if (pool == null) {
			return false;
		}
		ShardedJedis sj = pool.getResource();
		long l = sj.del(key);
		pool.returnResource(sj);
		return l > 0;
	}

	/**
	 * 返回链接不上的REDIS的别名列表,如果返回空列表则表示所有的REDIS都是好的
	 * 
	 * @return
	 */
	public static final List<String> getBadRedisServer() {
		List<String> result = new ArrayList<String>();
		for (Entry<String, ShardedJedisPool> redis : redisConfig.getRedisMap()
				.entrySet()) {
			ShardedJedis sj = redis.getValue().getResource();
			if (validateObject(sj)) {
				redis.getValue().returnResource(sj);
			} else {
				result.add(redis.getKey());
				redis.getValue().returnBrokenResource(sj);
			}
		}
		return Collections.unmodifiableList(result);
	}

	/**
	 * 验证REDIS集群中的所有REDIS是否都可以连接上,如果有一个链接不上则返回false
	 * 
	 * @param jedis
	 * @return
	 */
	public static final boolean validateObject(final ShardedJedis jedis) {
		try {
			for (Jedis shard : jedis.getAllShards()) {
				if (!shard.ping().equals("PONG")) {
					return false;
				}
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}

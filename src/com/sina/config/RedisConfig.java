package com.sina.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import com.sina.config.load.ConfigLoad;
import com.sina.util.Constant;



public class RedisConfig extends ConfigLoad implements Constant {

	private static final int REDIS_CONNECTION_TIMEOUT = 4000;

	private static final RedisConfig instance = new RedisConfig();

	private RedisConfig() {
	}

	public static RedisConfig getInstance() {
		return instance;
	}

	private String hostFile;
	private Map<String, String> regionRedisMap = Collections
			.unmodifiableMap(new HashMap<String, String>());
	private Map<String, ShardedJedisPool> redisMap = Collections
			.unmodifiableMap(new HashMap<String, ShardedJedisPool>());

	public Map<String, String> getRegionRedisMap() {
		return regionRedisMap;
	}

	/**
	 * key为Region,value为redisName
	 * 
	 * @param regionRedisMap
	 */
	public void setRegionRedisMap(Map<String, String> regionRedisMap) {
		if (regionRedisMap != null) {
			this.regionRedisMap = Collections.unmodifiableMap(regionRedisMap);
		} else {
			this.regionRedisMap = Collections
					.unmodifiableMap(new HashMap<String, String>());
		}

	}

	public String getHostFile() {
		return hostFile;
	}

	public void setHostFile(String hostFile) {
		this.hostFile = hostFile == null ? null : hostFile.trim();
	}

	public String getRedisName(String region) {
		String redisName = this.regionRedisMap.get(region);
		return redisName == null ? "" : redisName;
	}

	@Override
	public void load() {
		//logger.info("RedisConfig Load Start...");

		Map<String, ShardedJedisPool> fromJdbc = loadFromJDBC();

		Map<String, ShardedJedisPool> fromFile = loadFromFile();

		// 数据库中配置的优先级高于文件配置的优先级
		fromFile.putAll(fromJdbc);

		fromJdbc = this.redisMap;

		this.redisMap = Collections.unmodifiableMap(fromFile);

		//logger.info("RedisConfig Load End..." + this.redisMap);

		// TODO redis重新载入后，老的链接需要销毁
		// destoryOldJedis(fromJdbc);
	}

	public void loadhdfs(List<String> list) {
		//logger.info("RedisConfig Load Start...");

		Map<String, ShardedJedisPool> fromFile = loadFromFile(list);

		this.redisMap = Collections.unmodifiableMap(fromFile);
		//logger.info("RedisConfig Load End..." + this.redisMap);
	}

	private Map<String, ShardedJedisPool> loadFromFile(List<String> list) {
		if (list == null || list.isEmpty()) {
			return new HashMap<String, ShardedJedisPool>();
		}
		Map<String, List<JedisShardInfo>> map = new HashMap<String, List<JedisShardInfo>>();

		for (String line : list) {
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}
			// ip:port:redisName|ip:port|ip:port
			String[] redises = line.split("\\|");
			JedisShardInfo jedisShardInfo = null;
			JedisShardInfo temJedisShardInfo = null;
			for (int i=0;i<redises.length;i++)
			{
				String[] str = redises[i].split(":");
				if(i == 0)
				{
					jedisShardInfo = new JedisShardInfo(str[0], Integer.parseInt(str[1]),
							REDIS_CONNECTION_TIMEOUT, str[0] + ":" + str[1]);
					List<JedisShardInfo> lst = map.get(str[2]);
					if (lst == null) {
						lst = new ArrayList<JedisShardInfo>();
						map.put(str[2], lst);
					}
					lst.add(jedisShardInfo);
					temJedisShardInfo = jedisShardInfo;
				}
				else
				{
					JedisShardInfo bakJedisShardInfo = new JedisShardInfo(str[0], Integer.parseInt(str[1]),
							REDIS_CONNECTION_TIMEOUT, str[0] + ":" + str[1]);
					temJedisShardInfo.setBakJedisShardInfo(bakJedisShardInfo);
					temJedisShardInfo = bakJedisShardInfo;
					if(str.length == 3)
					{
						jedisShardInfo.setName(str[0] + ":" + str[1]);
					}
				}
				
			}
		}
		Config poolConfig = getSharededJedisPoolConfig();

		Map<String, ShardedJedisPool> shardedJedisPool = new HashMap<String, ShardedJedisPool>();
		for (Entry<String, List<JedisShardInfo>> entry : map.entrySet()) {
			shardedJedisPool.put(entry.getKey(), new ShardedJedisPool(
					poolConfig, entry.getValue()));
		}

		return shardedJedisPool;
	}

	private Map<String, ShardedJedisPool> loadFromFile() {
		if (hostFile == null || hostFile.isEmpty()) {
			return new HashMap<String, ShardedJedisPool>();
		}

		File file = new File(hostFile);
		if (!file.exists()) {
			return new HashMap<String, ShardedJedisPool>();
		}

		Map<String, List<JedisShardInfo>> map = new HashMap<String, List<JedisShardInfo>>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;

			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty()) {
					continue;
				}
				// ip:port:redisName
				String[] str = line.split(":");
				List<JedisShardInfo> lst = map.get(str[2]);
				if (lst == null) {
					lst = new ArrayList<JedisShardInfo>();
					map.put(str[2], lst);
				}
				lst.add(new JedisShardInfo(str[0], Integer.parseInt(str[1]),
						REDIS_CONNECTION_TIMEOUT, str[0] + ":" + str[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Config poolConfig = getSharededJedisPoolConfig();

		Map<String, ShardedJedisPool> shardedJedisPool = new HashMap<String, ShardedJedisPool>();
		for (Entry<String, List<JedisShardInfo>> entry : map.entrySet()) {
			shardedJedisPool.put(entry.getKey(), new ShardedJedisPool(
					poolConfig, entry.getValue()));
		}

		return shardedJedisPool;
	}

	/**
	 * table create sql:
	 * 
	 * 
	 * CREATE TABLE IF NOT EXISTS `t_redis_config` ( `id` int(11) NOT NULL
	 * auto_increment, `host` varchar(15) NOT NULL, `port` int(11) NOT NULL,
	 * `redisName` varchar(10) NOT NULL, `status` char(1) default NULL COMMENT
	 * '状态0有效1暂停2无效', PRIMARY KEY (`id`), UNIQUE KEY `IPORT` (`host`,`port`) )
	 * ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
	 */
	private Map<String, ShardedJedisPool> loadFromJDBC() {
/*		if (jdbcTemplate == null) {
			return new HashMap<String, ShardedJedisPool>();
		}

		String sql = "select `host` , `port` , `redisName` from t_redis_config";

		if (!regionRedisMap.isEmpty()) {
			StringBuilder sb = new StringBuilder(sql);
			sb.append(" where `redisName` in (");
			for (String str : regionRedisMap.values()) {
				sb.append("'").append(str).append("',");
			}

			sb.setCharAt(sb.length() - 1, ')');
			sql = sb.toString();
		}

		Map<String, List<JedisShardInfo>> map = new HashMap<String, List<JedisShardInfo>>();

		List<Map<String, Object>> sqlResult = jdbcTemplate.queryForList(sql);

		if (sqlResult != null && sqlResult.size() > 0) {
			for (Map<String, Object> row : sqlResult) {
				String host = (String) row.get("host");
				Integer port = (Integer) row.get("port");
				String redisName = (String) row.get("redisName");

				List<JedisShardInfo> lst = map.get(redisName);
				if (lst == null) {
					lst = new ArrayList<JedisShardInfo>();
					map.put(redisName, lst);
				}
				lst.add(new JedisShardInfo(host, port,
						REDIS_CONNECTION_TIMEOUT, host + ":" + port));
			}
		}

		Config poolConfig = getSharededJedisPoolConfig();

		Map<String, ShardedJedisPool> shardedJedisPool = new HashMap<String, ShardedJedisPool>();
		for (Entry<String, List<JedisShardInfo>> redis : map.entrySet()) {
			shardedJedisPool.put(redis.getKey(), new ShardedJedisPool(
					poolConfig, redis.getValue()));
		}

		return shardedJedisPool;*/
		return null;
	}

	private Config getSharededJedisPoolConfig() {
		// redis池中的待分配redis链接
		Config poolConfig = new Config();
		// controls the maximum number of objects that can be allocated by the
		// pool (checked out to clients, or idle awaiting checkout) at a given
		// time.When non-positive, there is no limit to the number of objects
		// that can be managed by the pool at one time. When maxActive is
		// reached, the pool is said to be exhausted.
		poolConfig.maxActive = 80;
		// controls the maximum number of objects that can sit idle in the pool
		// at any time. When negative, there is no limit to the number of
		// objects that may be idle at one time.
		poolConfig.maxIdle = 80;
		// borrowObject() will block (invoke Object.wait()) until a new or idle
		// object is available. If a positive maxWait value is supplied, then
		// borrowObject() will block for at most that many milliseconds, after
		// which a NoSuchElementException will be thrown. If maxWait is
		// non-positive, the borrowObject() method will block indefinitely.
		poolConfig.maxWait = 180000;
		poolConfig.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
		// indicates how long the eviction thread should sleep before "runs" of
		// examining idle objects. When non-positive, no eviction thread will be
		// launched.
		poolConfig.timeBetweenEvictionRunsMillis = 7000;
		// specifies the minimum amount of time that an object may sit idle in
		// the pool before it is eligible for eviction due to idle time. When
		// non-positive, no object will be dropped from the pool due to idle
		// time alone. This setting has no effect unless
		// timeBetweenEvictionRunsMillis > 0.
		poolConfig.minEvictableIdleTimeMillis = 14000;
		// determines the number of objects examined in each run of the idle
		// object evictor.
		poolConfig.numTestsPerEvictionRun = 8;
		// indicates whether or not idle objects should be validated using the
		// factory's PoolableObjectFactory.validateObject(java.lang.Object)
		// method. Objects that fail to validate will be dropped from the pool.
		// This setting has no effect unless timeBetweenEvictionRunsMillis > 0.
		// The default setting for this parameter is false.
		poolConfig.testWhileIdle = true;// false;// true;
		// the pool will attempt to validate each object before it is returned
		// from the borrowObject() method.Objects that fail to validate will be
		// dropped from the pool, and a different object will be borrowed.
		poolConfig.testOnBorrow = false;// false;// true;
		// the pool will attempt to validate each object before it is returned
		// to the pool in the returnObject(java.lang.Object) method.Objects that
		// fail to validate will be dropped from the pool.
		poolConfig.testOnReturn = false;// true;
		return poolConfig;
	}

	@SuppressWarnings("unused")
	private void destoryOldJedis(Map<String, ShardedJedisPool> tmp) {
		for (Entry<String, ShardedJedisPool> entry : tmp.entrySet()) {
			entry.getValue().destroy();
		}
	}

	public ShardedJedisPool getShardedJedisPool(String redisName) {
		return this.redisMap.get(redisName);
	}

	public Map<String, ShardedJedisPool> getRedisMap() {
		return this.redisMap;
	}

}

package org.redisch7.gossipserver.datahandler;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnectionManager {
	static JedisPoolConfig poolConfig = new JedisPoolConfig();
	static JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);

	public static Jedis get() {
		return jedisPool.getResource();
	}

	public static void set(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}

	public static void close() {
		jedisPool.destroy();
	}
}

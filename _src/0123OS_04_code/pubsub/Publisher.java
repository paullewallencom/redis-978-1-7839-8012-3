package org.learningredis.chapter.four.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Publisher {
	private JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
	Jedis jedis = null;
	
	public Jedis getResource() {
		jedis = pool.getResource();
		return jedis;
	}
	
	public void setResource(Jedis jedis){
		pool.returnResource(jedis);
	}
	
	private void publisher() {
		Jedis jedis = this.getResource();
		jedis.publish("news", "Houstan calling texas... message publlished !!");
		//jedis.publish("news.*", "Houstan calling texas... message publlished !!");
		
	}
	
	public static void main(String[] args) {
		Publisher test = new Publisher();
		test.publisher();
	}
}

package org.learningredis.chapter.three.datastruct;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MySetTest {

	private JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
	Jedis jedis = null;
	
	public Jedis getResource() {
		jedis = pool.getResource();
		return jedis;
	}
	
	public void setResource(Jedis jedis){
		pool.returnResource(jedis);
	}
	
	
	public static void main(String[] args) {
		MySetTest mySetTest = new MySetTest();
		mySetTest.test();
	}

	private void test() {
		Jedis jedis = this.getResource();
		jedis.sadd("follow:cricket", "vinoo.das@junk-mail.com","vinoo.das1@junk-mail.com","vinoo.das3@junk-mail.com");
		System.out.println(jedis.smembers("follow:cricket"));
		System.out.println(jedis.scard("follow:cricket"));
		jedis.sadd("follow:redis", "vinoo.das1@junk-mail.com","vinoo.das2@junk-mail.com");
		System.out.println(jedis.smembers("follow:redis"));
		System.out.println(jedis.scard("follow:redis"));
		
		// intersect the above sets to give name who is interested in cricket and redis
		System.out.println(jedis.sinter("Cricket:followers","follow:redis"));
		
		jedis.sinterstore("follow:redis+cricket","follow:cricket","follow:redis");
		System.out.println(jedis.smembers("follow:redis+cricket"));
		
		System.out.println(jedis.sismember("follow:redis+cricket", "vinoo.das@junk-mail.com"));
		System.out.println(jedis.sismember("follow:redis+cricket", "vinoo.das1@junk-mail.com"));
		
		jedis.smove("follow:cricket", "follow:redis", "vinoo.das3@junk-mail.com");
		System.out.println(jedis.smembers("follow:redis"));
		
		System.out.println(jedis.srandmember("follow:cricket"));
		
		System.out.println(jedis.spop("follow:cricket"));
		System.out.println(jedis.smembers("follow:cricket"));
		
		jedis.sadd("follow:cricket","wrong-data@junk-mail.com");
		System.out.println(jedis.smembers("follow:cricket"));
		jedis.srem("follow:cricket","wrong-data@junk-mail.com");
		System.out.println(jedis.smembers("follow:cricket"));
		
		
		System.out.println(jedis.sunion("follow:cricket","follow:redis"));
		jedis.sunionstore("follow:cricket-or-redis","follow:cricket","follow:redis");
		System.out.println(jedis.smembers("follow:cricket-or-redis"));
		
		System.out.println(jedis.sdiff("follow:cricket", "follow:redis"));
		
		
		
		
		
	}
}

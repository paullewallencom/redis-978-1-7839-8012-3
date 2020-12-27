package org.learningredis.chapter.three.datastruct;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.BinaryClient.LIST_POSITION;

public class MyListTest {
	private JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
	Jedis jedis = null;
	
	public Jedis getResource() {
		jedis = pool.getResource();
		return jedis;
	}
	
	public void setResource(Jedis jedis){
		pool.returnResource(jedis);
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		MyListTest myListTest  = new MyListTest();
		myListTest.test();
	}

	private void test() {
		Jedis jedis = this.getResource();
		
		System.out.println(jedis.del("mykey4list"));
		
		String commonkey="mykey4list";
		String commonkey1="mykey4list1";
		
		
		for(int index=0;index<3;index++){
			jedis.lpush(commonkey, "Message - " + index);
		}
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		
		for(int index=3;index<6;index++){
			jedis.rpush(commonkey, "Message - " + index);
		}
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		System.out.println(jedis.lindex(commonkey, 0));
		
		System.out.println(jedis.linsert(commonkey,LIST_POSITION.AFTER,"Message - 5", "Message - 7"));
		System.out.println(jedis.lrange(commonkey, 0, -1));
		System.out.println(jedis.linsert(commonkey,LIST_POSITION.BEFORE,"Message - 7", "Message - 6"));
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		System.out.println(jedis.llen(commonkey));
		
		System.out.println(jedis.lpop(commonkey));
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		System.out.println(jedis.lpush(commonkey,"Message - 2","Message -1.9"));
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		System.out.println(jedis.lpushx(commonkey,"Message - 1.8"));
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		System.out.println(jedis.lrem(commonkey,0,"Message - 1.8"));
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		System.out.println(jedis.lrem(commonkey,-1,"Message - 7"));
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		System.out.println(jedis.lset(commonkey,7,"Message - 7"));
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		System.out.println(jedis.ltrim(commonkey,2,-4));
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		
		jedis.rpoplpush(commonkey, commonkey1);
		
		System.out.println(jedis.lrange(commonkey, 0, -1));
		System.out.println(jedis.lrange(commonkey1, 0, -1));
		
		
	}
}

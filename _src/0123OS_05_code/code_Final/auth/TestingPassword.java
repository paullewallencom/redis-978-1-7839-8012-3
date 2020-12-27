package org.learningredis.chapter.four.auth;

import redis.clients.jedis.Jedis;

public class TestingPassword {
	
	public static void main(String[] args) {
		TestingPassword test = new TestingPassword();
		test.authentication();
	}

	
	private void authentication() {
		
		Jedis jedis = new Jedis("localhost");
		jedis.auth("Learning Redis");
		jedis.set("foo", "bar");
		System.out.println(jedis.get("foo"));
		
	}
}

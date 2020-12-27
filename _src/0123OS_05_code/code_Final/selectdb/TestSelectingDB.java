package org.learningredis.chapter.four.selectdb;

import redis.clients.jedis.Jedis;

public class TestSelectingDB {
	public static void main(String[] args) {
		TestSelectingDB test = new TestSelectingDB();
		test.commandSelect();
	}

	private void commandSelect() {
		Jedis jedis = new Jedis("localhost");
		jedis.select(1);
		jedis.set("msg", "Hello world");
		System.out.println(jedis.get("msg"));
		jedis.select(2);
		System.out.println(jedis.get("msg"));
		System.out.println(jedis.dbSize());
	}
}

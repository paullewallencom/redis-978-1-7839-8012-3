package org.learningredis.chapter.four.luascripting;

import java.util.Arrays;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestLuaScript {
	
	public String luaScript = Reader.read("D:\\path\\of\\file\\location\\LuaScript.txt");
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
		TestLuaScript test = new TestLuaScript();
		test.luaScript();
	}

	private void luaScript() {
		Jedis jedis = this.getResource();
		String result = (String) jedis.eval(luaScript,Arrays.asList("msg"),
				Arrays.asList("Learning Redis",
						"Now I am learning Lua for redis",
						"oops ..error..prepare for the test again"));
		System.out.println(result);
		this.setResource(jedis);
	}
}

package org.learningredis.chapter.four.echoandping;

import redis.clients.jedis.Jedis;

public class TestEchoAndPing {
	
	public static void main(String[] args) throws InterruptedException {
		TestEchoAndPing echoAndPing = new TestEchoAndPing();
		Thread thread = new Thread(new LoadGenerator());
		thread.start();
		while(true){
			Thread.currentThread().sleep(1000);
			echoAndPing.testPing();
			echoAndPing.testEcho();
		}
		
	}

	private void testPing() {
		long start = System.currentTimeMillis();
		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis.ping() + " in " + (System.currentTimeMillis()-start) + " milliseconds");
	}

	private void testEcho() {
		long start = System.currentTimeMillis();
		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis.echo("hi redis ") + " in " + (System.currentTimeMillis()-start) + " milliseconds");
	}
}

package org.learningredis.chapter.four.pipelineandtx;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class SingleCommand implements Runnable {

	Jedis jedis = ConnectionManager.get();
	@Override
	public void run() {
		Set<String> data= jedis.smembers("keys-1");
		System.out.println("The return value of nv1 is [ " + data.size() + " ]");
		ConnectionManager.set(jedis);
	}

}

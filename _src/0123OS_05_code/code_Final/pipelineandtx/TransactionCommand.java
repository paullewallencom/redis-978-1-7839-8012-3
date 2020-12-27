package org.learningredis.chapter.four.pipelineandtx;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;


public class TransactionCommand implements Runnable {
	Jedis jedis = ConnectionManager.get();
	@Override
	public void run() {
			long start = System.currentTimeMillis();
			Transaction transactionableCommands = jedis.multi();
			for(int nv=0;nv<300000;nv++){
				transactionableCommands.sadd("keys-1", "name"+nv);
			}
			transactionableCommands.exec();
			
			Set<String> data= jedis.smembers("keys-1");
			System.out.println("The return value nv1 after tx [ " + data.size() + " ]");
		System.out.println("The time taken for executing client(Thread-1) "+ (System.currentTimeMillis()-start));
		
		ConnectionManager.set(jedis);

	}
}

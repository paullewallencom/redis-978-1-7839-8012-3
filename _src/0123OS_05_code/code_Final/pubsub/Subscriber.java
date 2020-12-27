package org.learningredis.chapter.four.pubsub;

import redis.clients.jedis.JedisPubSub;

public class Subscriber extends  JedisPubSub{

	@Override
	public void onMessage(String arg0, String arg1) {
		System.out.println("on message : " + arg0 + " value = " + arg1);
	}

	@Override
	public void onPMessage(String arg0, String arg1, String arg2) {
		System.out.println("on pattern message : " + arg0 + " channel = " + arg1 + " message =" + arg2);
	}

	@Override
	public void onPSubscribe(String arg0, int arg1) {
		System.out.println("on pattern subscribe : " + arg0 + " value = " + arg1);
	}

	@Override
	public void onPUnsubscribe(String arg0, int arg1) {
		System.out.println("on pattern unsubscribe : " + arg0 + " value = " + arg1);
	}

	@Override
	public void onSubscribe(String arg0, int arg1) {
		System.out.println("on subscribe : " + arg0 + " value = " + arg1);
	}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {
		System.out.println("on un-subscribe : " + arg0 + " value = " + arg1);
	}
	
}

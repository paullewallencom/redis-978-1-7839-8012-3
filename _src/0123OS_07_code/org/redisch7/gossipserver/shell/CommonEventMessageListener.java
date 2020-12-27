package org.redisch7.gossipserver.shell;

import org.redisch7.gossipserver.datahandler.ConnectionManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class CommonEventMessageListener implements Runnable {
	private Subscriber subscriber = null;

	public boolean statusFlag = true;

	private Node node;
	private Jedis jedis = null;

	public CommonEventMessageListener(Node node) {
		this.node = node;
		jedis = ConnectionManager.get();
		this.subscriber = new Subscriber(node.getNodename());
	}

	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {
			jedis.psubscribe(subscriber, "EVENTS.*");
		}

	}

	public void unsubscribe() {
		subscriber.onPUnsubscribe("EVENTS.*", 0);
	}

	public class Subscriber extends JedisPubSub {

		public Subscriber(String nodename) {

		}

		@Override
		public void onMessage(String arg0, String arg1) {
			System.out.println("-arg0 onMessage " + arg0);
			System.out.println("-arg1 onMessage " + arg1);
		}

		@Override
		public void onPMessage(String arg0, String arg1, String arg2) {
			System.out.println("arg0 = " + arg0);
			System.out.println("arg1 = " + arg1);
			System.out.println("arg2 = " + arg2);
		}

		@Override
		public void onPSubscribe(String arg0, int arg1) {

		}

		@Override
		public void onPUnsubscribe(String arg0, int arg1) {
			// System.out.println("Pattern .. Unsubscribe..");
		}

		@Override
		public void onSubscribe(String arg0, int arg1) {
			System.out.println("-arg0 onSubscribe " + arg0);
			System.out.println("-arg1 onSubscribe " + arg1);
		}

		@Override
		public void onUnsubscribe(String arg0, int arg1) {
			// System.out.println("Unsubscribe..");
		}

	}

}

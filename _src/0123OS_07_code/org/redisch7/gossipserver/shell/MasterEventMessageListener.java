package org.redisch7.gossipserver.shell;

import org.redisch7.gossipserver.datahandler.ConnectionManager;
import org.redisch7.gossipserver.util.commandparser.MapListToken;
import org.redisch7.gossipserver.util.commandparser.Validator;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class MasterEventMessageListener implements Runnable {
	private Subscriber subscriber = null;
	private Node node;
	private Jedis jedis = ConnectionManager.get();
	private Validator validator = new Validator();

	public MasterEventMessageListener(Node node) {
		this.node = node;
		this.subscriber = new Subscriber(node);
		validator.configureTemplate().add(new MapListToken());
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			jedis.subscribe(subscriber, node.getNodename());
		}

	}

	public void unsubscribe() {
		subscriber.unsubscribe(node.getNodename());
	}

	public class Subscriber extends JedisPubSub {

		public Subscriber(Node node) {

		}

		@Override
		public void onMessage(String nodename, String readmessage) {
			System.out.println("msg: " + readmessage);
		}

		@Override
		public void onPMessage(String arg0, String arg1, String arg2) {
			System.out.println(arg1);
			System.out.println(arg2);
		}

		@Override
		public void onPSubscribe(String arg0, int arg1) {
		}

		@Override
		public void onPUnsubscribe(String arg0, int arg1) {
		}

		@Override
		public void onSubscribe(String arg0, int arg1) {
		}

		@Override
		public void onUnsubscribe(String arg0, int arg1) {
		}

	}
}
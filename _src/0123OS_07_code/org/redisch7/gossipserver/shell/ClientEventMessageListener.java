package org.redisch7.gossipserver.shell;

import org.redisch7.gossipserver.commands.CloneNodeCommand;
import org.redisch7.gossipserver.commands.KillNodeCommand;
import org.redisch7.gossipserver.commands.MessageCommand;
import org.redisch7.gossipserver.commands.SetCommand;
import org.redisch7.gossipserver.datahandler.ConnectionManager;
import org.redisch7.gossipserver.util.commandparser.MapListToken;
import org.redisch7.gossipserver.util.commandparser.Validator;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class ClientEventMessageListener implements Runnable {
	private Subscriber subscriber = null;
	private Node node;
	private Jedis jedis = ConnectionManager.get();
	private Validator validator = null;

	public ClientEventMessageListener(Node node) {
		this.node = node;
		this.subscriber = new Subscriber(node);
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

		public Subscriber(Node clientNode) {

		}

		@Override
		public void onMessage(String nodename, String readmessage) {

			validator = new Validator();
			validator.configureTemplate().add(new MapListToken());
			validator.setInput(readmessage);
			CheckResult checkResult = validator.validate();

			if (checkResult.getResult()) {
				MapListToken mapListToken = (MapListToken) validator
						.getToken(0);
				if (mapListToken.containsKey("command")) {
					String commandValue = mapListToken.getNValue("command");
					if (commandValue.equals("set")) {
						MapListToken newMapListToken = mapListToken
								.removeElement("command");
						SetCommand command = new SetCommand();
						command.setName(node.getNodename());
						CheckResult result = command.execute(new CommandTokens(
								"set "
										+ newMapListToken
												.getValueAsSantizedString()));

						System.out.println(result.getResult());
						System.out.println(result.getReason());
					} else if (commandValue.equals("kill")) {
						KillNodeCommand command = new KillNodeCommand();
						command.setName(node.getNodename());
						MapListToken newMapListToken = mapListToken
								.removeElement("command");
						CheckResult result = command.execute(new CommandTokens(
								"kill " + node.getNodename()));

						System.out.println(result.getResult());
						System.out.println(result.getReason());
					} else if (commandValue.equals("clone")) {
						CloneNodeCommand command = new CloneNodeCommand();
						command.setName(node.getNodename());
						MapListToken newMapListToken = mapListToken
								.removeElement("command");

						CheckResult result = command.execute(new CommandTokens(
								"clone "
										+ newMapListToken
												.getValueAsSantizedString()));

						System.out.println(result.getResult());
						System.out.println(result.getReason());
					} else {
						MessageCommand messageCommand = new MessageCommand();
						messageCommand.setName(nodename);
						CommandTokens commandTokens = new CommandTokens(
								"msg master where msg=illegal_command");
						messageCommand.execute(commandTokens);
					}
				} else {
					System.out
							.println(":->"
									+ checkResult
											.appendReason("The command sent from publisher does not conatain 'command' token"));
				}

			} else {
				System.out.println(":->" + checkResult.getReason());
			}
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

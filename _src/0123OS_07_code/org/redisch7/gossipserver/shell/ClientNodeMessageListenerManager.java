package org.redisch7.gossipserver.shell;

import org.redisch7.gossipserver.commands.AbstractCommand;
import org.redisch7.gossipserver.commands.ActivateCommand;
import org.redisch7.gossipserver.commands.KillNodeCommand;
import org.redisch7.gossipserver.commands.PassivateCommand;
import org.redisch7.gossipserver.commands.ReConnectCommand;
import org.redisch7.gossipserver.commands.ReactivateCommand;

public class ClientNodeMessageListenerManager implements
		NodeMessageListenerManager {

	private String nodename;
	private CommonEventMessageListener commonEventMessageSubscriber;
	private ClientEventMessageListener privateEventMessageSubscriber;

	private Thread commonEventThread;
	private Thread privateEventThread;

	public ClientNodeMessageListenerManager(ClientNode clientNode) {
		this.nodename = clientNode.getNodename();
		commonEventMessageSubscriber = new CommonEventMessageListener(
				clientNode);
		privateEventMessageSubscriber = new ClientEventMessageListener(
				clientNode);
	}

	@Override
	public void start() {
		// System.out.println(" start the client node manager .. ");
		commonEventThread = new Thread(commonEventMessageSubscriber);
		privateEventThread = new Thread(privateEventMessageSubscriber);
		commonEventThread.start();
		privateEventThread.start();
	}

	@Override
	public void stop() {
		// System.out.println(" stop the client node manager .. ");
		commonEventMessageSubscriber.unsubscribe();
		privateEventMessageSubscriber.unsubscribe();
		commonEventThread.interrupt();
		privateEventThread.interrupt();

	}

	@Override
	public void passCommand(AbstractCommand command) {
		if (command instanceof ActivateCommand
				|| command instanceof ReactivateCommand
				|| command instanceof ReConnectCommand) {
			this.start();
		} else if (command instanceof PassivateCommand
				|| command instanceof KillNodeCommand) {
			this.stop();
		}
	}
}

package org.redisch7.gossipserver.shell;

import org.redisch7.gossipserver.commands.AbstractCommand;
import org.redisch7.gossipserver.commands.StartMasterCommand;
import org.redisch7.gossipserver.commands.StopMasterCommand;

public class MasterNodeMessageListenerManager implements
		NodeMessageListenerManager {

	private MasterEventMessageListener masterEventMessageSubscriber;
	private Thread privateEventThread;
	private MasterNode masternode;

	public MasterNodeMessageListenerManager(MasterNode masterNode) {
		this.masternode = masterNode;
		masterEventMessageSubscriber = new MasterEventMessageListener(
				masternode);
	}

	@Override
	public void start() {
		System.out.println(" start the master node manager .. ");
		privateEventThread = new Thread(masterEventMessageSubscriber);
		privateEventThread.start();
	}

	@Override
	public void stop() {
		System.out.println(" stop the master node manager .. ");
		privateEventThread.interrupt();
		masterEventMessageSubscriber.unsubscribe();

	}

	@Override
	public void passCommand(AbstractCommand command) {
		if (command instanceof StartMasterCommand) {
			this.start();
		} else if (command instanceof StopMasterCommand) {
			this.stop();
		}
	}

}

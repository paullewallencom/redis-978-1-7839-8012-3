package org.redisch7.gossipserver.shell;

import org.redisch7.gossipserver.commands.AbstractCommand;

public interface NodeMessageListenerManager {
	public void start();

	public void stop();

	public void passCommand(AbstractCommand command);
}

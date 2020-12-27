package org.redisch7.gossipserver.shell;

import java.util.HashMap;
import java.util.Map;

import org.redisch7.gossipserver.commands.ActivateCommand;
import org.redisch7.gossipserver.commands.ArchiveCommand;
import org.redisch7.gossipserver.commands.CloneNodeCommand;
import org.redisch7.gossipserver.commands.DeleteCommand;
import org.redisch7.gossipserver.commands.GetCommand;
import org.redisch7.gossipserver.commands.KillNodeCommand;
import org.redisch7.gossipserver.commands.MessageCommand;
import org.redisch7.gossipserver.commands.NullCommand;
import org.redisch7.gossipserver.commands.PassivateCommand;
import org.redisch7.gossipserver.commands.ReConnectCommand;
import org.redisch7.gossipserver.commands.ReactivateCommand;
import org.redisch7.gossipserver.commands.RegisterCommand;
import org.redisch7.gossipserver.commands.SetCommand;
import org.redisch7.gossipserver.commands.StatusCommand;
import org.redisch7.gossipserver.commands.SynchCommand;

public class ClientNode extends Node {

	private ClientNodeMessageListenerManager clientNodeMessageManager = null;
	private JavaJVMShutdownHook jvmShutdownHook = new JavaJVMShutdownHook();

	public ClientNode(String nodename) {
		super(nodename);
		System.out.println("Interactive Client Shell For :- "
				+ super.getNodename());
		clientNodeMessageManager = new ClientNodeMessageListenerManager(this);
		jvmShutdownHook = new JavaJVMShutdownHook();
		jvmShutdownHook.setNode(nodename);
		Runtime.getRuntime().addShutdownHook(jvmShutdownHook);
	}

	@Override
	public Map<String, Class> loadCommands() {
		Map<String, Class> commandRepository = new HashMap<String, Class>();
		commandRepository.put("GET", GetCommand.class);
		commandRepository.put("SET", SetCommand.class);
		commandRepository.put("STATUS", StatusCommand.class);
		commandRepository.put("MSG", MessageCommand.class);
		commandRepository.put("ARCHIVE", ArchiveCommand.class);
		commandRepository.put("SYNC", SynchCommand.class);
		commandRepository.put("REGISTER", RegisterCommand.class);
		commandRepository.put("ACTIVATE", ActivateCommand.class);
		commandRepository.put("REACTIVATE", ReactivateCommand.class);
		commandRepository.put("PASSIVATE", PassivateCommand.class);
		commandRepository.put("RECONNECT", ReConnectCommand.class);
		commandRepository.put("KILL", KillNodeCommand.class);
		commandRepository.put("CLONE", CloneNodeCommand.class);
		commandRepository.put("DEL", DeleteCommand.class);
		commandRepository.put("", NullCommand.class);
		commandRepository.put(null, NullCommand.class);

		return commandRepository;

	}

	@Override
	public NodeMessageListenerManager getNodeMessageManager() {
		return clientNodeMessageManager;
	}
}

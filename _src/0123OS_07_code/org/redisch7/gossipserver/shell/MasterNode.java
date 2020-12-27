package org.redisch7.gossipserver.shell;

import java.util.HashMap;
import java.util.Map;

import org.redisch7.gossipserver.commands.CloneNodeCommand;
import org.redisch7.gossipserver.commands.GetNodeDataCommand;
import org.redisch7.gossipserver.commands.KillNodeCommand;
import org.redisch7.gossipserver.commands.MessageCommand;
import org.redisch7.gossipserver.commands.NullCommand;
import org.redisch7.gossipserver.commands.StartMasterCommand;
import org.redisch7.gossipserver.commands.StatusCommand;
import org.redisch7.gossipserver.commands.StopMasterCommand;

public class MasterNode extends Node {
	private MasterNodeMessageListenerManager masterNodeMessageManager;

	public MasterNode() {
		super("master");
		System.out.println("Interactive Admin Shell For :- "
				+ super.getNodename());
		masterNodeMessageManager = new MasterNodeMessageListenerManager(this);
	}

	@Override
	public Map<String, Class> loadCommands() {
		Map<String, Class> commandRepository = new HashMap<String, Class>();
		commandRepository.put("GET", GetNodeDataCommand.class);
		// commandRepository.put("SET", SetNodeDataCommand.class);
		commandRepository.put("STATUS", StatusCommand.class);
		commandRepository.put("MSG", MessageCommand.class);
		commandRepository.put("KILL", KillNodeCommand.class);
		commandRepository.put("CLONE", CloneNodeCommand.class);
		commandRepository.put("START", StartMasterCommand.class);
		commandRepository.put("STOP", StopMasterCommand.class);
		commandRepository.put("", NullCommand.class);
		commandRepository.put(null, NullCommand.class);

		return commandRepository;
	}

	@Override
	public MasterNodeMessageListenerManager getNodeMessageManager() {
		return masterNodeMessageManager;
	}

}

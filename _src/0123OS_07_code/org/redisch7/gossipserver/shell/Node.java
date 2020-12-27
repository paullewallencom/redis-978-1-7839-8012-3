package org.redisch7.gossipserver.shell;

import java.util.HashMap;
import java.util.Map;

import org.redisch7.gossipserver.commands.AbstractCommand;
import org.redisch7.gossipserver.commands.KillNodeCommand;
import org.redisch7.gossipserver.commands.NullCommand;

public abstract class Node {
	private String nodename;
	private Map<String, Class> commandRepository = new HashMap<String, Class>();

	public abstract Map<String, Class> loadCommands();

	public Node(String nodename) {
		this.nodename = nodename;
		commandRepository = this.loadCommands();
	}

	public String getNodename() {
		return nodename;
	}

	public CommandTokens tokenizeCommands(String readLine) {
		CommandTokens commandTokens = new CommandTokens(readLine);
		return commandTokens;
	}

	public AbstractCommand getCommand(String string) {
		NullCommand nullCommand = new NullCommand();
		nullCommand.setName(this.getNodename());

		AbstractCommand command = nullCommand;
		try {
			if (commandRepository.containsKey(string)) {
				command = (AbstractCommand) commandRepository.get(string)
						.newInstance();

				command.setName(nodename);
				return command;

			} else {
				return nullCommand;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			return nullCommand;
		}

	}

	public CheckResult process(String readLine) {
		CheckResult checkResult = new CheckResult();
		CommandTokens commandTokens = tokenizeCommands(readLine);
		AbstractCommand command = this.getCommand(commandTokens.get(0)
				.toUpperCase());
		if (command != null) {
			if (command instanceof KillNodeCommand
					&& !nodename.equals("master")) {
				return checkResult.setFalse("node cannot kill itelf");
			} else {
				checkResult = command.execute(commandTokens);
				if (checkResult.getResult()) {
					this.getNodeMessageManager().passCommand(command);
				}
				return checkResult;
			}
		} else {
			return checkResult.setFalse("Illegal command ");
		}
	}

	public abstract NodeMessageListenerManager getNodeMessageManager();
}

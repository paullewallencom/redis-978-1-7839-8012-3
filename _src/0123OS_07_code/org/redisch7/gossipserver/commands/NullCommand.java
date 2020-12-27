package org.redisch7.gossipserver.commands;

import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;

public class NullCommand extends AbstractCommand {

	public NullCommand() {

	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		return new CheckResult().setFalse("Illegal Command..").appendReason(
				" for node, " + this.getName());
	}

}

package org.redisch7.gossipserver.commands;

import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;

public abstract class AbstractCommand {

	private String nodename;

	public abstract CheckResult execute(CommandTokens commandTokens);

	public void setName(String nodename) {
		this.nodename = nodename;
	}

	public String getName() {
		return this.nodename;
	}
}

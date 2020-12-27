package org.redisch7.gossipserver.commandhandlers;

import java.util.List;

import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.util.commandparser.Token;

public abstract class AbstractCommandHandler {

	private String nodename;

	public AbstractCommandHandler(String nodename) {
		this.nodename = nodename;
	}

	public abstract CheckResult process(List<Token> tokenList);

	public String getNodename() {
		return nodename;
	}

}

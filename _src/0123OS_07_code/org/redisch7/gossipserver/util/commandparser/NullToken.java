package org.redisch7.gossipserver.util.commandparser;

import org.redisch7.gossipserver.shell.CheckResult;

public class NullToken extends Token {

	@Override
	public CheckResult validate(String string) {
		return new CheckResult().setTrue();
	}

	@Override
	public Token convertToToken(String input) {
		return new NullToken();
	}

	@Override
	public String getValue() {
		return null;
	}

}

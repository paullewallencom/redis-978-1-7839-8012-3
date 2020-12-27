package org.redisch7.gossipserver.util.commandparser;

import org.redisch7.gossipserver.shell.CheckResult;

public abstract class Token {

	public abstract CheckResult validate(String string);

	public abstract Token convertToToken(String input);

	public abstract String getValue();

}

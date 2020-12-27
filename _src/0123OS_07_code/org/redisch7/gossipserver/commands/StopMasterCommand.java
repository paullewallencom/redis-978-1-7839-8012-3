package org.redisch7.gossipserver.commands;

import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class StopMasterCommand extends AbstractCommand {

	private Validator validator = new Validator();

	public StopMasterCommand() {
		validator.configureTemplate().add((new StringToken("stop")));
	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		return checkResult.setTrue().appendReason("master stoped..");
	}
}

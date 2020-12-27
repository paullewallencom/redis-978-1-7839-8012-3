package org.redisch7.gossipserver.commands;

import java.util.List;

import org.redisch7.gossipserver.commandhandlers.CloneNodeCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.MapListToken;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class CloneNodeCommand extends AbstractCommand {

	private Validator validator = new Validator();

	public CloneNodeCommand() {
		validator.configureTemplate().add((new StringToken("clone")))
				.add(new MapListToken());

	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		checkResult = validator.validate();
		if (checkResult.getResult()) {
			List<Token> tokenList = validator.getAllTokens();
			checkResult = new CloneNodeCommandHandler(this.getName())
					.process(tokenList);
		}
		return checkResult;
	}

}

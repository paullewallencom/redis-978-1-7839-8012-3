package org.redisch7.gossipserver.commands;

import java.util.List;

import org.redisch7.gossipserver.commandhandlers.SetCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.MapListToken;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class SetCommand extends AbstractCommand {

	Validator validator = new Validator();

	public SetCommand() {
		validator.configureTemplate().add((new StringToken("set")))
				.add(new MapListToken());
	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		checkResult = validator.validate();
		if (checkResult.getResult()) {
			List<Token> tokenList = validator.getAllTokens();
			checkResult = new SetCommandHandler(this.getName())
					.process(tokenList);
		}
		return checkResult;
	}

}

package org.redisch7.gossipserver.commands;

import java.util.List;

import org.redisch7.gossipserver.commandhandlers.DeleteCommandHandler;
import org.redisch7.gossipserver.commandhandlers.GetCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.StringListToken;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class DeleteCommand extends AbstractCommand {
	Validator validator = new Validator();

	public DeleteCommand() {
		validator.configureTemplate().add((new StringToken("del")))
				.add(new StringListToken());
	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		checkResult = validator.validate();
		if (checkResult.getResult()) {
			List<Token> tokenList = validator.getAllTokens();
			checkResult = new DeleteCommandHandler(this.getName())
					.process(tokenList);
		}
		return checkResult;
	}
}
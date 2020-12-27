package org.redisch7.gossipserver.commands;

import java.util.List;
import org.redisch7.gossipserver.commandhandlers.GetNodeDataCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.StringListToken;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class GetNodeDataCommand extends AbstractCommand {

	private Validator validator = new Validator();

	public GetNodeDataCommand() {
		validator.configureTemplate().add((new StringToken("get")))
				.add(new StringListToken()).add(new StringToken("where"))
				.add(new StringToken("nodes")).add(new StringToken("are"))
				.add(new StringListToken());
	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {

		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		checkResult = validator.validate();
		if (checkResult.getResult()) {
			List<Token> tokenList = validator.getAllTokens();
			checkResult = new GetNodeDataCommandHandler(this.getName())
					.process(tokenList);
		}
		return checkResult;
	}

}

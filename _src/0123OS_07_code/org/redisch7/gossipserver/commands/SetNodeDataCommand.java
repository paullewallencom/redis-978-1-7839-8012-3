package org.redisch7.gossipserver.commands;

import java.util.List;
import org.redisch7.gossipserver.commandhandlers.SetNodeDataCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.MapListToken;
import org.redisch7.gossipserver.util.commandparser.StringListToken;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class SetNodeDataCommand extends AbstractCommand {

	Validator validator = new Validator();

	public SetNodeDataCommand() {

		validator.configureTemplate().add((new StringToken("set")))
				.add(new MapListToken()).add(new StringToken("where"))
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
			checkResult = new SetNodeDataCommandHandler(this.getName())
					.process(tokenList);
		}
		return checkResult;
	}

}

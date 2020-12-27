package org.redisch7.gossipserver.commands;

import java.util.List;
import org.redisch7.gossipserver.commandhandlers.MessageCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.MapListToken;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class MessageCommand extends AbstractCommand {
	Validator validator = new Validator();

	public MessageCommand() {
		validator.configureTemplate().add((new StringToken("msg")))
				.add(new StringToken()).add(new StringToken("where"))
				.add(new MapListToken());
	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		checkResult = validator.validate();
		if (checkResult.getResult()) {
			List<Token> tokenList = validator.getAllTokens();
			checkResult = new MessageCommandHandler(this.getName())
					.process(tokenList);
		} else {
			System.out.println(this.getName()+" is send message in wrong format..");
		}
		return checkResult;
	}
}

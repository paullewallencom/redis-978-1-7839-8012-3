package org.redisch7.gossipserver.commands;

import java.util.List;

import org.redisch7.gossipserver.commandhandlers.ArchiveCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class ArchiveCommand extends AbstractCommand {

	private Validator validator = new Validator();

	public ArchiveCommand() {
		validator.configureTemplate().add((new StringToken("archive")));
	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		checkResult = validator.validate();
		if (checkResult.getResult()) {
			List<Token> tokenList = validator.getAllTokens();
			checkResult = new ArchiveCommandHandler(this.getName())
					.process(tokenList);
		}
		return checkResult;
	}

}

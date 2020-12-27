package org.redisch7.gossipserver.commands;

import java.io.File;
import java.util.List;

import org.redisch7.gossipserver.commandhandlers.RegisterCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class RegisterCommand extends AbstractCommand {
	private Validator validator = new Validator();

	public RegisterCommand() {

		validator.configureTemplate().add((new StringToken("register")));
	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		checkResult = validator.validate();
		if (checkResult.getResult()) {
			List<Token> tokenList = validator.getAllTokens();
			checkResult = new RegisterCommandHandler(this.getName())
					.process(tokenList);
		}
		if (checkResult.getResult()) {
			String path = System.getProperty("user.home") + "\\archive\\";
			File file = new File(path);
			if (!file.exists()) {
				if (file.mkdir()) {
					checkResult.appendReason("Archive folder created!");
				} else {
					checkResult.appendReason("Archive folder exists!");
				}
			}
		}
		return checkResult;
	}

}

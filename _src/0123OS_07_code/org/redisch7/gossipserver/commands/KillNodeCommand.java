package org.redisch7.gossipserver.commands;

import java.io.File;
import java.util.List;

import org.redisch7.gossipserver.commandhandlers.KillNodeCommandHandler;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;
import org.redisch7.gossipserver.util.commandparser.StringToken;
import org.redisch7.gossipserver.util.commandparser.Token;
import org.redisch7.gossipserver.util.commandparser.Validator;

public class KillNodeCommand extends AbstractCommand {

	private Validator validator = new Validator();

	public KillNodeCommand() {
		validator.configureTemplate().add((new StringToken("kill")))
				.add(new StringToken());

	}

	@Override
	public CheckResult execute(CommandTokens commandTokens) {
		CheckResult checkResult = new CheckResult();
		validator.setInput(commandTokens);
		checkResult = validator.validate();
		if (checkResult.getResult()) {
			List<Token> tokenList = validator.getAllTokens();
			checkResult = new KillNodeCommandHandler(this.getName())
					.process(tokenList);

			if (checkResult.getResult()) {
				String path = System.getProperty("user.home") + "\\archive\\"
						+ this.getName() + ".json";
				File file = new File(path);
				if (file.exists()) {
					if (file.delete()) {
						System.exit(0);
					} else {
						checkResult.appendReason("Archive file for "
								+ this.getName()
								+ ".json could not get deleted!");
					}
				}
			}
		}
		return checkResult;
	}

}

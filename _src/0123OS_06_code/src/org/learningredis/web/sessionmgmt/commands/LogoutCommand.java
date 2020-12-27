package org.learningredis.web.sessionmgmt.commands;

import org.learningredis.web.Commands;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.UserDBManager;

public class LogoutCommand extends Commands {

	public LogoutCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");

		String sessionid = this.getArgument().getValue("sessionid");
		if (UserDBManager.singleton.expireSession(sessionid)) {
			return "logout was clean";
		} else {
			return "logout was not clean";
		}

	}

}

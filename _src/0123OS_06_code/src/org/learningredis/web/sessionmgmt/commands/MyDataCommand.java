package org.learningredis.web.sessionmgmt.commands;

import java.util.Map;

import org.learningredis.web.Commands;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.UserDBManager;

public class MyDataCommand extends Commands {

	public MyDataCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");
		String sessionid = this.getArgument().getValue("sessionid");

		String name = UserDBManager.singleton.getUserName(sessionid);
		Map<String, String> map = UserDBManager.singleton.getRegistrationMap(name);
		return map.toString();
	}

}

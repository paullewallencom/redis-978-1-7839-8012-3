package org.learningredis.web.sessionmgmt.commands;

import org.learningredis.web.Commands;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.UserDBManager;

public class RegistrationCommand extends Commands {

	public RegistrationCommand(Argument argument) {
		super(argument);
	}

	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");
		String name = this.getArgument().getValue("name");

		if (!UserDBManager.singleton.doesUserExist(name)) {
			UserDBManager.singleton.createUser(this.getArgument().getAttributes());

		} else {
			return "user already registered in ";
		}

		return "successful registeration  -> " + name;
	}

}

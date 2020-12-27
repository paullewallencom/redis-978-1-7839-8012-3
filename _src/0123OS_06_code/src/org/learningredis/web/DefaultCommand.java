package org.learningredis.web;

import org.learningredis.web.util.Argument;

public class DefaultCommand extends Commands {

	public DefaultCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		return "Command Not Recognized !!";
	}

}

package org.learningredis.web.productmgmt.commands;

import org.learningredis.web.Commands;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.ProductDBManager;

public class DisplayCommand extends Commands {

	public DisplayCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String display = ProductDBManager.singleton.getProductInfo(this.getArgument().getValue("name"));
		return display;
	}

}

package org.learningredis.web.productmgmt.commands;

import org.learningredis.web.Commands;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.ProductDBManager;

public class DisplayTagCommand extends Commands {

	public DisplayTagCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");

		String tagName = this.getArgument().getValue("tagname");
		String details = ProductDBManager.singleton.getTagValues(tagName);
		return details;
	}

}

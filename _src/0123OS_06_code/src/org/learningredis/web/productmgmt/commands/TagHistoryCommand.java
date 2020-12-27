package org.learningredis.web.productmgmt.commands;

import org.learningredis.web.Commands;
import org.learningredis.web.util.AnalyticsDBManager;
import org.learningredis.web.util.Argument;

public class TagHistoryCommand extends Commands {

	public TagHistoryCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String tagname = this.getArgument().getValue("tagname");
		String tagHistory = AnalyticsDBManager.singleton.getTagHistory(tagname);
		return tagHistory;
	}

}

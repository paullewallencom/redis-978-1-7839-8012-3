package org.learningredis.web.analytics.commands;

import org.learningredis.web.Commands;
import org.learningredis.web.util.AnalyticsDBManager;
import org.learningredis.web.util.Argument;

public class VisitTodayCommand extends Commands {

	public VisitTodayCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + "Entering the execute function");

		String productName = this.getArgument().getValue("productname");
		Integer visitCount = AnalyticsDBManager.singleton.getVisitToday(productName);

		System.out.println(this.getClass().getSimpleName() + ":  " + "Printing the result for execute function");
		System.out.println("Result = " + "Total Unique Visitors are: " + visitCount.toString());

		return "Total Unique Visitors are: " + visitCount.toString();
	}

}

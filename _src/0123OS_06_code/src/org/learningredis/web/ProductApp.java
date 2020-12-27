package org.learningredis.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.learningredis.web.analytics.commands.PurchasesTodayCommand;
import org.learningredis.web.analytics.commands.VisitTodayCommand;
import org.learningredis.web.productmgmt.commands.CommissionProductCommand;
import org.learningredis.web.productmgmt.commands.DisplayCommand;
import org.learningredis.web.productmgmt.commands.DisplayTagCommand;
import org.learningredis.web.productmgmt.commands.TagHistoryCommand;
import org.learningredis.web.productmgmt.commands.UpdateTagCommand;
import org.learningredis.web.util.Argument;

/**
 * Servlet implementation class LoginServlet
 */

public class ProductApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductApp() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		Argument argument = new Argument(request.getParameter("args"));
		PrintWriter out = response.getWriter();

		switch (command.toLowerCase()) {
		case "commission":
			Commands commission = new CommissionProductCommand(argument);
			out.println(commission.execute());
			break;
		case "display":
			Commands display = new DisplayCommand(argument);
			out.println(display.execute());
			break;
		case "displaytag":
			Commands displaytag = new DisplayTagCommand(argument);
			out.println(displaytag.execute());
			break;
		case "updatetag":
			Commands updatetag = new UpdateTagCommand(argument);
			out.println(updatetag.execute());
			break;

		case "visitstoday":
			Commands visittoday = new VisitTodayCommand(argument);
			out.println(visittoday.execute());
			break;

		case "purchasestoday":
			Commands purchasestoday = new PurchasesTodayCommand(argument);
			out.println(purchasestoday.execute());
			break;

		case "taghistory":
			Commands taghistory = new TagHistoryCommand(argument);
			out.println(taghistory.execute());
			break;
		default:
			Commands defaultUC = new DefaultCommand(argument);
			out.println(defaultUC.execute());
			break;
		}
	}

}

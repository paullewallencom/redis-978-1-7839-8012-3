package org.learningredis.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.learningredis.web.analytics.commands.MyStatusCommand;
import org.learningredis.web.analytics.commands.RecomendByProductCommand;
import org.learningredis.web.sessionmgmt.commands.Add2CartCommand;
import org.learningredis.web.sessionmgmt.commands.BrowseCommand;
import org.learningredis.web.sessionmgmt.commands.BuyCommand;
import org.learningredis.web.sessionmgmt.commands.EditCartCommand;
import org.learningredis.web.sessionmgmt.commands.EditMyDataCommand;
import org.learningredis.web.sessionmgmt.commands.LoginCommand;
import org.learningredis.web.sessionmgmt.commands.LogoutCommand;
import org.learningredis.web.sessionmgmt.commands.MyDataCommand;
import org.learningredis.web.sessionmgmt.commands.MyPurchaseHistoryCommand;
import org.learningredis.web.sessionmgmt.commands.RegistrationCommand;
import org.learningredis.web.sessionmgmt.commands.ReloginCommand;
import org.learningredis.web.sessionmgmt.commands.ShowMyCartCommand;
import org.learningredis.web.util.Argument;

public class UserApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserApp() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		Argument argument = new Argument(request.getParameter("args"));
		PrintWriter out = response.getWriter();
		switch (command.toLowerCase()) {
		case "register":
			Commands register = new RegistrationCommand(argument);
			out.println(register.execute());
			break;

		case "login":
			Commands login = new LoginCommand(argument);
			out.println(login.execute());
			break;

		case "mydata":
			Commands mydata = new MyDataCommand(argument);
			out.println(mydata.execute());
			break;
		case "editmydata":
			Commands editMyData = new EditMyDataCommand(argument);
			out.println(editMyData.execute());
			break;

		case "recommendbyproduct":
			Commands recommendbyproduct = new RecomendByProductCommand(argument);
			String recommendbyproducts = recommendbyproduct.execute();
			out.println(recommendbyproducts);
			break;

		case "browse":
			Commands browse = new BrowseCommand(argument);
			String result = browse.execute();
			out.println(result);
			String productname = argument.getValue("browse");
			String sessionid = argument.getValue("sessionid");
			request.getRequestDispatcher(
					"/productApp?command=updatetag&args=sessionid=" + sessionid + ":productname=" + productname
							+ ":action=browse").include(request, response);

			break;

		case "buy":
			Commands buy = new BuyCommand(argument);
			String[] details = buy.execute().split("#");
			out.println(details[0]);
			String sessionID = argument.getValue("sessionid");
			request.getRequestDispatcher(
					"/productApp?command=updatetag&args=sessionid=" + sessionID + ":action=buy:details=" + details[1])
					.include(request, response);
			break;

		case "stats":
			Commands stats = new MyStatusCommand(argument);
			out.println(stats.execute());
			break;

		case "add2cart":
			Commands add2cart = new Add2CartCommand(argument);
			out.println(add2cart.execute());
			break;

		case "showmycart":
			Commands showmycart = new ShowMyCartCommand(argument);
			out.println(showmycart.execute());
			break;

		case "editcart":
			Commands editCard = new EditCartCommand(argument);
			out.println(editCard.execute());
			break;

		case "relogin":
			Commands relogin = new ReloginCommand(argument);
			out.println(relogin.execute());
			break;

		case "logout":
			Commands logout = new LogoutCommand(argument);
			out.println(logout.execute());
			break;

		case "mypurchasehistory":
			Commands mypurchasehistory = new MyPurchaseHistoryCommand(argument);
			out.println(mypurchasehistory.execute());
			break;

		default:
			Commands defaultUC = new DefaultCommand(argument);
			out.println(defaultUC.execute());
			break;
		}

	}

}

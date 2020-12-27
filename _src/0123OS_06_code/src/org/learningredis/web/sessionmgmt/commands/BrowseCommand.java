package org.learningredis.web.sessionmgmt.commands;

import org.learningredis.web.Commands;
import org.learningredis.web.util.AnalyticsDBManager;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.ProductDBManager;

public class BrowseCommand extends Commands {

	public BrowseCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");

		String productname = this.getArgument().getValue("browse");
		if (ProductDBManager.singleton.keyExist(productname)) {
			AnalyticsDBManager.singleton.updateBrowsingHistory(this.getArgument().getValue("sessionid"), productname);
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("You are browsing the following product = " + productname + "\n");
			stringBuffer.append(ProductDBManager.singleton.getProductInfo(productname));

			return stringBuffer.toString();
		} else {
			return "Error: The product you are trying to browse does not exist i.e. " + productname;
		}

	}

}

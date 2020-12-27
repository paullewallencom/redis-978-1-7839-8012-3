package org.learningredis.web.sessionmgmt.commands;

import org.learningredis.web.Commands;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.ShoppingCartDBManager;

public class BuyCommand extends Commands {

	public BuyCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");
		String sessionid = this.getArgument().getValue("sessionid");
		String shoppingdetails = ShoppingCartDBManager.singleton.buyItemsInTheShoppingCart(sessionid);
		return shoppingdetails;
	}

}

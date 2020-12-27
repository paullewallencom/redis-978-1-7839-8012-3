package org.learningredis.web.sessionmgmt.commands;

import java.util.HashMap;
import java.util.Map;

import org.learningredis.web.Commands;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.ShoppingCartDBManager;
import org.learningredis.web.util.UserDBManager;

public class Add2CartCommand extends Commands {

	public Add2CartCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");

		String result = "did not update the shopping cart";
		String sessionid = this.getArgument().getValue("sessionid");
		String product = this.getArgument().getValue("product");
		String[] productList = product.split(",");
		Map<String, String> productQtyMap = new HashMap<String, String>();

		for (String _product : productList) {

			String[] nameQty = _product.split("@");
			productQtyMap.put(nameQty[0], nameQty[1]);
		}

		if (UserDBManager.singleton.doesSessionExist(sessionid)) {
			result = ShoppingCartDBManager.singleton.addToShoppingCart(sessionid, productQtyMap);
		}
		return "Result : " + result;
	}

}

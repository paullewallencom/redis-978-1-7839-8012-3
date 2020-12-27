package org.learningredis.web.productmgmt.commands;

import java.util.Map;

import org.learningredis.web.Commands;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.ProductDBManager;

public class CommissionProductCommand extends Commands {

	public CommissionProductCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");

		Map<String, String> productAttributes = this.getArgument().getAttributes();
		boolean commisioning_result = ProductDBManager.singleton.commisionProduct(productAttributes);
		boolean tagging_result = ProductDBManager.singleton.enterTagEntries(productAttributes.get("name"),
				productAttributes.get("tags"));

		if (commisioning_result & tagging_result) {
			return "commisioning successful";
		} else {
			return "commisioning not successful";
		}
	}

}

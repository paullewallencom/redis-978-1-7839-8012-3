package org.learningredis.web.analytics.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.learningredis.web.Commands;
import org.learningredis.web.util.AnalyticsDBManager;
import org.learningredis.web.util.Argument;
import org.learningredis.web.util.ProductDBManager;

public class RecomendByProductCommand extends Commands {
	int totalrecomendations = 10;

	public RecomendByProductCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		System.out.println(this.getClass().getSimpleName() + ":  " + " Entering the execute function");

		StringBuffer buffer = new StringBuffer();

		String productname = this.getArgument().getValue("productname");
		buffer.append("If you are lookinging into " + productname + " you might also find the following \n");
		buffer.append("products interseting... \n");

		Map<String, Integer> tags = ProductDBManager.singleton.getProductTags(productname);
		// Lets get total sum of weights
		int totalweight = 0;
		Set<String> keys = tags.keySet();
		for (String key : keys) {
			totalweight = totalweight + tags.get(key);
		}

		for (String key : keys) {
			int slotfortag = Math.round(totalrecomendations * tags.get(key) / totalweight);
			List<String> productnames = AnalyticsDBManager.singleton.getTopProducts(slotfortag, key);
			for (String product : productnames) {
				if (!product.equals(productname)) {
					buffer.append("For tag = " + key + " the recomended product is " + product);
					buffer.append("\n");
				}
			}
		}

		System.out.println(this.getClass().getSimpleName() + ":  " + "Printing the result for execute function");
		System.out.println("Result = " + buffer.toString());

		return buffer.toString();
	}

}

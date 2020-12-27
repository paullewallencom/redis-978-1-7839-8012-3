package org.learningredis.web.util;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class ShoppingCartDBManager extends RedisDBManager {

	private ShoppingCartDBManager() {

	}

	public static ShoppingCartDBManager singleton = new ShoppingCartDBManager();

	public String addToShoppingCart(String sessionid, Map<String, String> productQtyMap) {
		Jedis jedis = this.getConnection();
		String result = jedis.hmset(sessionid + "@shoppingcart", productQtyMap);
		this.returnConnection(jedis);
		return result;
	}

	public Map<String, String> myCartInfo(String sessionid) {
		Jedis jedis = this.getConnection();
		Map<String, String> shoppingcart = jedis.hgetAll(sessionid + "@shoppingcart");
		this.returnConnection(jedis);
		return shoppingcart;
	}

	public String editMyCart(String sessionID, Map<String, String> productQtyMap) {
		Jedis jedis = this.getConnection();
		String result = "";
		if (jedis.exists(sessionID + "@shoppingcart")) {
			Set<String> keySet = productQtyMap.keySet();

			for (String key : keySet) {
				if (jedis.hexists(sessionID + "@shoppingcart", key)) {
					Integer intValue = new Integer(productQtyMap.get(key)).intValue();
					if (intValue == 0) {
						jedis.hdel(sessionID + "@shoppingcart", key);
					} else if (intValue > 0) {
						jedis.hset(sessionID + "@shoppingcart", key, productQtyMap.get(key));
					}
				}
			}
			result = "Updated the shopping cart for user";
		} else {
			result = "Could not update the shopping cart for the user !! ";
		}
		this.returnConnection(jedis);
		return result;
	}

	public String buyItemsInTheShoppingCart(String sessionid) {
		Jedis jedis = this.getConnection();
		Map<String, String> cartInfo = jedis.hgetAll(sessionid + "@shoppingcart");
		Set<String> procductNameList = cartInfo.keySet();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("RECEIPT: You have purchased the following \n");
		stringBuffer.append("----------------------------------------------------------" + "\n");
		int i = 1;
		for (String productname : procductNameList) {
			String unitCost = jedis.hget(productname, "cost");
			int unitCostValue = new Integer(unitCost).intValue();
			String quantity = cartInfo.get(productname);
			int quantityValue = new Integer(quantity).intValue();
			stringBuffer.append("[" + i + "] Name of item : " + productname + " and quantity was : " + quantity
					+ " the total cost is = " + quantityValue * unitCostValue + "\n");
			i++;
		}
		stringBuffer.append("----------------------------------------------------------");
		stringBuffer.append("#");

		for (String productname : procductNameList) {
			stringBuffer.append(productname);
			stringBuffer.append(",");
		}

		// Update the user purchase history:
		String name = jedis.hget(sessionid + "@sessiondata", "name");
		for (String productname : procductNameList) {
			jedis.lpush(name + "@purchasehistory", productname + " on " + getDate());
		}
		this.returnConnection(jedis);
		return stringBuffer.toString();
	}
}

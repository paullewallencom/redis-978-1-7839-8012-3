package org.learningredis.web.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class ProductDBManager extends RedisDBManager {

	private ProductDBManager() {

	}

	public static ProductDBManager singleton = new ProductDBManager();

	public boolean commisionProduct(Map<String, String> productAttributes) {
		Jedis jedis = this.getConnection();
		String productCreationResult = jedis.hmset(productAttributes.get("name"), productAttributes);
		if (productCreationResult.toLowerCase().equals("ok")) {
			this.returnConnection(jedis);
			return true;
		} else {
			this.returnConnection(jedis);
			return false;
		}
	}

	public boolean enterTagEntries(String name, String string) {
		Jedis jedis = this.getConnection();
		String[] tags = string.split(",");
		boolean boolResult = false;

		List<String> tagList = new ArrayList<String>();
		for (String tag : tags) {
			String[] tagAndRating = tag.split("@");
			tagList.add(tagAndRating[0]);
		}

		for (String tag : tagList) {
			long result = jedis.zadd(tag.toLowerCase(), 0, name);
			if (result == 0) {
				break;
			} else {
				boolResult = true;
			}
		}
		this.returnConnection(jedis);
		return boolResult;
	}

	public String getProductInfo(String name) {
		Jedis jedis = this.getConnection();
		Map<String, String> map = jedis.hgetAll(name);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Following are the product attributes for  " + name);
		stringBuffer.append("\n");
		Set<String> keys = map.keySet();
		int i = 1;
		for (String key : keys) {
			stringBuffer.append("[" + i + "] . " + key + " value : " + map.get(key));
			stringBuffer.append("\n");
			i++;
		}
		this.returnConnection(jedis);
		return stringBuffer.toString();
	}

	public String getTagValues(String tagName) {
		Jedis jedis = this.getConnection();
		StringBuffer stringBuffer = new StringBuffer();
		Set<String> sortedTagList = jedis.zrange(tagName.toLowerCase(), 0, 10000);
		stringBuffer.append("The following products are listed as per the hit rate \n");
		int i = 1;
		for (String tagname : sortedTagList) {
			stringBuffer.append(" [" + i + "] " + tagname + "\n");
			i++;
		}
		this.returnConnection(jedis);
		return stringBuffer.toString();
	}

	public boolean keyExist(String keyName) {
		Jedis jedis = this.getConnection();
		boolean result = jedis.exists(keyName);
		this.returnConnection(jedis);
		return result;
	}

	public int getPurchaseToday(String productName) {
		Jedis jedis = this.getConnection();
		if (jedis.get(productName + "@purchase:" + getDate()) != null) {
			BitSet users = BitSet.valueOf(jedis.get(productName + "@purchase:" + getDate()).getBytes());
			this.returnConnection(jedis);
			return users.cardinality();
		} else {
			this.returnConnection(jedis);
			return 0;
		}
	}

	public Map<String, Integer> getProductTags(String productname) {
		Jedis jedis = this.getConnection();
		String producttags = jedis.hget(productname, "tags");
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] tagAndweights = producttags.split(",");
		for (String tagAndWeight : tagAndweights) {
			map.put(tagAndWeight.split("@")[0], new Integer(tagAndWeight.split("@")[1]));
		}
		this.returnConnection(jedis);
		return map;
	}

}

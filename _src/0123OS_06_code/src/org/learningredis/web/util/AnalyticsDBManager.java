package org.learningredis.web.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class AnalyticsDBManager extends RedisDBManager {

	private AnalyticsDBManager() {

	}

	public static AnalyticsDBManager singleton = new AnalyticsDBManager();

	public void registerInSessionTracker(String sessionID) {
		Jedis jedis = this.getConnection();
		Long sessionvalue = new Long(sessionID);
		jedis.setbit("sessionIdTracker", sessionvalue, true);
		this.returnConnection(jedis);
	}

	public void updateBrowsingHistory(String sessionID, String productname) {
		Jedis jedis = this.getConnection();
		jedis.zincrby(sessionID + "@browsinghistory", 1.0, productname);
		this.returnConnection(jedis);
	}

	public Set<String> getBrowsingHistory(String sessionID) {
		Jedis jedis = this.getConnection();
		Set<String> range = jedis.zrange(sessionID + "@browsinghistory", 0, 1000000);
		this.returnConnection(jedis);
		return range;
	}

	public int getVisitToday(String productName) {
		Jedis jedis = this.getConnection();
		if (jedis.get(productName + "@visit:" + getDate()) != null) {
			BitSet users = BitSet.valueOf(jedis.get(productName + "@visit:" + getDate()).getBytes());
			this.returnConnection(jedis);
			return users.cardinality();
		} else {
			this.returnConnection(jedis);
			return 0;
		}

	}

	public void updateProductVisit(String sessionid, String productName) {
		Jedis jedis = this.getConnection();
		jedis.setbit(productName + "@visit:" + getDate(), new Long(sessionid), true);
		this.returnConnection(jedis);
	}

	public void updateProductPurchase(String sessionid, String productName) {
		Jedis jedis = this.getConnection();
		jedis.setbit(productName + "@purchase:" + getDate(), new Long(sessionid), true);
		this.returnConnection(jedis);
	}

	public void updateRatingInTag(String productname, double rating) {
		Jedis jedis = this.getConnection();
		String string = jedis.hget(productname, "tags");
		String[] tags = string.split(",");
		List<String> tagList = new ArrayList<String>();
		for (String tag : tags) {
			String[] tagAndRating = tag.split("@");
			tagList.add(tagAndRating[0]);
		}

		for (String tag : tagList) {
			jedis.zincrby(tag.toLowerCase(), rating, productname);
		}
		this.returnConnection(jedis);
	}

	public List<String> getMyPurchaseHistory(String sessionid) {
		Jedis jedis = this.getConnection();
		String name = jedis.hget(sessionid + "@sessiondata", "name");
		List<String> purchaseHistory = jedis.lrange(name + "@purchasehistory", 0, 100);
		this.returnConnection(jedis);
		return purchaseHistory;
	}

	public String getTagHistory(String tagname) {
		Jedis jedis = this.getConnection();
		Set<String> sortedProductList = jedis.zrange(tagname.toLowerCase(), 0, 10000);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("The following products are listed as per the hit rate \n");
		int i = 1;
		for (String productname : sortedProductList) {
			stringBuffer.append(" [" + i + "] " + productname + " and the score is "
					+ jedis.zscore(tagname.toLowerCase(), productname) + "\n");
			i++;
		}
		this.returnConnection(jedis);
		return stringBuffer.toString();
	}

	public List<String> getTopProducts(int slotfortag, String tag) {
		Jedis jedis = this.getConnection();
		Set<String> sortedProductList = jedis.zrevrange(tag.toLowerCase(), 0, 100000000);
		List<String> topproducts = new ArrayList<String>();
		Iterator<String> iterator = sortedProductList.iterator();
		int index = 0;
		while (iterator.hasNext()) {
			if (index <= slotfortag) {
				topproducts.add(iterator.next());
				index++;
			} else {
				break;
			}
		}
		this.returnConnection(jedis);
		return topproducts;
	}
}

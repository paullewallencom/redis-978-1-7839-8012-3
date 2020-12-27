package org.learningredis.web.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class UserDBManager extends RedisDBManager {

	private UserDBManager() {

	}

	public static UserDBManager singleton = new UserDBManager();

	public String getUserName(String sessionID) {
		Jedis jedis = this.getConnection();
		String name = jedis.hget(sessionID + "@sessiondata", "name");
		this.returnConnection(jedis);
		return name;
	}

	public void createUser(Map<String, String> attriuteMap) {
		Jedis jedis = this.getConnection();
		Map<String, String> map = attriuteMap;
		map.put("creation-time", new Date().toString());
		map.put("sessionID", "null");
		jedis.hmset(attriuteMap.get("name") + "@userdata", map);
		this.returnConnection(jedis);
	}

	public Map<String, String> getRegistrationMap(String name) {
		Jedis jedis = this.getConnection();
		Map<String, String> attributeMap = new HashMap<String, String>();
		attributeMap = jedis.hgetAll(name + "@userdata");
		this.returnConnection(jedis);
		return attributeMap;
	}

	public boolean doesUserExist(String name) {
		Jedis jedis = this.getConnection();
		String value = jedis.hget(name + "@userdata", "name");
		this.returnConnection(jedis);
		if (value == null) {
			return false;
		} else if (value != null & value.equals(name)) {
			return true;
		} else {
			return false;
		}

	}

	public void setRegistrationMap(String name, Map<String, String> attributeMap) {
		Jedis jedis = this.getConnection();
		jedis.hmset(name + "@userdata", attributeMap);
		this.returnConnection(jedis);
	}

	public String getUserPassword(String name) {
		Jedis jedis = this.getConnection();
		String password = jedis.hget(name + "@userdata", "password");
		this.returnConnection(jedis);
		return password;
	}

	public void login(String sessionID, String name) {
		Jedis jedis = this.getConnection();
		Map<String, String> loginMap = new HashMap<String, String>();
		loginMap.put("LastLogin", new Date().toString());
		loginMap.put("loginstatus", "LoggedIn");
		loginMap.put("sessionID", sessionID);
		loginMap.put("name", name);
		jedis.hmset(sessionID + "@sessiondata", loginMap);
		this.returnConnection(jedis);
	}

	public boolean editRegistrationMap(Map<String, String> editMap) {
		Jedis jedis = this.getConnection();
		if (jedis.hget(editMap.get("name") + "@userdata", "sessionID").equals(editMap.get("sessionid"))) {
			jedis.hmset(editMap.get("name") + "@userdata", editMap);
			this.returnConnection(jedis);
			return true;
		} else {
			this.returnConnection(jedis);
			return false;
		}

	}

	public String getUserSessionId(String name) {
		Jedis jedis = this.getConnection();
		String sessionID = jedis.hget(name + "@userdata", "sessionID");
		this.returnConnection(jedis);
		return sessionID;
	}

	public boolean expireSession(String sessionid) {
		// Get name from session data structure
		Jedis jedis = this.getConnection();
		String name = jedis.hget(sessionid + "@sessiondata", "name");
		// remove session id from userdata
		if (name != null) {
			Long sessionvalue = new Long(jedis.hget(name + "@userdata", "sessionID"));
			jedis.hset(name + "@userdata", "sessionID", "null");

			// remove session data : use TTL
			if (jedis.exists(sessionid + "@sessiondata")) {
				jedis.expire(sessionid + "@sessiondata", 1);
			}
			// remove browsing history : use TTL
			if (jedis.exists(sessionid + "@browsinghistory")) {
				jedis.expire(sessionid + "@browsinghistory", 1);
			}

			// remove shopping cart : use TTL
			if (jedis.exists(sessionid + "@shoppingcart")) {
				jedis.expire(sessionid + "@shoppingcart", 1);
			}
			// make the value at offset as '0'
			jedis.setbit("sessionIdTracker", sessionvalue, false);
			this.returnConnection(jedis);
			return true;

		} else {
			this.returnConnection(jedis);
			return false;
		}
	}

	public boolean doesSessionExist(String sessionid) {
		Jedis jedis = this.getConnection();
		if (jedis.hexists(sessionid + "@sessiondata", "name")) {
			this.returnConnection(jedis);
			return true;
		} else {
			this.returnConnection(jedis);
			return false;
		}

	}

}

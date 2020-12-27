package org.learningredis.web.util;

import java.util.HashMap;
import java.util.Map;

public class Argument {

	Map<String, String> argumentMap = new HashMap<String, String>();

	public Argument(String args) {

		String[] arguments = args.split(":");
		for (String argument : arguments) {
			String key = argument.split("=")[0];
			String value = argument.split("=")[1];
			argumentMap.put(key, value);
		}
	}

	public String getValue(String key) {
		return argumentMap.get(key);
	}

	public Map<String, String> getAttributes() {
		return argumentMap;
	}

}

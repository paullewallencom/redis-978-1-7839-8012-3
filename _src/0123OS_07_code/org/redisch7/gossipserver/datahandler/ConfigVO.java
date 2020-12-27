package org.redisch7.gossipserver.datahandler;

import java.util.HashMap;
import java.util.Map;

public class ConfigVO {
	public Map<String, String> store = new HashMap<String, String>();

	public ConfigVO(Map<String, String> config) {
		this.store = config;
	}

}

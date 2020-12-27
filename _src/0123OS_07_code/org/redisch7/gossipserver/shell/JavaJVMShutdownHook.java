package org.redisch7.gossipserver.shell;

import org.redisch7.gossipserver.datahandler.JedisUtil;

public class JavaJVMShutdownHook extends Thread {
	private String nodeName;

	public void setNode(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public void run() {
		JedisUtil jedisUtil = new JedisUtil();
		jedisUtil.addToShutdownNode(nodeName);
	}
}

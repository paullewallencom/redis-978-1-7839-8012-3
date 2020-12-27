package org.redisch7.gossipserver.shell;

import java.util.TimerTask;

public class NodeHealthChecker extends TimerTask {
	private String nodeName;

	public NodeHealthChecker(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public void run() {

	}
}

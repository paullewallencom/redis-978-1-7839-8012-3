package org.redisch7.gossipserver.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
	private Shell() {}

	private Node			node		= null;
	private static Shell	singleton	= new Shell();

	public static Shell instance() {
		return singleton;
	}

	// : as an shell API mode.
	public Shell asClient(String nodename) {
		if (node != null && nodename != null && nodename.trim().length() != 0) {
			node = new ClientNode(nodename);
			return this;
		} else {
			return null;
		}
	}

	public Shell asMaster() {
		if (node != null) {
			node = new MasterNode();
			return this;
		} else {
			return null;
		}
	}

	public CheckResult execute(String commands) {
		CheckResult checkResult = new CheckResult();
		if (commands != null && commands.trim().length() == 0) {
			checkResult = node.process(commands);
		}
		return checkResult;
	}

	// : as a shell standalone mode.
	public static void main(String[] args) throws IOException {
		Shell shell = Shell.instance();
		shell.startInteracting();

	}

	private void startInteracting() throws IOException {
		System.out.println("Please enter the name of the node..");
		BufferedReader nodenameReader = new BufferedReader(new InputStreamReader(System.in));
		String nodename = nodenameReader.readLine();

		if (nodename.equals("master")) {
			node = new MasterNode();
		} else {
			node = new ClientNode(nodename);
		}

		while (true) {
			BufferedReader commandReader = new BufferedReader(new InputStreamReader(System.in));
			String readline = commandReader.readLine();
			if (readline == null) {
				System.out.println("Ctrl + C ");
				break;
			} else {
				CheckResult checkResult = node.process(readline);
				System.out.println(":->" + checkResult.getResult());
				System.out.println(":->" + checkResult.getReason());
				// System.out.println(":->" + checkResult.getValue());
			}
		}

		System.exit(0);
	}

}

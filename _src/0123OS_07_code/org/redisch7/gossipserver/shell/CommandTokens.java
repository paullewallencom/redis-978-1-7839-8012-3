package org.redisch7.gossipserver.shell;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class CommandTokens {
	List<String> tokenList = new LinkedList<String>();

	public CommandTokens(String readLine) {
		this(readLine, " ");
	}

	public CommandTokens(String readLine, String dilimator) {
		String santizedString1 = readLine.replaceAll("\\s{2,}", "")
				.replaceAll("( ,)", ",").replaceAll("(, )", ",")
				.replaceAll("( =)", "=").replaceAll("(= )", "=");

		StringTokenizer stringTokenizer = new StringTokenizer(santizedString1,
				dilimator);
		while (stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			tokenList.add(token);
		}
	}

	public String get(int i) {
		return tokenList.get(i);
	}

	public void remove(int i) {
		tokenList.remove(i);

	}

	public String toStringAsList() {
		return tokenList.toString();
	}

	public String toSimpleString() {
		StringBuffer stringBuffer = new StringBuffer();
		for (String token : tokenList) {
			stringBuffer.append(token);
		}
		return stringBuffer.toString();
	}

	public int size() {
		return tokenList.size();
	}

	public List<String> getCommandTokens() {
		return tokenList;
	}
}

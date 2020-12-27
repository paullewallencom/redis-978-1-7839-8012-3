package org.redisch7.gossipserver.util.commandparser;

import java.util.ArrayList;
import java.util.List;

public class Template {
	private List<Token> tokenList = new ArrayList<Token>();

	public Template add(Token token) {
		tokenList.add(token);
		return this;
	}

	public int tokenLength() {
		return tokenList.size();
	}

	public Token get(int index) {
		if (index < tokenList.size()) {
			return tokenList.get(index);
		} else {
			return new NullToken();
		}

	}

	public void set(int index, Token token) {
		tokenList.set(index, token);
	}

	public List<Token> getAllTokens() {
		return tokenList;
	}

}

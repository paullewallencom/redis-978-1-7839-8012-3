package org.redisch7.gossipserver.util.commandparser;

import org.redisch7.gossipserver.shell.CheckResult;

public class StringToken extends Token {
	private String value;

	public StringToken() {

	}

	public StringToken(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public CheckResult validate(String string) {
		if (value == null && !string.contains(",") && !string.contains("=")) {
			return new CheckResult().setTrue();
		} else {
			if (!string.contains(",") && !string.contains("=")) {
				StringToken target = (StringToken) this.convertToToken(string);
				if (target.getValue().equals(this.getValue())) {
					return new CheckResult().setTrue();
				} else {
					return new CheckResult().setFalse("The input value="
							+ string + " and configured value = " + value);
				}
			} else {
				return new CheckResult().setFalse("Not a proper input value ");
			}
		}

	}

	@Override
	public Token convertToToken(String input) {
		return new StringToken(input);
	}
}

package org.redisch7.gossipserver.util.commandparser;

import org.redisch7.gossipserver.shell.CheckResult;

public class MapToken extends Token {
	private String name = null;
	private String value = null;
	private char equator = '=';

	public MapToken() {

	}

	public MapToken(String namevalue, char equator) {
		if (namevalue.contains(String.valueOf(equator))) {
			this.name = namevalue.split(String.valueOf(equator))[0];
			this.value = namevalue.split(String.valueOf(equator))[1];
		}
	}

	public MapToken(String namevalue) {
		this(namevalue, '=');
	}

	public MapToken(String name, String value, char equator) {
		this.name = name;
		this.value = value;
		this.equator = equator;
	}

	public MapToken(String name, String value) {
		this(name, value, '=');
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getNVAsString() {
		return name.concat("=".concat(value));
	}

	@Override
	public CheckResult validate(String string) {
		System.out.println(string);
		if (string.contains("=") && !string.contains(",")) {
			MapToken targetMapToken = (MapToken) this.convertToToken(string);
			if (name != null && value != null) {
				if (targetMapToken.getName().equals(this.getName())
						&& targetMapToken.getValue().equals(this.getValue())) {
					return new CheckResult().setTrue();
				} else {
					return new CheckResult().setFalse(
							"Target Name = " + targetMapToken.getName()
									+ " configured name = " + this.getName())
							.appendReason(
									"Target value ="
											+ targetMapToken.getValue()
											+ " configured value ="
											+ this.getValue());
				}
			} else {
				return new CheckResult().setTrue();
			}
		} else {
			return new CheckResult().setFalse("The input value " + string
					+ " is not as per configured token i.e. MapToken");
		}

	}

	@Override
	public Token convertToToken(String input) {
		return new MapToken(input);
	}
}

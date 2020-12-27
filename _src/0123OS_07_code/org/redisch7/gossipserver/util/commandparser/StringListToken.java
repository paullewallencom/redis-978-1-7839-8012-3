package org.redisch7.gossipserver.util.commandparser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.redisch7.gossipserver.shell.CheckResult;

public class StringListToken extends Token {
	private List<StringToken> stringTokenList = new ArrayList<StringToken>();
	private String dilimiter = ",";

	public StringListToken() {

	}

	public StringListToken(List<StringToken> stringTokenList, String dilim) {
		this.stringTokenList = stringTokenList;
		this.dilimiter = dilim;
	}

	public StringListToken(String data, String dilim) {
		String santizedString = data.replaceAll("\\s{2,}", "")
				.replaceAll("( ,)", ",").replaceAll("(, )", ",")
				.replaceAll("( =)", "=");
		StringTokenizer stringTokenizer = new StringTokenizer(santizedString,
				dilim);
		while (stringTokenizer.hasMoreElements()) {
			stringTokenList.add(new StringToken(stringTokenizer.nextToken()));
		}
	}

	public StringListToken(String data) {
		String santizedString = data.replaceAll("\\s{2,}", "")
				.replaceAll("( ,)", ",").replaceAll("(, )", ",")
				.replaceAll("( =)", "=");
		StringTokenizer stringTokenizer = new StringTokenizer(santizedString,
				dilimiter);
		while (stringTokenizer.hasMoreElements()) {
			stringTokenList.add(new StringToken(stringTokenizer.nextToken()));
		}
	}

	public StringListToken(List<String> asList) {
		for (String value : asList) {
			stringTokenList.add(new StringToken(value));
		}
	}

	public void add(StringToken stringToken) {
		this.stringTokenList.add(stringToken);
	}

	public StringToken get(int index) {
		return stringTokenList.get(index);
	}

	public StringListToken convertToToken(String input) {
		return new StringListToken(input);
	}

	public String getValue() {
		StringBuffer stringBuffer = new StringBuffer();
		for (int index = 0; index < stringTokenList.size(); index++) {
			if (index == stringTokenList.size() - 1) {
				stringBuffer.append(stringTokenList.get(index).getValue());
			} else {
				stringBuffer.append(stringTokenList.get(index).getValue());
				stringBuffer.append(dilimiter);
			}
		}
		return stringBuffer.toString();
	}

	public List<String> getValueAsList() {
		List<String> tokenList = new ArrayList<String>();

		for (int index = 0; index < stringTokenList.size(); index++) {
			tokenList.add(stringTokenList.get(index).getValue());
		}

		return tokenList;
	}

	@Override
	public CheckResult validate(String string) {
		if (stringTokenList.size() == 0) {
			return new CheckResult().setTrue();
		} else {
			CheckResult checkResult = this.convertToToken(string).isEqualTo(
					this);
			return checkResult;
		}

	}

	private CheckResult isEqualTo(StringListToken stringListToken) {
		CheckResult checkResult = new CheckResult();
		checkResult.setTrue();
		for (int index = 0; index < stringTokenList.size(); index++) {
			StringToken thisStringToken = this.get(index);
			if (!stringListToken.contains(thisStringToken)) {
				checkResult.setFalse("The token is not available ");
			}
		}
		return checkResult;
	}

	public boolean contains(StringToken thisStringToken) {
		for (StringToken stringToken : stringTokenList) {
			if (stringToken.getValue().equals(thisStringToken.getValue())) {
				return true;
			}
		}
		return false;
	}

}

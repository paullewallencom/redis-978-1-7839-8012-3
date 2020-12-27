package org.redisch7.gossipserver.util.commandparser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.redisch7.gossipserver.shell.CheckResult;
import org.redisch7.gossipserver.shell.CommandTokens;

public class Validator {
	private Template template = null;
	// private String inputString;
	private List<String> tokens = null;

	public Validator() {

	}

	public Template configureTemplate() {
		template = new Template();
		return template;
	}

	public void setInput(CommandTokens commandTokens) {
		this.clean();
		this.tokens = commandTokens.getCommandTokens();
	}

	public void setInput(String commandString) {
		this.clean();
		String santizedString = commandString.replaceAll("\\s{2,}", " ")
				.replaceAll("( ,)", ",").replaceAll("(, )", ",")
				.replaceAll("( =)", "=");

		StringTokenizer stringTokenizer = new StringTokenizer(santizedString,
				" ");
		while (stringTokenizer.hasMoreElements()) {
			tokens.add(stringTokenizer.nextToken());
		}
	}

	public CheckResult validate() {
		CheckResult checkResult = new CheckResult();
		checkResult.setTrue().appendReason("Command syntax is correct ");
		if (template.tokenLength() == tokens.size()) {
			for (int index = 0; index < tokens.size(); index++) {
				Token configuredtoken = template.get(index);
				CheckResult result = configuredtoken
						.validate(tokens.get(index));
				if (!result.getResult()) {

					return new CheckResult().setFalse(
							"Configured syntax does not match for token "
									+ tokens.get(index) + " \n").appendReason(
							result.getReason());
				} else {
					template.set(index,
							configuredtoken.convertToToken(tokens.get(index)));
				}
			}
		} else {
			return new CheckResult()
					.setFalse("There is a mismatch between the configure command template and passed command syntax "
							+ tokens.toString()
							+ " template is as follows "
							+ template.getAllTokens());
		}

		return checkResult;
	}

	public Token getToken(int i) {
		return template.get(i);
	}

	public List<Token> getAllTokens() {
		return template.getAllTokens();
	}

	public void clean() {
		tokens = new ArrayList<String>();
	}

}

package org.redisch7.gossipserver.util.commandparser;

import org.redisch7.gossipserver.shell.CheckResult;

public class Test2 {

	public static void main(String[] args) {
		Test2 test2 = new Test2();
		// test2.sample1();
		// test2.sample2();
		test2.sample3();
		// test2.sample4();
	}

	private void sample4() {
		String sample = "set x=5,y=6 where nodes are loki@wipro.com";
		Validator validator = new Validator();
		validator.configureTemplate().add((new StringToken("set")))
				.add(new MapListToken()).add(new StringToken("where"))
				.add(new StringToken("nodes")).add(new StringToken("are"))
				.add(new StringListToken());

		validator.setInput(sample);
		CheckResult checkResult = validator.validate();
		if (checkResult.getResult()) {
			MapListToken token = (MapListToken) validator.getToken(1);
			System.out.println(token.getValue());
			System.out.println(token.getSize());
			StringListToken stringListToken = (StringListToken) validator
					.getToken(5);
			System.out.println(stringListToken.getValue());

		} else {
			System.out.println(checkResult.getReason());
		}
	}

	private void sample3() {
		String sample = "command=set das";
		Validator validator = new Validator();
		validator.configureTemplate().add(new MapListToken());
		validator.setInput(sample);

		CheckResult checkResult = validator.validate();
		if (checkResult.getResult()) {
			MapListToken token = (MapListToken) validator.getToken(0);
			System.out.println(token.getValue());
			System.out.println(token.getSize());
			System.out.println(token.getValueAsSantizedString());
			MapListToken newMapListToken = token.removeElement("command");
			System.out.println(newMapListToken.getValue());
			System.out.println(newMapListToken.getSize());
			System.out.println(newMapListToken.getValueAsSantizedString());

		} else {
			System.out.println(checkResult.getReason());
		}

	}

	private void sample2() {
		String sample = "set vindas=where name=vindas,name1=vindas1";
		Validator validator = new Validator();
		validator.configureTemplate().add(new StringToken("set"))
				.add(new MapToken()).add(new MapListToken());

		validator.setInput(sample);
		CheckResult checkResult = validator.validate();
		if (checkResult.getResult()) {
			Token token0 = validator.getToken(0);
			System.out.println(token0.getValue());
			Token token1 = validator.getToken(1);
			System.out.println(token1.getValue());
			Token token2 = validator.getToken(2);
			System.out.println(token2.getValue());
			Token token3 = validator.getToken(3);
			System.out.println(token3.getValue());
		} else {
			System.out.println(checkResult.getReason());
		}

	}

	// private void sample1() {
	// String sample =
	// "set vinoo,das where name=vinoo , email=vinoo.das@gmail.com";
	// Validator validator = new Validator();
	// validator.configureTemplate().add(new StringToken("set")).add(new
	// StringListToken()).add(new StringToken("where")).add(new MapListToken());
	// validator.setInputString(sample);
	// CheckResult checkResult = validator.validate();
	// Token token0 = validator.getToken(0);
	// System.out.println(token0.getValue());
	// Token token1 = validator.getToken(1);
	// System.out.println(token1.getValue());
	// Token token2 = validator.getToken(2);
	// System.out.println(token2.getValue());
	// Token token3 = validator.getToken(3);
	// System.out.println(token3.getValue());
	// Token token4 = validator.getToken(4);
	// System.out.println(token4.getValue());
	// Token token5 = validator.getToken(5);
	// System.out.println(token5.getValue());
	//
	// }
}

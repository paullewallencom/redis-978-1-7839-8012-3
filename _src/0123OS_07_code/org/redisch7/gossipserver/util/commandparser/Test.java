package org.redisch7.gossipserver.util.commandparser;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) {
		Test test = new Test();
		test.stringToken();
		test.stringListToken1();
		test.stringListToken2();
		test.mapToken1();
		test.mapToken2();
		test.mapListToken1();
	}

	private void mapListToken1() {
		MapListToken mapListToken = new MapListToken("my=prog,x=dadadad");
		System.out.println(mapListToken.getSize());
		System.out.println(mapListToken.containsKey("my"));
		System.out.println(mapListToken.getNValue("my"));

	}

	private void mapToken3() {
		MapToken mapToken = new MapToken("my=prog");
		System.out.println(mapToken.getValue());
		System.out.println(mapToken.getName());
	}

	private void mapToken2() {
		MapToken mapToken = new MapToken("my =prog");
		System.out.println(mapToken.getValue());
		System.out.println(mapToken.getName());
	}

	private void mapToken1() {
		MapToken mapToken = new MapToken("my", "prog");
		System.out.println(mapToken.getValue());
		System.out.println(mapToken.getName());
	}

	private void stringListToken2() {
		StringListToken stringListToken = new StringListToken(Arrays.asList(
				"Heloo", "worldX"));
		System.out.println(stringListToken.get(0).getValue());
		System.out.println(stringListToken.get(1).getValue());
	}

	private void stringListToken1() {
		StringListToken stringListToken = new StringListToken("Hello,World");
		System.out.println(stringListToken.get(0).getValue());
		System.out.println(stringListToken.get(1).getValue());
	}

	private void stringToken() {
		StringToken stringToken = new StringToken("Hello");
		System.out.println(stringToken.getValue());
	}
}

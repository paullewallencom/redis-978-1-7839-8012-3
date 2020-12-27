package org.learningredis.chapter.two;

public class Helloworld2 {
	JedisWrapper jedisWrapper = null;
	public Helloworld2() {
		jedisWrapper = new JedisWrapper();
	}
	
	private void test() {
		jedisWrapper.set("MSG", "Hello world 2 ");
		
		String result = jedisWrapper.get("MSG");
		System.out.println("MSG : " + result);
	}
	
	
	public static void main(String[] args) {
		Helloworld2 helloworld2 = new Helloworld2();
		helloworld2.test();
	}
}





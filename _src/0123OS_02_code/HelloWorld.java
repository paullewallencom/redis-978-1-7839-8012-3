package org.learningredis.chapter.two;

import redis.clients.jedis.*;

public class HelloWorld {
	private JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
	
	private void test() {
		try 
        { 
            Jedis jedis = pool.getResource(); 
            jedis.set("MSG", "Hello World"); 
            String result = jedis.get("MSG16"); 
            System.out.println(" MSG : " + result); 
            pool.returnResource(jedis); 
           
        } 
        catch (Exception e) 
        { 
            System.err.println(e.toString()); 
        }finally{
             pool.destroy(); 
        }
		
	} 
	
    public static void main(String args[]) 
    { 
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.test();
    }

}

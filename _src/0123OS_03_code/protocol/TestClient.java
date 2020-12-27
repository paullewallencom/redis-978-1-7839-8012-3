package org.learningredis.chapter.three.protocol;



public class TestClient {
	
	public void execute(Command command){
    	try{
    		/*Connects to server*/
    		command.excute();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

	public static void main(String... args) {
		TestClient testclient = new TestClient();
		
		SetCommand set = new  SetCommand("MSG","Hello world : simple test client");
		testclient.execute(set);
		
		GetCommand get = new GetCommand("MSG");
		testclient.execute(get);
    }

	
    
}

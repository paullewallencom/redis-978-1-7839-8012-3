package org.learningredis.chapter.three.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SetCommand extends Command{

	
	private String key;
	private String value;
	
	
	public SetCommand(String string, String string2) {
		this.key=string;
		this.value=string2;
	}

	public String createPayload(){
		ArrayList<String> messageList = new ArrayList<String>();
		messageList.add("SET");
		messageList.add(key);
		messageList.add(value);
		return super.createPayload(messageList);
	}

	@Override
	public void excute() throws IOException  {
		PrintWriter out=null;
		BufferedReader in=null;
		try {
			out = new PrintWriter(super.socket.getOutputStream(), true);
			out.println(this.createPayload());
			
			//Reads from Redis server
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
			
			// This is going to be a single line reply..
			System.out.println(in.readLine());
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			in.close();
			out.flush();
			out.close();
			socket.close();
		}
	}

	
}

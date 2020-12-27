package org.learningredis.chapter.four.luascripting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

	public static String read(String filepath) {
		StringBuffer string = new StringBuffer();
		try (BufferedReader br = new BufferedReader(new FileReader(filepath)))
		{
 	
			String currentline;
			while ((currentline = br.readLine()) != null) {
				string.append(currentline);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return string.toString();
	}
}

package org.learningredis.chapter.four.reliablemsging;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.learningredis.chapter.four.pipeline.Reader;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import chapter.four.pubsub.ConnectionManager;

public class SimpleMsgSubscriber {
	
	static Thread lostMsgWorker;
	static Thread msgWorker;

	public static void main(String[] args) {
		SimpleMsgSubscriber source = new SimpleMsgSubscriber();

		msgWorker = new Thread(source.new MsgProcessor());
		lostMsgWorker = new Thread(source.new LostMsgProcessor());
		

		msgWorker.start();
		lostMsgWorker.start();
		

	}

	public class MsgProcessor extends JedisPubSub implements Runnable {
		
		Jedis jedis = ConnectionManager.get();

		public MsgProcessor() {
			
		}

		@Override
		public void run() {
			jedis.subscribe(this, "client1");
		}

		@Override
		public void onMessage(String arg0, String arg1) {
			System.out.println("processing the msg = " + arg1);
		}

		@Override
		public void onPMessage(String arg0, String arg1, String arg2) {
		}

		@Override
		public void onPSubscribe(String arg0, int arg1) {
		}

		@Override
		public void onPUnsubscribe(String arg0, int arg1) {
		}

		@Override
		public void onSubscribe(String arg0, int arg1) {
		}

		@Override
		public void onUnsubscribe(String arg0, int arg1) {
		}
	}

	public class LostMsgProcessor implements Runnable {
		Jedis jedis = ConnectionManager.get();

		@Override
		public void run() {
			try {
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String event;
			Jedis jedis = ConnectionManager.get();
			String msg;
			while((msg=jedis.spop("MSGBOX")) != null){
				MessageHandler.push(msg);
			}
		}
	}

	
	public static class MessageHandler {
		static Jedis jedis = ConnectionManager.get();

        public static void push(String msg)
        {
            String luaScript = "";
            try
            {
                luaScript = read("D:\\path\\to\\file\\RELIABLE-MSGING.txt");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            String result = (String) jedis.eval(luaScript, Arrays.asList(""), Arrays.asList("{type='channel',publishto='client1',msg='" + msg + "'}"));
        }

        private static String read(String luaScriptPath) throws IOException
        {
            Path file = Paths.get(luaScriptPath);
            BufferedReader reader = Files.newBufferedReader(file, Charset.defaultCharset());
            StringBuilder content = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                content.append(line).append("/n");
            }
            System.out.println("Content: " + content.toString());
            return content.toString();
        }
	}
}

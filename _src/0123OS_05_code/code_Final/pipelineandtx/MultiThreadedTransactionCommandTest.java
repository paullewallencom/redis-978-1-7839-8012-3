package org.learningredis.chapter.four.pipelineandtx;

public class MultiThreadedTransactionCommandTest {
	
	public static void main(String[] args) throws InterruptedException {
		Thread transactionClient = new Thread(new TransactionCommand());
		Thread singleCommandClient = new Thread(new SingleCommand());
		
		transactionClient.start();
		Thread.currentThread().sleep(30);
		singleCommandClient.start();
	}
}
//we dont see value in between as in pipeline
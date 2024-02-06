package com.payroc.shortenurl.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UrlShortenerHttpClient {

	public static void main(String[] args) throws Exception {


		//Keeping our thread pool to 2
		final int threadNum = 2;

		//Runnable to get short url from original url
		Runnable tinyUrlReturnRunnable = new PostRequest_ShortUrlGetRunnable();

		//Runnable to get original url from short url
		Runnable origUrlGetRunnable = new GetRequest_OriginalUrlGetRunnable();

		//Runnable to list all entries in our database
		Runnable listAllGetRunnable = new GetRequest_AllRecordsRunnable();


		//Instantiate executer using Executors factory 
		ExecutorService executor = Executors.newFixedThreadPool(5);

		//Use all threads to execute our API calls
		for(int i=0; i<threadNum; i++) {
			//executor.execute(tinyUrlReturnRunnable);
			//executor.execute(origUrlGetRunnable);
			executor.execute(listAllGetRunnable);
		}


		executor.shutdown();

		//Finish existing tasks
		try {
			executor.awaitTermination(2, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {

		}finally {
			if(!executor.isTerminated())
				executor.shutdown();
		}

	}
}
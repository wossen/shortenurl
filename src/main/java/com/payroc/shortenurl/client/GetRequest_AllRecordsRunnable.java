package com.payroc.shortenurl.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetRequest_AllRecordsRunnable implements Runnable {


	public void run() {
		doGet();
	}


	/**
	 * This method runs an Http GET request to get all the entries in our database.
	 * 
	 */
	public void doGet() {
		
		String url = "http://localhost:8080/shorten-url/api/shorturl/";
		
		HttpRequest request2 = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(url))
				.header("Content-Type", "application/json")
				.header("API-KEY", "MY-SECRET-API-KEY")
				.build();

		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request2, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		//Because we are calling the system println method in two occasions, we want the below output block in a synchronized block (response status & body)
		//Otherwise threads can interfere with each other and print in any undetermined manner to make the output in console confusing  
		//To lock, we need to get a class level lock on the PrintStream class (since it is being shared by our thread to write to console.
		//
		synchronized(System.out) {
			System.out.println(response.statusCode());
			HttpResponseBodyFormatter.formatListAllRecordBodyResponse(response);
		}

	}

}

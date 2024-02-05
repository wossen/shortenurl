package com.payroc.shortenurl.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetRequest_OriginalUrlGetRunnable implements Runnable {


	public void run() {

		doGet();

	}


	/**
	 * This method does a GET http request to our shortenUrl API by passing a short url as path parameter. 
	 * 
	 */
	public void doGet() {
		
		String url = "http://localhost:8080/shorten-url/api/shorturl/";

		//Do a post request first so we can get tiny Url
		String tinyUrl = doPost();

		System.out.println("Passing tiny url: " + tinyUrl + "\n");

		HttpRequest request2 = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(url + tinyUrl))
				.header("Content-Type", "application/json")
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
			HttpResponseBodyFormatter.formatSingleRecordBodyResponse(response);
		}

	}


	/**
	 * This method returns a short url from a given original url. we use this method to first add an entry to our database so 
	 * we can then use the returned short url in our GET request to get back the original url. 
	 * 
	 * @return short url
	 */
	public String doPost()  {

		int intRandom = ThreadLocalRandom.current().nextInt(0, 1000 + 1);

		HttpResponse<String> response = null;
		String url = "https://vesta.atlassian.net/plugins/servlet/ac/io.tempo.jira/tempo-app#!/teams/team/12/approvals/?teamId=12" + intRandom;

		HashMap<String, Object> payload = new HashMap<>();
		payload.put("url", url);

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = null;

		try {
			requestBody = objectMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}


		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.uri(URI.create("http://localhost:8080/shorten-url/api/shorturl/"))
				.header("Content-Type", "application/json")
				.build();

		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return response.body().split(":")[1].replaceAll("\"", "").replace("}","");
	}



}

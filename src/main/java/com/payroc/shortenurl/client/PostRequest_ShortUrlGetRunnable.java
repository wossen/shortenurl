package com.payroc.shortenurl.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostRequest_ShortUrlGetRunnable implements Runnable {


	public void run() {

		doPost();

	}

	/**
	 * This method does a post http request to our shortenUrl API. Since this runs in multithreaded way, we append
	 * random number to our url so we can pass a different url each time we call our API to shorten url
	 * 
	 */

	public void doPost()  {

		int intRandom = ThreadLocalRandom.current().nextInt(0, 1000 + 1);

		HttpResponse<String> response = null;
		String url = "https://vesta.atlassian.net/plugins/servlet/ac/io.tempo.jira/tempo-app#!/teams/team/12/approvals/?teamId=12" + intRandom;

		Map<String, Object> payload = new HashMap<>();
		payload.put("url", url);
		String requestJsonBody = getJsonPayload(payload);

		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(requestJsonBody))
				.uri(URI.create("http://localhost:8080/shorten-url/api/shorturl/"))
				.header("Content-Type", "application/json")
				.build();

		try {
			response = HttpClient.newHttpClient()
					.send(request, HttpResponse.BodyHandlers.ofString());
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
	 * Converts our payload in Map object to a Json object to be sent as http request body
	 * 
	 * @param payload - map object to convert to json
	 * @return String in Json format
	 */
	
	
	public String getJsonPayload(Map<String, Object> payload) {

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = null;

		try {
			requestBody = objectMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return requestBody;
	}


}

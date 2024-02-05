package com.payroc.shortenurl.client;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpResponseBodyFormatter {


	/**
	 * This methods formats the response (list of all rows in our database) we get from our DB in a nicer readable format 
	 * for us to be able to properly read the content in our console. 
	 * @param response: http response body
	 * 
	 */
	public static void formatListAllRecordBodyResponse(HttpResponse<String> response) {

		String[] responseArray = response.body().split(",");

		if(responseArray != null && responseArray[0].equals("[]")) {
			System.out.println("Database empty: "  + responseArray[0]);
			return;
		}

		StringBuilder formattedBody = new StringBuilder();

		formattedBody.append("[")
		.append(System.getProperty("line.separator"))
		.append("    {")
		.append(System.getProperty("line.separator"));

		for(String element : responseArray) {

			if(element.startsWith("{\"id") || element.startsWith("[{")) 
				element = element.replace("{","");


			if(!element.startsWith("\"click"))
				formattedBody
				.append("        " + element.replace("[","").replace("]",""))
				.append(System.getProperty("line.separator"));

			if(element.startsWith("\"click")) 
				formattedBody
				.append("        " + element.replace("}","").replace("]", ""))
				.append(System.getProperty("line.separator"))
				.append("    },")
				.append(System.getProperty("line.separator"));
		}

		formattedBody
		.append(System.getProperty("line.separator"))
		.append("]")
		.append(System.getProperty("line.separator"))
		.append("----------------END-------------")
		.append(System.getProperty("line.separator"));

		//Outputs response content to system.out
		System.out.println(formattedBody);
	}


	/**
	 * This methods formats the single row response we get either as a short url (passing original url) or 
	 * as a long url (passing short url) for us to be able to properly read the content in our console. 
	 * 
	 * @param response: http response body
	 * 
	 */
	public static void formatSingleRecordBodyResponse(HttpResponse<String> response) {

		String[] responseArray = response.body().split(",");


		List<String> str = Arrays.stream(responseArray)
				.map(row -> row.replace("{","").replace("}",""))
				.collect(Collectors.toList());

		StringBuilder formattedBody = new StringBuilder();

		formattedBody.append("[")
		.append(System.getProperty("line.separator"))
		.append("    {")
		.append(System.getProperty("line.separator"));

		for(String element : str) {
			formattedBody.append("        " + element)
			.append(System.getProperty("line.separator"));
		}

		formattedBody.append("    }")
		.append(System.getProperty("line.separator"))
		.append("]")
		.append(System.getProperty("line.separator"));

		//Outputs response content to system.out
		System.out.println(formattedBody);

	}

}

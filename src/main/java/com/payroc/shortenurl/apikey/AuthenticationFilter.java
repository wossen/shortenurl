package com.payroc.shortenurl.apikey;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

/**
 * We use this Request filter to check for API-KEY. This is a very simplistic example of API-KEY just to demo how
 * we can intercept our API calls to authorize users. The API-Key handling on server side is not implemented due to
 * time constraint.
 * 
 * @KeyRequired annotation is used for our list all Urls API call.
 */

@Provider
@KeyRequired
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());


    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
    	
    	String apiKey = crc.getHeaderString("API-Key");
    	
    	if(apiKey == null || !apiKey.equals("MY-SECRET-API-KEY"))
    		throw new WebApplicationException("Invalid API KEY.", Response.Status.FORBIDDEN); 
    	
    	logger.info(crc.getMethod() + " " + crc.getHeaderString("API-Key"));
    	logger.info(crc.getMethod() + " " + crc.getUriInfo().getAbsolutePath());
    	
    }

}
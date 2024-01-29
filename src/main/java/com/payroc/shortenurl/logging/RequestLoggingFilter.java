package com.payroc.shortenurl.logging;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

/**
 * To log incoming requests, a ContainerRequestFilter is used to intercept the request and 
 * extract information such as the request method, URL, headers, and body. 
 * This information can be logged to a file, database or other storage medium.
 * 
 */

@Provider
@Log
public class RequestLoggingFilter implements ContainerRequestFilter {
    private static final Logger logger = Logger.getLogger(RequestLoggingFilter.class.getName());


    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
    	
    	logger.info(crc.getMethod() + " " + crc.getUriInfo().getAbsolutePath());
        for (String key : crc.getHeaders().keySet()) {
            logger.info("[REST Logging] " +key + ": " + crc.getHeaders().get(key));
        }
    }

}
package com.payroc.shortenurl.logging;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

/**
 * To log outgoing responses, a ContainerResponseFilter is used to intercept the response and 
 * extract information such as response status, headers, and body. 
 * This information can be logged to a file, database or other storage medium.
 * 
 */

@Log
@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter {
    private static final Logger logger = Logger.getLogger(ResponseLoggingFilter.class.getName());
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, String> map = responseContext.getStringHeaders();
        
        if (map != null && !map.isEmpty()) {
            logger.info("Response Headers:");
                logger.info(map.toString());
        }
    }
}
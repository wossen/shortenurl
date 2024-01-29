package com.payroc.shortenurl.exceptions;

import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


/**
 * Instead of handling this commonly thrown exception explicitly, you could write an ExceptionMapper
 * to handle this exception for us
 * Our ExceptionMapper implementation must be annotated with the @Provider annotation. 
 * This tells the JAX-RS runtime that it is a component. 
 * 
 */
@Provider
public class NoResultExceptionMapper
implements ExceptionMapper<NoResultException> {
	
	@Override
	public Response toResponse(NoResultException e) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}

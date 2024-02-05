package com.payroc.shortenurl.jaxrs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;


/**
 * Jaxrs resourse for a health check.
 * This can be used in prod environment to continuously check for our application health status.
 * Kubernetes for example, can be configure to call this endpoint for Startup, Liveness, Readiness checks.
 * 
 */
@Path("/health")
public class Healthcheck {


	@PersistenceContext(unitName="primary")
	private EntityManager entityManager;

	/**
	 * This healthcheck validates our Jaxrs resources are registered and configured properly.
	 * 
	 * @return
	 */
	@GET
	public Response checkHealth() {
		return Response.status(Status.OK).entity("Status: [UP]").build();
	}



	/**
	 * This healthcheck makes a call to our database and verifies our database is up and running. 
	 * @return
	 */
	@GET
	@Path("/db")
	public Response checkDatabaseHealth() {

		Integer dbResponse = (Integer)entityManager
				.createQuery("SELECT 1").getSingleResult();
		if(dbResponse == 1)
			return Response.status(Status.OK).entity("Database Status: [UP]").build();
		
		return	Response.status(Status.OK).entity("Database Status: [DOWN]").build();
	}

}
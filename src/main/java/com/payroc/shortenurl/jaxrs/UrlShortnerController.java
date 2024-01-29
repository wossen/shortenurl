package com.payroc.shortenurl.jaxrs;
import java.util.List;

import com.payroc.shortenurl.cdi.UrlShortnerDAOService;
import com.payroc.shortenurl.jpa.UrlEntity;
import com.payroc.shortenurl.jpa.UrlRequest;
import com.payroc.shortenurl.jpa.UrlResponse;
import com.payroc.shortenurl.logging.Log;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;


/**
 * Base context for our restful API
 * 
 */
@Path("/shorturl")
public class UrlShortnerController {

	@Inject 
	UrlShortnerDAOService urlShortnerDAOService;


	/**
	 * List all the enties in database, a handy API to see the entries added to our database
	 * Accessing this through API KEY is the plan 
	 * 
	 * @Log annotation allows the LoggingFilter to intercept this REST endpoint.
	 * 
	 */
	@Log
	@GET
	@Produces("application/json")
	public List<UrlEntity> getAll() {
		return urlShortnerDAOService.findAll();
	}


	/**
	 * This returns the original Url stored in database searching by tiny Url
	 * 
	 * @param tiny URL that is passed as path parameters within the request url. Here we are forced
	 *        to pass two Path params as our tiny URL will have forward slash as part of its url
	 * @returns UrlResponse a wrapper URL object that has the original URL
	 * 
	 * @Log annotation allows the LoggingFilter to intercept this REST endpoint.
	 */
	@Log
	@GET
	@Path("{tiny1}/{tiny2}")
	@Produces("application/json")
	@Consumes("application/json")
	public UrlResponse getLongUrlByTinyUrl(@PathParam("tiny1") String tiny1, @PathParam("tiny2") String tiny2) {

		String tinyUrl = tiny1 + "/" + tiny2;
		
		UrlEntity urlEntity = urlShortnerDAOService
				.findOrigUrlByTinyUrl(tinyUrl);

		if(urlEntity == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		UrlResponse urlResponse = new UrlResponse();
		urlResponse.setUrl(urlEntity.getOrigUrl());
		return urlResponse;
	}


	/**
	 * This returns tiny Url created and stored in database
	 * 
	 * @param UrlRequest long URL wrapped in UrlRequest object
	 * @returns UrlResponse a wrapper UrlResponse object that has a tiny URL
	 * 
	 * @Log annotation allows the LoggingFilter to intercept this REST endpoint.
	 */
	@Log
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public UrlResponse createTinyUrl(UrlRequest urlRequest) {
		return urlShortnerDAOService.createTinyUrl(urlRequest.getUrl());
	}

}
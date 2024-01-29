package com.payroc.shortenurl;

import java.util.HashSet;
import java.util.Set;

import com.payroc.shortenurl.exceptions.EntityNotFoundMapper;
import com.payroc.shortenurl.exceptions.NoResultExceptionMapper;
import com.payroc.shortenurl.jaxrs.UrlShortnerController;
import com.payroc.shortenurl.logging.RequestLoggingFilter;
import com.payroc.shortenurl.logging.ResponseLoggingFilter;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Root path where URLs are accessed.
 * A bean file is not used for this application as everything needed to be registered for Jthe axrs Component manager is
 * added in this class in the getClasses() method.
 * @author Wossenyeleh Abate
 *
 */

@ApplicationPath("/api")
public class ApplicationService extends Application {

	Set<Class<?>> classes = new HashSet<>();
	Set<Object> singletons = new HashSet<>();

	public ApplicationService() {
		
	}

	/**
	 * Register Jaxrs resources.
	 */
	@Override
	public Set<Class<?>> getClasses(){
		classes.add(UrlShortnerController.class);
		classes.add(NoResultExceptionMapper.class);
		classes.add(EntityNotFoundMapper.class);
		classes.add(RequestLoggingFilter.class);
		classes.add(ResponseLoggingFilter.class);
		return classes;
	}


	/**
	 * This returns empty set. Only used when we need to make a resource stateful.
	 * We need to instantiate the stateful objects if that is the case.
	 */
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}

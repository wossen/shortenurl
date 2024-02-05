package com.payroc.shortenurl.cdi;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Interface for our base62 and base36 implementations. 
 * 
 */


@ApplicationScoped
public interface UrlShortenerUtil {
	
	public String generateTinyUrl(Long primaryKey);
	public int parsePrimaryKey(String tinyUrl);

}

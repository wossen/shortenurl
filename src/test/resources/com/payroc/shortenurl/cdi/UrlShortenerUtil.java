package com.payroc.shortenurl.cdi;

import jakarta.enterprise.context.ApplicationScoped;


/**
 * The class is a utility class that is used to generate base62 unique key.
 * 
 * @author Wossenyeleh Abate
 * 
 * @ApplicationScoped this annotation makes this class CDI injectable with application scope  
 *
 */
@ApplicationScoped
public class UrlShortenerUtil {

	private final String TINY_URL = "tiny.io/"; 
	private final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private final int    BASE     = ALLOWED_CHARACTERS.length();


	/**
	 * This is the steps used to encode to base 62
	 * 1. Declare alphabet to use, that's [a-zA-Z0-9]. It contains 62 letters.
	 * 2. Take an auto-generated, unique numerical key (the auto-incremented id of our in memory database).
	 * 3. Convert to base62. To convert our primary key to base62, we use long division to divide the decimal by sixty-two. 
	 * Then you take the remainder and map it to the character that represents it by 
	 * locating its position within the base62 digits.
	 * @param primaryKey primaryKey value
	 * @return encoded base61 value
	 * 
	 */
	public String encodeToBase62(Long primaryKey) {
		StringBuilder sb = new StringBuilder();
		while ( primaryKey > 0 ) {
			sb.append( ALLOWED_CHARACTERS.charAt( Math.toIntExact(primaryKey) % BASE ) );
			primaryKey = primaryKey / BASE;
		}
		return sb.reverse().toString();   
	}

	/**
	 * We do a reverse lookup in your alphabet to decode primary key value
	 * @param encodedPrimaryKey the encode primary key 
	 * @return Original primary key
	 */
	public int decodeToPrimaryKey(String encodedPrimaryKey) {
		int primaryKey = 0;
		for ( int i = 0; i < encodedPrimaryKey.length(); i++ )
			primaryKey = primaryKey * BASE + ALLOWED_CHARACTERS.indexOf(encodedPrimaryKey.charAt(i));
		return primaryKey;
	} 

	public String generateTinyUrl(Long primaryKey) {
		String encodedPrimaryKey = encodeToBase62(primaryKey);
		return TINY_URL + encodedPrimaryKey;
	}

	public int parsePrimaryKey(String tinyUrl) {

		String encodedPrimaryKey = null;
		
		if(tinyUrl != null) 
			encodedPrimaryKey = tinyUrl.substring(8);

		return decodeToPrimaryKey(encodedPrimaryKey);
	}


}
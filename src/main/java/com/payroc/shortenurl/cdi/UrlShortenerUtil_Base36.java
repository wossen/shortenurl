package com.payroc.shortenurl.cdi;

import jakarta.enterprise.context.ApplicationScoped;


/**
 * The class is a utility class that is used to generate base36 unique key.
 * This will be an alternative class to our base62 implementation. The one good thing about this implementation is, it makes
 * our short urls case insensitive making them convenient for users.
 * 
 * @author Wossenyeleh Abate
 * 
 * @ApplicationScoped this annotation makes this class CDI injectable with application scope
 * @BaseThirtySix tells CDI container to instantiate interface type with this class when annotated  
 *
 */
@BaseThirtySix
@ApplicationScoped
public class UrlShortenerUtil_Base36 implements UrlShortenerUtil {

	private final String TINY_URL = "tiny.io/"; 
	private final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
	private final int    BASE     = ALLOWED_CHARACTERS.length();


	/**
	 * This is the steps used to encode to base 36
	 * 1. Declare alphabet to use, that's [a-z0-9]. It contains 36 letters.
	 * 2. Take an auto-generated, unique numerical key (the auto-incremented id of our in memory database).
	 * 3. Convert to base36. To convert our primary key to base36, we use long division to divide the decimal by thirty-six. 
	 * Then you take the remainder and map it to the character that represents it by 
	 * locating its position within the base36 digits.
	 * @param primaryKey primaryKey value
	 * @return encoded base36 value
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
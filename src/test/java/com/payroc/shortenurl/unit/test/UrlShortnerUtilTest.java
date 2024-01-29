package com.payroc.shortenurl.unit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.payroc.shortenurl.cdi.UrlShortenerUtil;


@ExtendWith(MockitoExtension.class)
public class UrlShortnerUtilTest {

	@InjectMocks
	UrlShortenerUtil util;

	private static String TINY_URL_PREFIX;
	private String TINY_URL;

	@BeforeAll
	public static void init() {
		TINY_URL_PREFIX = "tiny.io/"; 
	}


	@Test
	public void generateTinyUrlTest() {
		TINY_URL = util.generateTinyUrl(1L);
		assertEquals(TINY_URL_PREFIX + "b", TINY_URL);
	}

	@Test
	public void parsePrimaryKeyTest() {
		TINY_URL = util.generateTinyUrl(1L);
		assertEquals(1, util.parsePrimaryKey(TINY_URL));
	}

}

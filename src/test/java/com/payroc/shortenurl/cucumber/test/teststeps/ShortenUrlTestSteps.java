package com.payroc.shortenurl.cucumber.test.teststeps;

import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.payroc.shortenurl.cucumber.test.ConfigReader;
import com.payroc.shortenurl.cucumber.test.UrlResponse;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

public class ShortenUrlTestSteps extends AbstractRestAssuredTestSteps {
    
	UrlResponse tinyUrlResponse;
	UrlResponse longUrlResponse;
	String tinyUrl;
	String longUrl;
	
	Properties prop = ConfigReader.getConfiguration(); 
    
    
    @Given("a long URL")
    public void setupLongUrl() throws Throwable
    {
    	 RestAssured.baseURI = prop.getProperty("baseURI");
         RestAssured.basePath = prop.getProperty("basePath");
         longUrl = prop.getProperty("longUrl");;
    }
    
    @When("I Post ShortenUrl API passing the Long URL as payload")
    public void callShortenUrlAPI() throws Throwable
    {
    	Map<String, String> map = new HashMap<>();
    	map.put("url", longUrl);
    	tinyUrlResponse = callPOSTApi( map, "/shorturl", UrlResponse.class);
    	tinyUrl = tinyUrlResponse.getUrl();
    }

    @Then("I should get a unique Short URL")
    public void validateLongUrlesponse()
    {
         assertNotNull(tinyUrlResponse.getUrl());
    }
    
    
    @Given("Having a unique Short URL")
    public void setupShortUrl() throws Throwable
    {
    	RestAssured.baseURI = prop.getProperty("baseURI");
        RestAssured.basePath = prop.getProperty("basePath");
        longUrl = prop.getProperty("longUrl");;
    }
    
    
    @When("I call API passing Short URL as Path parameter")
    public void callShortenUrlAPI_ReturnLongUrl() throws Throwable
    {
    	Map<String, String> longUrlMap = new HashMap<>();
    	longUrlMap.put("url", longUrl);
    	Map<String, String> shortUrlMap = new HashMap<>();
    	
    	tinyUrlResponse = callPOSTApi( longUrlMap, "/shorturl", UrlResponse.class);
    	longUrlResponse = callGETApi( shortUrlMap, "/shorturl/" + tinyUrlResponse.getUrl(), UrlResponse.class);
    	tinyUrl = longUrlResponse.getUrl();
    }

    @Then("I should get the original url in response")
    public void validateShortUrlResponse()
    {
         assertNotNull(longUrlResponse.getUrl());
    }
    
}

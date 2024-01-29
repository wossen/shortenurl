
Initiative: Url Shortner
   Design and build a restful Url Shortener service that creates a short/tiny url from a very long URL. 

Feature: It creates a short/tiny url from a very long URL. Passing the short URL, the user will get back the original URL. The service should be REST API accessible.
         For portability, and to speed up developing and testing, this restful app uses Wildfly H2 in-memory database. This also helps to easily deploy and test on a server. 


Scenarios: these scenarios will be used for our integration tests.

   1. Given a long URL, (POST request passing URL as payload in request body), it should generate a unique Short URL
   2. Given a short URL, (GET request passing tiny URL as path parameter), should return to the original URL. This also counts the number of times the url has been accessed.
   3. A simple Rest GET request will display all the URL entries stored in our database.


    Please note: The wildfly server needs to run locally on the default 8080 port.

          Eg. Scenario 1: 
		  
		         API:  POST /shorten-url/api/shorturl
		         
				 eg: http://localhost:8080/shorten-url/api/shorturl/
				 
				 HTTP Method: POST
		         
				 Payload in Post request:
				 
				    {

					"url"  : "https://www.mastertheboss.com/java-ee/jakarta-ee/jakarta-persistence-3-1-new-features/"

				    }
					
			  Scenario 2: (assuming the returned tiny url is 'tiny.io/f', we will append the tiny url as path parameter as shown below)
			  
			       API: GET /shorten-url/api/shorturl/tiny.io/f
			  
			       eg: http://localhost:8080/shorten-url/api/shorturl/tiny.io/f
				 
				   HTTP Method: GET
				   
		      Scenario 3:
			  
			       API: GET /shorten-url/api/shorturl
			  
			       eg: http://localhost:8080/shorten-url/api/shorturl
				   
				   HTTP Method: GET
				   

Application Server: Wildfly 29, Wildfly 31 (latest)
database: Wildfly H2 in-memory database. It ships in JBoss EAP and WildFly application server 

Java EE technologies: Core Java, Jaxrs, CDI, JPA 

Java JDK Version: 17

Testing tools and Farmeworks: RestAssured, Cucumber, Junit, Mockito

Build and management tool: Maven

IDE: Eclipse (Version 2023-12)

Note: Cucumber integration tests are included in this project. However, as they are integration tests, they need a running instance of this app to run against and pass. 
      Therefore, they are disabled by default so they don't interfere with generating artifacts (while running mvn install). Once we have a running instance of the app,
      they can be enabled by removing the @ignore tag applied in the cucumber feature file. The file is named ShortenUrlTests.feature and exists in src/test/resource inside 
      functionalTests folder. In this file, please remove the @ignore tag on line 19.				  


How to Deploy to Wildlfy server:
Wildfly runs on localhost 8080 by default and this application uses these default host:port
1. Get the latest Wildfly Zip - WildFly 31 Final (also test on Wildfly 29)
2. Got to the bin folder and run standalone.bat(Windows) or standalone.sh(Linux)
3. Drop the shoren-url.war file into standalone/deploymets folder.



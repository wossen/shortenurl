package com.payroc.shortenurl.cucumber.test.teststeps;

import org.apache.http.HttpStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public abstract class AbstractRestAssuredTestSteps
{


    public <T, V> T callPOSTApi(V requestBody, String path, Class<T> objectType)
    {

        return RestAssured.given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .post(path)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .log()
                .ifValidationFails()
                .extract()
                .jsonPath()
                .getObject("", objectType);

    }
    
    
    public <T, V> T callGETApi(V requestBody, String path, Class<T> objectType)
    {

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(path)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .log()
                .ifValidationFails()
                .extract()
                .jsonPath()
                .getObject("", objectType);

    }
    
}
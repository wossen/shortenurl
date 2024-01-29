package com.payroc.shortenurl.cucumber.test.runners;

import io.cucumber.testng.*;

@CucumberOptions(
        features = "src/test/resources/functionalTests",
        glue = {"com.payroc.shortenurl.cucumber.test.teststeps"},
        plugin = {"pretty", "json:target/cucumber-reports/Cucumber.json"},
        tags = "not @ignore",
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

}

#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@ShortenUrlApplicationTests
@ignore
Feature: ShortenUrl application functionality testing

  Scenario: ShortenUrl app returns a unique Short URL for a given Long Url
    Given a long URL
    When I Post ShortenUrl API passing the Long URL as payload
    Then I should get a unique Short URL
  
  Scenario: ShortenUrl Get api returns original url when tiny url is passed as Path parameter
    Given Having a unique Short URL
    When I call API passing Short URL as Path parameter
    Then I should get the original url in response
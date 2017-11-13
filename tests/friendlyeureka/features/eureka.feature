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

Feature: Eureka login
  I want to use this template for my feature file

  Scenario Outline: Eureka login with valid credentials
    Given I am on Eureka Website
    When I enter username as "<username>"
    And enter password as "<password>"
    And click login button
    Then I should be able to login successfully and see my username as "<username>"

    Examples: 
      | username                 | password   |
      | selintestselin@gmail.com | test0test  |
      | selintestselin@gmail.com | test0test1 |
      | test@gmail.com           | test       |
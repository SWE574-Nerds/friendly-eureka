Feature: Listory tests
  I want to use this template for my feature file

  Scenario Outline: List history items
    Given I am on Eureka Website
    When I login with valid credentials using "<username>" and "<password>"
    And I click to see history items list
    Then I should be able to list the history items
    Then I click logout

    Examples:
      | username                 | password               |
      | AutomatedTestUser        | AutomatedTestPassword  |  
Feature: Test CRUD methods in Department REST API testing

  Scenario: Add list of Departments
    Given A list of departments
    When No departments
    Then add the listed departments with success response

  Scenario: Get all departments
    Given
    When I try to get all departments
    Then it returns success message if a department exists

  Scenario: Get department by name
  Given
    When I try to search a department which is not exists
    Then it returns a bad request status

  Scenario: Get department by name
  Given
    When I try to search an existing department
    Then it returns the success status

  Scenario: Update a department with a new department name
    Given A department data
    When I try to search and update the department
    Then department would update and return a success status

  Scenario: Update a department with an existing department name
    Given A department details
    When I try to update the departmentname with existing departmentname
    Then department would reject the request

  Scenario: Update a department with an unknown Departmentid
    Given A department information
    When I try to update a department with an unknown ID
    Then department would forbid the request

  Scenario: delete an existing department
    Given
    When I try to delete an existing department
    Then department has been deleted with success status

  Scenario: delete an unknown department
  Given
    When I try to delete an unknown department
    Then I receive a forbid response



Feature: Test CRUD methods in Employee REST API testing

  Scenario: Add Departments
    Given A list of predefined departments
    When No department exists
    Then add new department with success response

  Scenario: Add Employee
    Given Employee details
    When No employee exists with the employee number
    Then add this new employee to the department

  Scenario: Get all employee
    Given
    When I try to fetch all employee details
    Then returns a valid response

  Scenario: Get employee by ID
  Given
    When I try to fetch an employee data based on ID
    Then returns a valid response_employeeID

  Scenario: Get employee by Name
  Given
    When I try to fetch an employee data based on Name
    Then returns a valid response_employeeName

  Scenario: Get employee by Name
  Given
    When I try to fetch an employee who is not registered
    Then returns a not found response

  Scenario: Delete Employee by ID
  Given
    When I delete an employee
    Then return a success message

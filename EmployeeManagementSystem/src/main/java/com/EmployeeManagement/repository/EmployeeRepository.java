package com.EmployeeManagement.repository;

import com.EmployeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    public List<Employee> findByName(String name);
    public List<Employee> findByEmployeeNumber(String number);

    public List<String> findByEmailID(String number);

    @Query("SELECT  COUNT (e.employeeNumber) from Employee e" +
            " where e.department.DEPARTMENTID = :DEPARTMENTID" +
            " AND e.employeeNumber = :employeeNumber")
    public int findEmployeeNumberAndDepartment(@Param("DEPARTMENTID") int departmentID, @Param("employeeNumber") String employeeNumber);

    @Query("SELECT count(DEPARTMENTID) from Department" +
            " where DEPARTMENTID = :DEPARTMENTID")
    public int findAnyDepartment(@Param("DEPARTMENTID") int id);

}



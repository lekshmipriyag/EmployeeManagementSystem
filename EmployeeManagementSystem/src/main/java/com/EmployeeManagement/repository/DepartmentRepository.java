package com.EmployeeManagement.repository;

import com.EmployeeManagement.model.Department;
import com.EmployeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    public List<Department> findByDepartmentname(String name);

    @Query("SELECT  e.name,e.employeeNumber from Employee e" +
            " INNER JOIN Department d ON d.DEPARTMENTID = e.department.DEPARTMENTID" +
            " where d.departmentname = :departmentname")
    public List<String> findEmployeeDetails(@Param("departmentname") String departmentname);

    public int findByDEPARTMENTID(int id);

    @Query("SELECT  COUNT (e.employeeNumber) from Employee e" +
            " where e.department.DEPARTMENTID = :DEPARTMENTID" )
    public int isDepartmentUsed(@Param("DEPARTMENTID") int departmentID);

    @Query("SELECT count(DEPARTMENTID) from Department" +
            " where DEPARTMENTID = :DEPARTMENTID")
    public int findAnyDepartment(@Param("DEPARTMENTID") int id);


}

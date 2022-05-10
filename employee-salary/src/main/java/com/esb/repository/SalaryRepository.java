package com.esb.repository;

import com.esb.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    @Query("SELECT  COUNT (s.employeeNumber) from Salary s" +
            " where s.employeeNumber = :employeeNumber" )
    public int isEmployeeSalary(@Param("employeeNumber") String employeeNumber);

}

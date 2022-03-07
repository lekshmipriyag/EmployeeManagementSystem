package payment.employeesalary.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Data
@Table(name = "Salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int salaryId;

    @Column(name = "employee_number")
    @NotBlank(message = "Employee Number is required")
    private String employeeNumber;

    @Column(name = "basic_salary")
    @DecimalMax(value = "7000.0", message = "Basic salary maximum limit is 7000")
    @DecimalMin(value = "3000.0", message = "Basic salary minimum limit is 3000")
    private double basicSalary;


    @DecimalMax(value = "1000.0", message = "Allowance maximum limit is 1000")
    @DecimalMin(value = "300.0", message = "Allowance minimum limit is 300")
    private double allowance;

    private double total;

}

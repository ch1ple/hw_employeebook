package me.ch1ple.hw.employeebook.service;

import me.ch1ple.hw.employeebook.model.Employee;

import java.util.Collection;
import java.util.List;

public interface EmployeeService {

    Employee addEmployee(String firstName, String lastName, int salary, int departmentId);

    Employee removeEmployee(String firstName, String lastName);

    Employee findEmployee(String firstName, String lastName);

    Collection<Employee> printEmployees();

    Employee getEmployeeWithMaxSalary();

    Employee getEmployeeWithMinSalary();

    List<Employee> getEmployees();

    Employee setSalary(String firstName, String lastName, int salary);

    int calcTotalMonthlySalary();

}

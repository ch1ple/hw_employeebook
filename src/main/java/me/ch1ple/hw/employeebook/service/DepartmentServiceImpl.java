package me.ch1ple.hw.employeebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import me.ch1ple.hw.employeebook.exception.EmployeeNotFoundException;
import me.ch1ple.hw.employeebook.model.Employee;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final EmployeeService employeeService;

    @Autowired
    public DepartmentServiceImpl(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Number getMaxSalary(final int departmentId) {
        final Employee employee = employeeService.getEmployees()
                .stream()
                .filter(emp -> emp.getDepartmentId() == departmentId)
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with max salary in department " + departmentId + " not found"));

        return employee.getSalary();
    }

    @Override
    public Number getMinSalary(final int departmentId) {
        final Employee employee = employeeService.getEmployees()
                .stream()
                .filter(emp -> emp.getDepartmentId() == departmentId)
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with min salary in department " + departmentId + " not found"));

        return employee.getSalary();
    }

    @Override
    public List<Employee> getAllEmployeesByDepartment(final int departmentId) {
        return employeeService.getEmployees()
                .stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<Employee>> employeesGroupedByDepartment() {
        return employeeService.getEmployees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartmentId));
    }

    @Override
    public Number calcTotalSalaryByDepartment(final int departmentId) {
        return employeeService.getEmployees()
                .stream()
                .filter(employee -> employee.getDepartmentId() == departmentId)
                .mapToInt(Employee::getSalary)
                .sum();
    }

}

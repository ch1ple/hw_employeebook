package me.ch1ple.hw.employeebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import me.ch1ple.hw.employeebook.model.Employee;
import me.ch1ple.hw.employeebook.service.DepartmentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController {

    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping(value = "/employees")
    public Map<Integer, List<Employee>> getAllEmployees() {
        return service.employeesGroupedByDepartment();
    }

    @GetMapping(value = "/{id}/employees")
    public List<Employee> getAllEmployeesByDepartment(@PathVariable(value = "id") Integer id) {
        return service.getAllEmployeesByDepartment(id);
    }

    @GetMapping(value = "/{id}/salary/sum")
    public Number calcTotalSalaryByDepartment(@PathVariable(value = "id") Integer id) {
        return service.calcTotalSalaryByDepartment(id);
    }

    @GetMapping(path = "/{id}/salary/max")
    public Number getMaxSalaryInDepartment(@PathVariable(value = "id") Integer id) {
        return service.getMaxSalary(id);
    }

    @GetMapping(path = "/{id}/salary/min")
    public Number getMinSalaryInDepartment(@PathVariable(value = "id") Integer id) {
        return service.getMinSalary(id);
    }

}

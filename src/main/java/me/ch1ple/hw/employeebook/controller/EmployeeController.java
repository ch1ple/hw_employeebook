package me.ch1ple.hw.employeebook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import me.ch1ple.hw.employeebook.exception.EmployeeAlreadyAddedException;
import me.ch1ple.hw.employeebook.exception.EmployeeNotFoundException;
import me.ch1ple.hw.employeebook.exception.InvalidNameException;
import me.ch1ple.hw.employeebook.exception.InvalidSurnameException;
import me.ch1ple.hw.employeebook.model.Employee;
import me.ch1ple.hw.employeebook.service.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Employee> printEmployees() {
        return service.printEmployees();
    }

    @GetMapping(path = "/add")
    public Employee addEmployee(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("salary") int salary,
                                @RequestParam("departmentId") int departmentId
    ) {
        return service.addEmployee(firstName, lastName, salary, departmentId);
    }

    @GetMapping(path = "/remove")
    public Employee removeEmployee(@RequestParam("firstName") String firstName,
                                             @RequestParam("lastName") String lastName
    ) {
        return service.removeEmployee(firstName, lastName);
    }

    @GetMapping(path = "/find")
    public Employee findEmployee(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName
    ) {
        return service.findEmployee(firstName, lastName);
    }

    @GetMapping(path = "/set-salary")
    public Employee setSalary(@RequestParam("firstName") String firstName,
                                        @RequestParam("lastName") String lastName,
                                        @RequestParam("salary") int salary
    ) {
        return service.setSalary(firstName, lastName, salary);
    }

    @GetMapping(path = "/max-salary")
    public Employee getEmployeeWithMaxSalary() {
        return service.getEmployeeWithMaxSalary();
    }

    @GetMapping(path = "/min-salary")
    public Employee getEmployeeWithMinSalary() {
        return service.getEmployeeWithMinSalary();
    }

    @GetMapping(path = "/total-salary")
    public int calcTotalMonthlySalary() {
        return service.calcTotalMonthlySalary();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public String handleNotFoundException(EmployeeNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeAlreadyAddedException.class)
    public String handleAlreadyAddedException(EmployeeAlreadyAddedException e) {
        return e.getMessage();
    }

    @ExceptionHandler({InvalidNameException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handleInvalidNameException(InvalidNameException e) {
        return "Имя '%s' должно содержать только буквы!" . formatted(e.getMessage());
    }

    @ExceptionHandler({InvalidSurnameException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handleInvalidSurnameException(InvalidSurnameException e) {
        return "Фамилия '%s' должна содержать только буквы!" . formatted(e.getMessage());
    }

}

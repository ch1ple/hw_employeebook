package me.ch1ple.hw.employeebook.constants;

import me.ch1ple.hw.employeebook.model.Employee;

import java.util.List;

public class EmployeeConstants {

    public static final String FIRST_NAME_1 = "Марина";
    public static final String FIRST_NAME_2 = "Елена";
    public static final String FIRST_NAME_3 = "Дмитрий";
    public static final String NEW_PERSON_NAME = "Петр";
    public static final String NAME_NOT_EXISTS = "Джордж";

    public static final String LAST_NAME_1 = "Пивоварова";
    public static final String LAST_NAME_2 = "Абросимова";
    public static final String LAST_NAME_3 = "Петрова";
    public static final String NEW_PERSON_LAST_NAME = "Петров";
    public static final String LAST_NAME_NOT_EXISTS = "Миллер";

    public static final int SALARY_1 = 45_000;
    public static final int SALARY_2 = 25_000;
    public static final int SALARY_3 = 65_000;
    public static final int NEW_SALARY_1 = 65_000;
    public static final int MIN_SALARY = SALARY_2;
    public static final int MAX_SALARY = SALARY_3;
    public static final int TOTAL_SALARY = SALARY_1 + SALARY_2 + SALARY_3;

    public static final int DEPARTMENT_1 = 1;
    public static final int DEPARTMENT_2 = 2;
    public static final int DEPARTMENT_3 = 3;
    public static final int BAD_DEPARTMENT_ID = 5;

    public static final Employee EMPLOYEE_1 = new Employee(FIRST_NAME_1, LAST_NAME_1, SALARY_1, DEPARTMENT_1);
    public static final Employee EMPLOYEE_2 = new Employee(FIRST_NAME_2, LAST_NAME_2, SALARY_2, DEPARTMENT_2);
    public static final Employee EMPLOYEE_3 = new Employee(FIRST_NAME_3, LAST_NAME_3, SALARY_3, DEPARTMENT_3);
    public static final Employee EMPLOYEE_4 = new Employee("Ivan", "Ivanov", 50_000, DEPARTMENT_1);
    public static final Employee EMPLOYEE_5 = new Employee("Petr", "Petrov", 60_000, DEPARTMENT_2);

    public static final List<Employee> EMPLOYEE_LIST = List.of(
            EMPLOYEE_1,
            EMPLOYEE_2,
            EMPLOYEE_3,
            EMPLOYEE_4,
            EMPLOYEE_5
    );

}

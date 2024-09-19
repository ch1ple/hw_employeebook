package me.ch1ple.hw.employeebook.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import me.ch1ple.hw.employeebook.exception.*;
import me.ch1ple.hw.employeebook.model.Employee;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static me.ch1ple.hw.employeebook.constants.EmployeeConstants.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EmployeeServiceImplTest {

    private final EmployeeService employeeService = new EmployeeServiceImpl(new ValidatorService());

    public static Stream<Arguments> addWrongNameParams() {
        return Stream.of(
                Arguments.of(" Петр "),
                Arguments.of("Петр2"),
                Arguments.of("Петр_П"),
                Arguments.of(" "),
                Arguments.of(" Петр"),
                Arguments.of("Петр!")
        );
    }

    public static Stream<Arguments> addWrongLastNameParams() {
        return Stream.of(
                Arguments.of(" Петров "),
                Arguments.of("Петров2"),
                Arguments.of("Петров_П"),
                Arguments.of(" "),
                Arguments.of(" Петров"),
                Arguments.of("Петров!")
        );
    }

    @BeforeEach
    void setUp() {
        employeeService.addEmployee(FIRST_NAME_1, LAST_NAME_1, SALARY_1, DEPARTMENT_1);
        employeeService.addEmployee(FIRST_NAME_2, LAST_NAME_2, SALARY_2, DEPARTMENT_2);
        employeeService.addEmployee(FIRST_NAME_3, LAST_NAME_3, SALARY_3, DEPARTMENT_3);
    }

    @AfterEach
    void tearDown() {
        employeeService.getEmployees()
                .forEach(employee -> employeeService.removeEmployee(employee.getFirstName(), employee.getLastName()));
    }

    @Test
    @DisplayName("Должен добавить сотрудника")
    void addTest() {
        int beforeAdd = employeeService.getEmployees().size();

        // новый сотрудник
        Employee expected = new Employee(NEW_PERSON_NAME, NEW_PERSON_LAST_NAME, SALARY_2, DEPARTMENT_2);

        // проверки
        assertThat(employeeService.addEmployee(NEW_PERSON_NAME, NEW_PERSON_LAST_NAME, SALARY_2, DEPARTMENT_2))
                .isEqualTo(expected)
                .isIn(employeeService.getEmployees());

        assertThat(employeeService.getEmployees()).hasSize(beforeAdd + 1);

        assertThat(employeeService.findEmployee(NEW_PERSON_NAME, NEW_PERSON_LAST_NAME)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Должен выбросить исключение - неверное имя")
    @MethodSource("addWrongNameParams")
    void addWrongNameTest(String wrongName) {
        assertThatExceptionOfType(InvalidNameException.class)
                .isThrownBy(() -> employeeService.addEmployee(wrongName, NEW_PERSON_LAST_NAME, SALARY_2, DEPARTMENT_2));
    }

    @ParameterizedTest
    @DisplayName("Должен выбросить исключение - неверная фамилия")
    @MethodSource("addWrongLastNameParams")
    void addWrongLastNameTest(String wrongLastName) {
        assertThatExceptionOfType(InvalidSurnameException.class)
                .isThrownBy(() -> employeeService.addEmployee(NEW_PERSON_NAME, wrongLastName, SALARY_2, DEPARTMENT_2));
    }

    @Test
    @DisplayName("Должен выбросить исключение - сотрудник уже добавлен")
    void Should_throw_exception_when_added_employee_exists() {
        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.addEmployee(FIRST_NAME_1, LAST_NAME_1, SALARY_1, DEPARTMENT_1));
    }

    @Test
    @DisplayName("Должен выбросить исключение - достигнут лимит сотрудников")
    void Should_throw_exception_when_employee_limit_is_reached() {
        // заполнение массива сгенерированными сотрудниками
        Stream.iterate(1, integer -> integer + 1)
                .limit(7)
                .map(number -> new Employee(
                        "Ivan" + ((char) ('a' + number)),
                        "Ivanov" + ((char) ('a' + number)),
                        SALARY_2 + number,
                        number
                ))
                .forEach(employee -> employeeService.addEmployee(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary(),
                        employee.getDepartmentId()
                ));

        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.addEmployee(NEW_PERSON_NAME, NEW_PERSON_LAST_NAME, SALARY_1, DEPARTMENT_1));
    }

    @Test
    @DisplayName("Должен удалить сотрудника")
    void removeEmployeeTest() {
        int beforeRemove = employeeService.getEmployees().size();

        // удаляемый сотрудник
        Employee expected = new Employee(FIRST_NAME_1, LAST_NAME_1);

        // проверки
        assertThat(employeeService.removeEmployee(FIRST_NAME_1, LAST_NAME_1))
                .isEqualTo(expected)
                .isNotIn(employeeService.getEmployees());

        assertThat(employeeService.getEmployees()).hasSize(beforeRemove - 1);

        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee(FIRST_NAME_1, LAST_NAME_1));
    }

    @Test
    @DisplayName("Должен выбросить исключение - не найден сотрудник при удалении")
    void Should_throw_not_found_exception_when_removing_not_existing_employee() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.removeEmployee(NAME_NOT_EXISTS, LAST_NAME_NOT_EXISTS));
    }

    @Test
    @DisplayName("Должен найти сотрудника")
    void Should_return_employee_when_finding_existing_employee() {
        int beforeFind = employeeService.getEmployees().size();

        // сотрудник, которого ищем
        Employee expected = new Employee(FIRST_NAME_1, LAST_NAME_1);

        // проверки
        assertThat(employeeService.findEmployee(FIRST_NAME_1, LAST_NAME_1))
                .isEqualTo(expected)
                .isIn(employeeService.getEmployees());

        assertThat(employeeService.getEmployees()).hasSize(beforeFind);
    }

    @Test
    @DisplayName("Должен выбросить исключение - не найден сотрудник, которого нет")
    void Should_throw_not_found_exception_when_finding_not_existing_employee() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee(NAME_NOT_EXISTS, LAST_NAME_NOT_EXISTS));
    }

    @Test
    @DisplayName("Должен вывести всех сотрудников")
    void Should_return_all_employees() {
        assertThat(employeeService.getEmployees())
                .hasSize(3)
                .containsExactlyInAnyOrder(EMPLOYEE_1, EMPLOYEE_2, EMPLOYEE_3);
    }

    @Test
    @DisplayName("Должен вывести всех сотрудников на печать")
    void Should_print_all_employees() {
        assertThat(employeeService.printEmployees())
                .hasSize(3)
                .containsExactlyInAnyOrder(EMPLOYEE_1, EMPLOYEE_2, EMPLOYEE_3);
    }

    @Test
    @DisplayName("Должен проверить массив сотрудников на NOT NULL")
    void Should_return_all_employees_not_null() {
        assertThat(employeeService.getEmployees())
                .isNotNull();
    }

    @Test
    @DisplayName("Должен выбросить исключение при изменении зарплаты для сотрудника, которого нет")
    void Should_throw_not_found_exception_when_setting_salary_for_not_existing_employee() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.setSalary(NAME_NOT_EXISTS, LAST_NAME_NOT_EXISTS, SALARY_2));
    }

    @Test
    @DisplayName("Должен изменить зарплату для существующего сотрудника")
    void Should_return_employee_not_null_when_setting_salary_for_existing_employee() {
        Employee foundEmployee = employeeService.findEmployee(FIRST_NAME_1, LAST_NAME_1);
        int expectedSalary = foundEmployee.getSalary();

        assertThat(employeeService.setSalary(FIRST_NAME_1, LAST_NAME_1, NEW_SALARY_1).getSalary())
                .isNotEqualTo(expectedSalary);
    }

    @Test
    @DisplayName("Должен найти минимальную зарплату")
    void minSalaryTest() {
        assertThat(employeeService.getEmployeeWithMinSalary().getSalary())
                .isEqualTo(MIN_SALARY);
    }

    @Test
    @DisplayName("Должен найти максимальную зарплату")
    void maxSalaryTest() {
        assertThat(employeeService.getEmployeeWithMaxSalary().getSalary())
                .isEqualTo(MAX_SALARY);
    }

    @Test
    @DisplayName("Должен посчитать суммарную зарплату")
    void Should_calc_total_monthly_salary() {
        Number expected = employeeService.calcTotalMonthlySalary();
        assertThat(expected)
                .isEqualTo(TOTAL_SALARY);
    }

}


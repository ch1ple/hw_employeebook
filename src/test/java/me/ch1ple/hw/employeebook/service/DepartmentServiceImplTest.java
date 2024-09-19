package me.ch1ple.hw.employeebook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import me.ch1ple.hw.employeebook.exception.EmployeeNotFoundException;
import me.ch1ple.hw.employeebook.model.Employee;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static me.ch1ple.hw.employeebook.constants.EmployeeConstants.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DepartmentServiceImplTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    public static Stream<Arguments> maxSalaryParams() {
        return Stream.of(
                Arguments.of(1, EMPLOYEE_4),
                Arguments.of(2, EMPLOYEE_5),
                Arguments.of(3, EMPLOYEE_3)
        );
    }

    public static Stream<Arguments> minSalaryParams() {
        return Stream.of(
                Arguments.of(1, EMPLOYEE_1),
                Arguments.of(2, EMPLOYEE_2),
                Arguments.of(3, EMPLOYEE_3)
        );
    }

    public static Stream<Arguments> employeesFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, List.of(EMPLOYEE_1, EMPLOYEE_4)),
                Arguments.of(2, List.of(EMPLOYEE_2, EMPLOYEE_5)),
                Arguments.of(3, Collections.singletonList(EMPLOYEE_3)),
                Arguments.of(BAD_DEPARTMENT_ID, Collections.emptyList())
        );
    }

    public static Stream<Arguments> totalSalaryByDepartmentsParams() {
        return Stream.of(
                Arguments.of(1, 95_000),
                Arguments.of(2, 85_000),
                Arguments.of(3, SALARY_3),
                Arguments.of(BAD_DEPARTMENT_ID, 0)
        );
    }

    @BeforeEach
    void setUp() {
        // когда будет вызван метод getEmployees, то нужно вернуть список сотрудников EMPLOYEE_LIST
        Mockito.when(employeeService.getEmployees()).thenReturn(EMPLOYEE_LIST);
    }

    @ParameterizedTest
    @MethodSource("maxSalaryParams")
    void Should_get_max_salary(int departmentId, Employee expected) {
        assertThat(departmentService.getMaxSalary(departmentId))
                .isEqualTo(expected.getSalary());
    }

    @Test
    void Should_throw_exception_when_getting_max_salary_for_department_not_exists() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.getMaxSalary(BAD_DEPARTMENT_ID));
    }

    @ParameterizedTest
    @MethodSource("minSalaryParams")
    void Should_get_min_salary(int departmentId, Employee expected) {
        assertThat(departmentService.getMinSalary(departmentId))
                .isEqualTo(expected.getSalary());
    }

    @Test
    void Should_throw_exception_when_getting_min_salary_for_department_not_exists() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.getMinSalary(BAD_DEPARTMENT_ID));
    }

    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    void Should_return_all_employees_by_department(int departmentId, List<Employee> expected) {
        assertThat(departmentService.getAllEmployeesByDepartment(departmentId))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void Should_return_collection_when_get_all_employees_called() {
        Map<Integer, List<Employee>> expected = Map.of(
                1, List.of(EMPLOYEE_1, EMPLOYEE_4),
                2, List.of(EMPLOYEE_2, EMPLOYEE_5),
                3, Collections.singletonList(EMPLOYEE_3)
        );
        assertThat(departmentService.employeesGroupedByDepartment())
                .containsExactlyInAnyOrderEntriesOf(expected);
    }

    @ParameterizedTest
    @MethodSource("totalSalaryByDepartmentsParams")
    void Should_calc_total_salary_by_department(int departmentId, int expectedSalary) {
        assertThat(departmentService.calcTotalSalaryByDepartment(departmentId))
                .isEqualTo(expectedSalary);
    }

    @Test
    void Should_return_empty_for_employees_department_not_exists() {
        assertThat(departmentService.calcTotalSalaryByDepartment(BAD_DEPARTMENT_ID))
                .isEqualTo(0);
    }

}

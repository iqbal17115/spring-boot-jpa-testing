package net.java.spring_boot_jpa_testing;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Unit test for save employee
    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void saveEmployeeTest() {
        Employee employee = Employee.builder()
                .firstName("Iqbal")
                .lastName("Hossain")
                .email("iqbal8657@gmail.com")
                .build();

        employeeRepository.save(employee);

        // Ensure employee is saved and ID is assigned
        Assertions.assertThat(employee.getId()).isGreaterThan(0);

        // Verify that the employee is retrievable by ID
        Optional<Employee> savedEmployee = employeeRepository.findById(employee.getId());
        Assertions.assertThat(savedEmployee).isPresent();
        Assertions.assertThat(savedEmployee.get().getFirstName()).isEqualTo("Iqbal");
    }

    @Test
    @Order(2)
    public void getEmployeeTest() {
        Optional<Employee> employeeOptional = employeeRepository.findById(1L);

        Assertions.assertThat(employeeOptional).isPresent();
        Employee employee = employeeOptional.get();
        Assertions.assertThat(employee.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void getListOfEmployeeTest() {
        List<Employee> employees = employeeRepository.findAll();
        Assertions.assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void updateEmployeeTest() {
        Optional<Employee> employeeOptional = employeeRepository.findById(1L);

        Assertions.assertThat(employeeOptional).isPresent();
        Employee employee = employeeOptional.get();

        employee.setEmail("iqbal.cse17@gmail.com");

        Employee employeeUpdates = employeeRepository.save(employee);
        Assertions.assertThat(employeeUpdates.getEmail()).isEqualTo("iqbal.cse17@gmail.com");
    }

    @Test
    @Order(5)
    public void deleteEmployeeTest() {
        Optional<Employee> employeeOptional = employeeRepository.findById(1L);

        Assertions.assertThat(employeeOptional).isPresent();
        Employee employee = employeeOptional.get();

        employeeRepository.delete(employee);

        Optional<Employee> deletedEmployee = employeeRepository.findByEmail("iqbal.cse171@gmail.com");

        Assertions.assertThat(deletedEmployee).isNotPresent();
    }
}

package net.java.spring_boot_jpa_testing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
     Optional<Employee> findByEmail(String email);
}

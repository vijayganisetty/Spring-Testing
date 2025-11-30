package com.springBoot.practice.springTesting.controller;

import com.springBoot.practice.springTesting.DTO.EmployeeDTO;
import com.springBoot.practice.springTesting.entity.Employee;
import com.springBoot.practice.springTesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeControllerTestIT extends AbstractIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .name("kalyan")
                .department("development")
                .mail("kalyan@gmail.com")
                .build();
        employeeDTO = EmployeeDTO.builder()
                .name("kalyan")
                .department("development")
                .mail("kalyan@gmail.com")
                .build();
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_whenEmployeeIsExists() {
        Employee savedEmp = employeeRepository.save(employee);
        webTestClient.get()
                .uri("employee/{id}", savedEmp.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDTO.class)
                .value(emp ->
                        assertThat(emp.getName()).isEqualTo(savedEmp.getName()));
    }

    @Test
    void testGetEmployeeById_whenEmployeeDoesNotExist() {
        webTestClient.get()
                .uri("employee/{id}", 2)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateEmployee_whenMailAlreadyExists() {
        employeeRepository.save(employee);

        webTestClient.post()
                .uri("employee")
                .bodyValue(employee)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateEmployee_whenEmployeeIsNew() {
        webTestClient.post()
                .uri("employee")
                .bodyValue(employee)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(EmployeeDTO.class)
                .value(emp -> assertThat(emp.getName()).isEqualTo("kalyan"));
    }

    @Test
    void testDeleteEmployee_WhenEmployeeExists() {
        Employee emp = employeeRepository.save(employee);
        webTestClient.delete()
                .uri("employee/{id}", emp.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteEmployee_WhenEmployeeNotExists() {
        webTestClient.delete()
                .uri("employee/{id}", employee.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAllEmployees() {
        webTestClient.get()
                .uri("employee")
                .exchange()
                .expectStatus().isOk();
    }
}
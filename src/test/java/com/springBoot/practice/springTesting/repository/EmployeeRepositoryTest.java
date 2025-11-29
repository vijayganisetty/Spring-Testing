package com.springBoot.practice.springTesting.repository;

import com.springBoot.practice.springTesting.TestContainerConfiguration;
import com.springBoot.practice.springTesting.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


//@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .id(1L)
                .name("kalyan")
                .department("development")
                .mail("kalyan@gmail.com")
                .build();
    }

    @Test
    void TestFindByMail_whenMailIsPresent_thenReturnEmployee() {
        //arrange
        employeeRepository.save(employee);
        //act
        Employee emp =  employeeRepository.findByMail(employee.getMail());
        //assert
        Assertions.assertThat(emp).isNotNull();
        Assertions.assertThat(emp.getMail()).isEqualTo(employee.getMail());
    }

    @Test
    void TestFindByMail_whenMailIsNotPresent_thenReturnEmptyEmployee() {

        //Arrange
        String mail = "notPresent@gmail.com";
        //Act
        Employee emp = employeeRepository.findByMail(mail);
        //Assert
        Assertions.assertThat(emp).isNull();
    }
}
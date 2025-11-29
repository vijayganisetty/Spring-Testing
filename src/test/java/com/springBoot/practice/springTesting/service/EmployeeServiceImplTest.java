package com.springBoot.practice.springTesting.service;

import com.springBoot.practice.springTesting.DTO.EmployeeDTO;
import com.springBoot.practice.springTesting.entity.Employee;
import com.springBoot.practice.springTesting.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO mockEmployeeDTO(){
        return modelMapper.map(mockEmployee(),EmployeeDTO.class);
    }

    private Employee mockEmployee(){
        return Employee.builder()
                .id(1L)
                .name("kalyan")
                .department("development")
                .mail("kalyan@gmail.com")
                .build();
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_thenReturnEmployee(){

        Long id = 1L;
        //Assign
        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee()));

        //Act
        EmployeeDTO emp = employeeService.getEmployeeById(1L);

        //Assert
        assertThat(emp).isNotNull();
        assertThat(emp.getName()).isEqualTo("kalyan");
        verify(employeeRepository, only()).findById(1L);
    }

    @Test
    void testCreateEmployee_WhenEmployeeIsValid_thenCreateEmployee(){
        when(employeeRepository.findByMail(anyString())).thenReturn(null);
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee());
        EmployeeDTO result = employeeService.createEmployee(mockEmployeeDTO());

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        Employee employee = captor.getValue();
        System.out.println(employee.getMail());

        assertThat(result).isNotNull();
        assertThat(result.getMail()).isEqualTo("kalyan@gmail.com");
    }
}
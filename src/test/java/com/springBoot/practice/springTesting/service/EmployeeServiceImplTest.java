package com.springBoot.practice.springTesting.service;

import com.springBoot.practice.springTesting.DTO.EmployeeDTO;
import com.springBoot.practice.springTesting.entity.Employee;
import com.springBoot.practice.springTesting.exception.ResourceNotFoundException;
import com.springBoot.practice.springTesting.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO mockEmployeeDTO() {
        return modelMapper.map(mockEmployee(), EmployeeDTO.class);
    }

    private Employee mockEmployee() {
        return Employee.builder()
                .id(1L)
                .name("kalyan")
                .department("development")
                .mail("kalyan@gmail.com")
                .build();
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_thenReturnEmployee() {

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
    void testGetEmployeeById_WhenEmployeeIdIsNotPresent_thenThrowException() {

        Long id = 2L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> employeeService.getEmployeeById(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Employee with id 2");

    }

    @Test
    void testCreateEmployee_WhenEmployeeIsValid_thenCreateEmployee() {
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

    @Test
    void testCreateEmployee_WhenEmployeeIsAlreadyExist_thenThrowException() {
        when(employeeRepository.findByMail(anyString())).thenReturn(mockEmployee());
        assertThatThrownBy(() -> employeeService.createEmployee(mockEmployeeDTO()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("employee with mail already exists");
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(List.of(mockEmployee()));
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees();
        assertThat(employeeDTOList).isNotNull();
        assertThat(employeeDTOList.size()).isGreaterThan(0);
    }


    @Test
    void testDeleteEmployeeById_EmployeeExists() {
        when(employeeRepository.findById(any())).thenReturn(Optional.of(mockEmployee()));
        EmployeeDTO employeeDTO = employeeService.deleteEmployeeById(1L);
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getName()).isEqualTo("kalyan");
    }

    @Test
    void testDeleteEmployeeById_EmployeeDoesNotExists_thenThrowException() {
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> employeeService.deleteEmployeeById(1L)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Employee with id 1");
    }

    @Test
    void testGetEmployeeByMail_MailExists() {
        //stubbing
        when(employeeRepository.findByMail(anyString())).thenReturn(mockEmployee());
        EmployeeDTO employee = employeeService.getEmployeeByMail("kalyan@gmail.com");
        assertThat(employee).isNotNull();
        assertThat(employee.getName()).isEqualTo("kalyan");
    }

    @Test
    void testGetEmployeeByMail_MailNotFound() {
        //stubbing
        when(employeeRepository.findByMail(anyString())).thenReturn(null);
        assertThatThrownBy(() -> employeeService.getEmployeeByMail(anyString())).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No Employee with mail ");

    }
}
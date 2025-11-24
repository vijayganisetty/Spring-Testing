package com.springBoot.practice.springTesting.service;


import com.springBoot.practice.springTesting.DTO.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO getEmployeeById(long id);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO deleteEmployeeById(long id);

    EmployeeDTO getEmployeeByMail(String mail);
}

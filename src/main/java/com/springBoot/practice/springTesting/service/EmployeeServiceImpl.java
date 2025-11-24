package com.springBoot.practice.springTesting.service;

import com.springBoot.practice.springTesting.DTO.EmployeeDTO;
import com.springBoot.practice.springTesting.entity.Employee;
import com.springBoot.practice.springTesting.exception.ResourceNotFoundException;
import com.springBoot.practice.springTesting.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{


    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public EmployeeDTO getEmployeeById(long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No Employee with id "+ id)
        );
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
       List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
               .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
               .toList();
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
          Employee employee = modelMapper.map(employeeDTO,Employee.class);
          Employee newEmp = employeeRepository.save(employee);
          return modelMapper.map(newEmp, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO deleteEmployeeById(long id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No Employee with id "+ id)
        );
        if(employee!= null){
            employeeRepository.delete(employee);
        }
        return modelMapper.map(employee,EmployeeDTO.class);
    }

    public EmployeeDTO getEmployeeByMail(String mail) {
        Employee employee = employeeRepository.findByMail(mail);
        if(employee == null){
            throw new ResourceNotFoundException("No Employee with mail "+mail);
        }
        return modelMapper.map(employee, EmployeeDTO.class);
    }
}

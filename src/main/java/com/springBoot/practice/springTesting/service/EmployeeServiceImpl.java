package com.springBoot.practice.springTesting.service;

import com.springBoot.practice.springTesting.DTO.EmployeeDTO;
import com.springBoot.practice.springTesting.entity.Employee;
import com.springBoot.practice.springTesting.exception.ResourceNotFoundException;
import com.springBoot.practice.springTesting.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{


    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public EmployeeDTO getEmployeeById(long id) {
        log.info("Fetching employee wth id {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No Employee with id "+ id)
        );
        log.info("Fetchecd Employee with id {}", id);
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
          Employee employee = employeeRepository.findByMail(employeeDTO.getMail());
          if(employee==null) {
              Employee emp = modelMapper.map(employeeDTO, Employee.class);
              Employee newEmp = employeeRepository.save(emp);
              return modelMapper.map(newEmp, EmployeeDTO.class);
          }
          return new EmployeeDTO();
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

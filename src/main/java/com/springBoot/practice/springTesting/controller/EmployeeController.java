package com.springBoot.practice.springTesting.controller;


import com.springBoot.practice.springTesting.DTO.EmployeeDTO;
import com.springBoot.practice.springTesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable long id){
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<EmployeeDTO> employees  = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO employee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployeeById(@PathVariable long id){
        EmployeeDTO employee = employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.GONE);
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<EmployeeDTO> getEmployeeByMail(@PathVariable String mail){
        EmployeeDTO employeeDTO = employeeService.getEmployeeByMail(mail);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }


}

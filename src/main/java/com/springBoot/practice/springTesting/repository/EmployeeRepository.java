package com.springBoot.practice.springTesting.repository;


import com.springBoot.practice.springTesting.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
           Employee findByMail(String mail);
}

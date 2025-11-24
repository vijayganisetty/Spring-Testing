package com.springBoot.practice.springTesting.DTO;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private String name;
    private String department;
    private String mail;
}

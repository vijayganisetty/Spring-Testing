package com.springBoot.practice.springTesting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String department;
    @Column(unique = true)
    private String mail;
}

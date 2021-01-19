package com.basiauth.basiauth.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    @Getter
    @Setter
    private Long employeeId;

    @Column(name="employee_name")
    @Getter
    @Setter
    private String employeeName;

    @Column(name="password")
    @Getter
    @Setter
    private String password;

    @Column(name="salary")
    @Getter
    @Setter
    private long salary;

    @Column(name="department")
    @Getter
    @Setter
    private String department;
}

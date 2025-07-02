package com.EmployeePayRollSystem.CapStoneProject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Department {
    @Id
    private long id;
    
    private String name;
    private String location;
    
    @OneToMany(mappedBy = "department")
    private List<Employee> listOfEmployees;



}

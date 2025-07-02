package com.EmployeePayRollSystem.CapStoneProject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Department {
    @Id
    private long id;
    
    private String name;
    private String location;
    
    @OneToMany(mappedBy = "department")
    private List<Employee> listOfEmployees;

    public Department() {
    }

    public Department(long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Department(String name, String location, List<Employee> listOfEmployees) {
        this.name = name;
        this.location = location;
        this.listOfEmployees = listOfEmployees;
    }

    public Department(long id, String name, String location, List<Employee> listOfEmployees) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.listOfEmployees = listOfEmployees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employee> getListOfEmployees() {
        return listOfEmployees;
    }

    public void setListOfEmployees(List<Employee> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }
}

package com.EmployeePayRollSystem.CapStoneProject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Department {
    @Id
    private long id;

    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Employee> listOfEmployees;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department(long id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    public Department(String departmentName, List<Employee> listOfEmployees) {
        this.departmentName = departmentName;
        this.listOfEmployees = listOfEmployees;
    }

    public Department(long id, String departmentName, List<Employee> listOfEmployees) {
        this.id = id;
        this.departmentName = departmentName;
        this.listOfEmployees = listOfEmployees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getListOfEmployees() {
        return listOfEmployees;
    }

    public void setListOfEmployees(List<Employee> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }
}

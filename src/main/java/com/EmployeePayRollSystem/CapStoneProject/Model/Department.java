package com.EmployeePayRollSystem.CapStoneProject.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @OneToMany(mappedBy = "department", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "department-employee")
    private List<Employee> listOfEmployees;

    public  Department(){}
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

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", listOfEmployees=" + listOfEmployees +
                '}';
    }
}

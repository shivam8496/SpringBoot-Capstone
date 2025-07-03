package com.EmployeePayRollSystem.CapStoneProject.Model;


import jakarta.persistence.*;


@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private  String password;
    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "bank_account_number")
    private Bank bank;

    public Employee() {
    }

    public Employee(String name, String email, String password, double salary, Department department) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.department = department;
    }

    public Employee(String name, String email, String password, double salary, Bank bank) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.bank = bank;
    }

    public Employee(String name, String email, String password, double salary, Department department, Bank bank) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.department = department;
        this.bank = bank;
    }

    public Employee(long id, String name, String email, String password, double salary, Department department, Bank bank) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.department = department;
        this.bank = bank;
    }

    public Employee(String name, String email, String password, double salary) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.salary = salary;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salary=" + salary +
                ", department=" + (department != null ? department.getDepartmentName() : "null") +
                ", bank=" + (bank != null ? bank.getBankName() : "null") +
                '}';
    }
}

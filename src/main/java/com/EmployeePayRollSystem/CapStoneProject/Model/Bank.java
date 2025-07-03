package com.EmployeePayRollSystem.CapStoneProject.Model;


import jakarta.persistence.*;



import java.util.List;


@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountNumber;


    private String bankName;
    private String ifscCode;

    @OneToMany(mappedBy = "bank")
    List<Employee> employeeList;




    public Bank(String bankName, String ifscCode) {
        this.bankName = bankName;
        this.ifscCode = ifscCode;
    }

    public Bank(String bankName, String ifscCode, List<Employee> employeeList) {
        this.bankName = bankName;
        this.ifscCode = ifscCode;
        this.employeeList = employeeList;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "accountNumber=" + accountNumber +
                ", bankName='" + bankName + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }
}

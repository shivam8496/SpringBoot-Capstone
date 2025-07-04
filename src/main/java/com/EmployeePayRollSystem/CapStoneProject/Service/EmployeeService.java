package com.EmployeePayRollSystem.CapStoneProject.Service;

import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private BCryptPasswordEncoder encoder;

    public EmployeeService() {
        this.encoder= new BCryptPasswordEncoder();
    }

    @Autowired
    EmployeeRepository employeeRepository;
    public List<Employee> findByName(String name){
        List<Employee> emps = employeeRepository.findByName(name)
                .orElseThrow(()->  new RuntimeException("Employee with name ==>"+name+" Not Find"))
                .stream()
                .toList();

        return emps;

    }


    public Employee findByEmail(String email){
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);

        if (optionalEmployee.isEmpty()) {
            System.out.println("User with email " + email + " Not found");
            return null; // Return null if no employee is found
        } else {
            Employee emp = optionalEmployee.get();
            System.out.println("Found by Email " + emp);
            return emp;
        }
    }

    public Employee updateEmployee(Employee employee) {
        if (!employeeRepository.existsById(employee.getId())) {
            throw new IllegalArgumentException("Employee with ID " + employee.getId() + " does not exist.");
        }


        return employeeRepository.save(employee);
    }

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> addEmployees(List<Employee> employees){

        return employeeRepository.saveAll(employees);
    }

    public Employee findById(long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()) {
            System.out.println("User with email " + id + " Not found");
            return null; // Return null if no employee is found
        } else {
            Employee emp = optionalEmployee.get();
            System.out.println("Found by Email " + emp);
            return emp;
        }

    }

    public void  deleteEmployeeById(long id){
        Optional<Employee> empOptional = employeeRepository.findById(id);
        if (empOptional.isPresent()) {
            Employee emp = empOptional.get();

            // Break the bidirectional relationships before delete
            if (emp.getBank() != null) {
                emp.getBank().getEmployeeList().remove(emp);
                emp.setBank(null);
            }

            if (emp.getDepartment() != null) {
                emp.getDepartment().getListOfEmployees().remove(emp);
                emp.setDepartment(null);
            }

            employeeRepository.delete(emp);
        }
    }

    public List<Employee> showAllEmployees(){
        return employeeRepository.findAll();
    }




}

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
        List<Employee> emps = employeeRepository.findByName(email)
                .orElseThrow(()->  new RuntimeException("Employee with name ==>"+email+" Not Found"))
                .stream()
                .toList();
        if(emps.size()>1) throw  new RuntimeException("Found more than One Employee with same Email Id");
        return emps.getFirst();
    }

    public Employee addEmployee(Employee employee){
        employee.setPassword(encoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public List<Employee> addEmployees(List<Employee> employees){
        for(Employee employee:employees){
            employee.setPassword(encoder.encode(employee.getPassword()));
        }
        return employeeRepository.saveAll(employees);
    }

    public List<Employee> showAllEmployees(){
        return employeeRepository.findAll();
    }




}

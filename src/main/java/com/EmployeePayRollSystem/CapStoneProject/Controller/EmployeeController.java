package com.EmployeePayRollSystem.CapStoneProject.Controller;


import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @GetMapping("/HR")
    public List<Employee> showEmployeeToHR(){
        List<Employee> employees = employeeService.showAllEmployees();

        System.out.println(employees);
        return employees;
    }
    @GetMapping("/admin")
    public List<Employee> showEmployeeToAdmin(){
        List<Employee> employees = employeeService.showAllEmployees();
        System.out.println(employees);
        return employees;
    }
}

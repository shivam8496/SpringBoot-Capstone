package com.EmployeePayRollSystem.CapStoneProject.Controller;


import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/")
    public String loginOrSignup(){
        return "Login Or SignUp Page";
    }

    @RequestMapping("/login")
    public String login(){
        return "This is Login Page";
    }

    @RequestMapping("/signup")
    public Employee signup(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }
}

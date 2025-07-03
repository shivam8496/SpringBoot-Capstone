package com.EmployeePayRollSystem.CapStoneProject.Controller;


import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
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
        return "Login";
    }


    @RequestMapping("/signup")
    public String signup(@RequestBody Employee employee){
        return "Signup";
    }
}

package com.EmployeePayRollSystem.CapStoneProject.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @RequestMapping("/")
    public String loginOrSignup(){
        return "Login Or SignUp Page";
    }

    @RequestMapping("/login")
    public String login(){
        return "This is Login Page";
    }

    @RequestMapping("/signup")
    public String signup(){
        return "This is signup Page";
    }
}

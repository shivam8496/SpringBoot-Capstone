package com.EmployeePayRollSystem.CapStoneProject.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankController {

    @RequestMapping("/")
    public String dashboard(){
        return "DashBoard for the BANK";
    }


}

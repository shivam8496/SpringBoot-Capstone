package com.EmployeePayRollSystem.CapStoneProject.Service;

import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Model.MyEmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyEmployeeDetailService  implements UserDetailsService {
    @Autowired
    EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeService.findByEmail(email);
        if(employee==null){
            System.out.println("User with email " + email + " Not found");
            throw new UsernameNotFoundException("User Not Found Exception");
        }
        return new MyEmployeeDetails(employee);
    }
}

package com.EmployeePayRollSystem.CapStoneProject.Model;

import com.EmployeePayRollSystem.CapStoneProject.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MyEmployeeDetails implements UserDetails {


    private Employee employee;
    public MyEmployeeDetails(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = employee.getDepartment().getDepartmentName().toUpperCase();
        return List.of(new SimpleGrantedAuthority("ROLE_"+role));
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getEmail();
    }
    public Long getEmployeeId(){
        return employee.getId();
    }
}

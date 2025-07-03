package com.EmployeePayRollSystem.CapStoneProject.Service;

import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import com.EmployeePayRollSystem.CapStoneProject.Model.Department;
import com.EmployeePayRollSystem.CapStoneProject.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> departmentList(){
        return departmentRepository.findAll();
    }

    public Department findDepartmentById(long id){
     return departmentRepository.findById(id).orElse(null);
    }
}

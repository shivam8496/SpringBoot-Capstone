package com.EmployeePayRollSystem.CapStoneProject.Service;

import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import com.EmployeePayRollSystem.CapStoneProject.Model.Department;
import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Repository.DepartmentRepository;
import com.EmployeePayRollSystem.CapStoneProject.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Department> departmentList(){
        return departmentRepository.findAll();
    }

    public void deleteDepartmentById(long id) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));

            // Get all employees in the department and set their department to null
            List<Employee> employees = department.getListOfEmployees();
            if (employees != null) {
                for (Employee employee : employees) {
                    employee.setDepartment(null);
                }
                employeeRepository.saveAll(employees);  // Persist updated employees
            }

            // Now delete the department safely
            departmentRepository.deleteById(id);

            System.out.println("Department deleted successfully and employees disassociated.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error while deleting department: " + e.getMessage());
            throw new RuntimeException("Failed to delete department. Please try again later.", e);
        }
    }


    public Department findDepartmentById(long id){
     return departmentRepository.findById(id).orElse(null);
    }
    public Department saveDepartment(Department department){
        return departmentRepository.save(department);
    }

    public Department updateEmployee(Department dep) {
        if (!departmentRepository.existsById(dep.getId())) {
            throw new IllegalArgumentException("Department with id  " + dep.getDepartmentName() + " does not exist.");
        }


        return departmentRepository.save(dep);
    }
}

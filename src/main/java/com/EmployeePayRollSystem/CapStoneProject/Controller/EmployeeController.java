package com.EmployeePayRollSystem.CapStoneProject.Controller;


import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import com.EmployeePayRollSystem.CapStoneProject.Model.Department;
import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Service.BankService;
import com.EmployeePayRollSystem.CapStoneProject.Service.DepartmentService;
import com.EmployeePayRollSystem.CapStoneProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public DepartmentService departmentService;

    @Autowired
    public BankService bankService;

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
    @GetMapping("/HR")
    public List<Employee> showEmployeeToHR(){
        List<Employee> employees = employeeService.showAllEmployees();
        System.out.println(employees);
        return employees;
    }
    @GetMapping("/addemployee")   // localhost:8080/api/employee/admin/addemployee
    public String employeeForm(Model model){
        List<Bank> banks = bankService.bankList();
        List<Department> departments = departmentService.departmentList();
        model.addAttribute("banks",banks);
        model.addAttribute("departments",departments);
        return "view/addEmployee.html";
    }

    @PostMapping("/addemployee/{bankid}:{departmentId}")
    public ResponseEntity<?> addEmployee(@RequestBody Employee  employeeReq, @PathVariable("bankid") long bankid, @PathVariable("departmentId") long departmentId){
        employeeReq.setPassword(encoder.encode(employeeReq.getPassword()));
        Bank bank = bankService.findBankById(bankid);
        Department department = departmentService.findDepartmentById(departmentId);
        Employee employee1 = new Employee(
                employeeReq.getName(),
                employeeReq.getEmail(),
                employeeReq.getPassword(),
                employeeReq.getSalary(),
                department,
                bank
        );
        bank.setEmployeeList(List.of(employee1));
        department.setListOfEmployees(List.of(employee1));
        System.out.println("Employee Saved ==> "+employeeService.addEmployee(employee1));
        return ResponseEntity.ok("Employee added successfully");
    }


    @GetMapping("/")
    public String showEmployee(Model model){
        List<Employee> employees = employeeService.showAllEmployees();
        model.addAttribute("employees",employees);
        System.out.println(employees);
        return "view/empView.html";
    }
}

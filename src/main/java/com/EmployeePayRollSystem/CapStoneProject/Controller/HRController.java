package com.EmployeePayRollSystem.CapStoneProject.Controller;


import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import com.EmployeePayRollSystem.CapStoneProject.Model.Department;
import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Service.BankService;
import com.EmployeePayRollSystem.CapStoneProject.Service.DepartmentService;
import com.EmployeePayRollSystem.CapStoneProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasRole('HR') or hasRole('ADMIN')")
@RequestMapping("/hr")
public class HRController {

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public DepartmentService departmentService;

    @Autowired
    public BankService bankService;

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(5);

    @GetMapping("/")
    public String showEmployee(Model model){
        List<Employee> employees = employeeService.showAllEmployees();
        model.addAttribute("employees",employees);
        System.out.println(employees);
        return "view/empViewHr.html";
    }

    @PostMapping("/editemployee/{bankId}/{departmentId}/{empId}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("bankId") long bankid,
            @PathVariable("departmentId") long departmentId,
            @PathVariable("empId") long empId,
            @RequestBody Map<String, Object> payload) {

        Bank bank = bankService.findBankById(bankid);
        Department department = departmentService.findDepartmentById(departmentId);
        Employee employee = employeeService.findById(empId);

        // Set new bank and department
        employee.setBank(bank);
        employee.setDepartment(department);

        // Extract and update fields
        String name = (String) payload.get("name");
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");
        double salary = Double.parseDouble(payload.get("salary").toString());

        employee.setName(name);
        employee.setEmail(email);
        employee.setSalary(salary);
        System.out.println("Employee successfully updated ==> " + employeeService.updateEmployee(employee));

        return ResponseEntity.ok("Employee updated successfully");
    }


    @GetMapping("/editemployee/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        List<Department> departments = departmentService.departmentList();
        List<Bank> banks = bankService.bankList();

        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);
        model.addAttribute("banks", banks);
        return "view/editEmployee"; // Thymeleaf template
    }

    @GetMapping("/addemployee")
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
}

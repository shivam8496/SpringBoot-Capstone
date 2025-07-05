package com.EmployeePayRollSystem.CapStoneProject.Controller;


import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import com.EmployeePayRollSystem.CapStoneProject.Model.Department;
import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Service.BankService;
import com.EmployeePayRollSystem.CapStoneProject.Service.DepartmentService;
import com.EmployeePayRollSystem.CapStoneProject.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    BankService bankService;

    @GetMapping("/")
    public String login(){
        return "view/login";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "view/signup.html";
    }

    @PostMapping("/signup")
    public String signUpPost(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam double salary,
            @RequestParam Long departmentId,
            @RequestParam Long bankAccountNumber,
            Model model
    ) {
        try {
            // 1. Fetch Department & Bank
            Department department = departmentService.findDepartmentById(departmentId);
            Bank bank = bankService.findBankById(bankAccountNumber); // method needed in BankService

            if (department == null || bank == null) {
                model.addAttribute("error", "Invalid department or bank selected.");
                return "view/signup"; // return the signup page again with error
            }

            // 2. Create Employee
            Employee employee = new Employee();
            employee.setName(name);
            employee.setEmail(email);
            employee.setSalary(salary);
            employee.setDepartment(department);
            employee.setBank(bank);

            // 3. Encrypt password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
            employee.setPassword(encoder.encode(password));

            // 4. Save employee
            employeeService.addEmployee(employee);

            // 5. Redirect or show success
            model.addAttribute("success", "Employee registered successfully!");
            return "view/login"; // or "view/signup" with success message

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Something went wrong during signup.");
            return "view/signup";
        }
    }




}

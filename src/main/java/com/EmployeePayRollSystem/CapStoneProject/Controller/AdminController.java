package com.EmployeePayRollSystem.CapStoneProject.Controller;


import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import com.EmployeePayRollSystem.CapStoneProject.Model.Department;
import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Service.BankService;
import com.EmployeePayRollSystem.CapStoneProject.Service.DepartmentService;
import com.EmployeePayRollSystem.CapStoneProject.Service.EmployeeService;
import com.itextpdf.text.pdf.qrcode.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;


@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public DepartmentService departmentService;

    @Autowired
    public BankService bankService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



    @GetMapping("/")

    public String showEmployee(Model model){
        List<Employee> employees = employeeService.showAllEmployees();
        model.addAttribute("employees",employees);
        System.out.println(employees);
        return "view/empViewAdmin.html";
    }



    @GetMapping("/addemployee")
    public String employeeForm(Model model){
        List<Bank> banks = bankService.bankList();
        List<Department> departments = departmentService.departmentList();
        model.addAttribute("banks",banks);
        model.addAttribute("departments",departments);
        return "view/addEmployee.html";
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

        System.out.println("Employee Saved ==> "+employeeService.addEmployee(employee1));
        return ResponseEntity.ok("Employee added successfully");
    }

    @PostMapping("/deleteemployee/{id}")
    public String deleteEmployee(@PathVariable("id") long id){
        System.out.println("============ Before Deleting Employee ID: " + id + " ================================");
        employeeService.deleteEmployeeById(id);
        System.out.println("============ After Deleting Employee ID: " + id + " ================================");
        return "redirect:/admin/";
    }
    @PostMapping("/deletebank/{id}")
    public String deleteBank(@PathVariable("id") long id){
        Bank bank = bankService.findBankById(id);
        bankService.deleteBankById(id);
        return "redirect:/admin/";
    }

    @GetMapping("/viewbanks")
    public String viewBanks(Model model){
        model.addAttribute("banks",bankService.bankList());
        return "view/viewBanks";
    }
    @GetMapping("/viewdepartments")
    public String viewDepartment(Model model){
        model.addAttribute("departments",departmentService.departmentList());
        return "view/viewDepartments";
    }

    @GetMapping("/editdepartmnet/{id}")
    public String editDepartment(Model model,@PathVariable long id){
        Department department = departmentService.findDepartmentById(id);
        model.addAttribute("department",department);
        return "view/editDepartment";
    }

    @GetMapping("/editbank/{id}")
    public String editBank(Model model,@PathVariable long id){
        Bank bank = bankService.findBankById(id);
        model.addAttribute("bank",bank);
        return "view/editBank";
    }

    @PostMapping("/editbank/{id}")
public ResponseEntity<String> editPostBank(@PathVariable("id") long id,
                                           @RequestBody Map<String, String> data) {
    try {
        // Fetch existing bank by ID
        Bank bank = bankService.findBankById(id);
        if (bank == null) {
            return ResponseEntity.status(404).body("Bank not found");
        }

        // Update bank details from request data
        bank.setBankName(data.get("bankname"));
        bank.setIfscCode(data.get("ifsccode"));

        // Persist the updated bank
        bankService.saveBank(bank);  // ensure this method saves or updates in DB

        return ResponseEntity.ok("Bank updated successfully");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Failed to update bank: " + e.getMessage());
    }
}

    @PostMapping("/editdepartment/{id}")
    public ResponseEntity<String> editPostDepartment(@PathVariable("id") long id,
                                                     @RequestBody Map<String, String> data) {
        try {
            // Fetch existing department by ID
            Department department = departmentService.findDepartmentById(id);
            if (department == null) {
                return ResponseEntity.status(404).body("Department not found");
            }

            // Update department name from request data
            department.setDepartmentName(data.get("departmentname"));

            // Persist the updated department
            departmentService.saveDepartment(department);  // ensure this saves to DB

            return ResponseEntity.ok("Department updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update department: " + e.getMessage());
        }
    }

    @PostMapping("/deletedepartment/{id}")
    public String deleteDepartment(@PathVariable("id") long id){
        Department department =departmentService.findDepartmentById(id);
        departmentService.deleteDepartmentById(id);
        return "redirect:/admin/";
    }

    @GetMapping("/adddepartment")
    public String showAddDepartmentForm(Model model) {
        return "view/addDepartment"; // adjust the path if you store it elsewhere
    }
    @GetMapping("/addbank")
    public String addBank(){
        return "view/addBank";
    }
    @PostMapping("/addbank")
    @ResponseBody
    public ResponseEntity<String> addBankPost(@RequestBody Map<String, String> data) {
        try {
            String bankName = data.get("bankName");
            String ifscCode = data.get("ifscCode");

            if (bankName == null || ifscCode == null || bankName.isBlank() || ifscCode.isBlank()) {
                return ResponseEntity.badRequest().body("Bank Name and IFSC Code are required");
            }

            Bank bank = new Bank(bankName,ifscCode);

            bankService.saveBank(bank); // assumes saveBank persists to DB

            return ResponseEntity.ok("Bank added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to add bank: " + e.getMessage());
        }
    }


    @PostMapping("/adddepartment")
    @ResponseBody
    public ResponseEntity<String> addDepartment(@RequestBody Map<String, String> data) {
        try {
            String departmentName = data.get("departmentName");
            if (departmentName == null || departmentName.isBlank()) {
                return ResponseEntity.badRequest().body("Department name is required");
            }

            Department department = new Department(departmentName);
            departmentService.saveDepartment(department);

            return ResponseEntity.ok("Department added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to add department.");
        }
    }




}

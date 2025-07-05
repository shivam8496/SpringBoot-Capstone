package com.EmployeePayRollSystem.CapStoneProject.Controller;


import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import com.EmployeePayRollSystem.CapStoneProject.Model.Department;
import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Model.MyEmployeeDetails;
import com.EmployeePayRollSystem.CapStoneProject.Service.BankService;
import com.EmployeePayRollSystem.CapStoneProject.Service.DepartmentService;
import com.EmployeePayRollSystem.CapStoneProject.Service.EmployeeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public DepartmentService departmentService;

    @Autowired
    public BankService bankService;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public String showDashboard(Model model, Authentication authentication) {
        MyEmployeeDetails userDetails = (MyEmployeeDetails) authentication.getPrincipal();
        long employeeId = userDetails.getEmployeeId();

        Employee employee = employeeService.findById(employeeId); // if needed
        List<Double> salaryDetails = bankService.calculateTaxAndNetSalary(employee);
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("tax",salaryDetails.get(0));
        model.addAttribute("taxpercentage",salaryDetails.get(1));
        model.addAttribute("netSalary",salaryDetails.get(salaryDetails.size()-1));
        model.addAttribute("banks",bankService.bankList());
        return "view/employeeDashboard.html";
    }


    @GetMapping("/getbanks")
    @ResponseBody
    public List<Bank> getBank(){
        return bankService.bankList();
    }

    @GetMapping("/download-pdf/{id}")
    public void downloadPdf(@PathVariable("id") Long employeeId, HttpServletResponse response) {
        try {
            Employee employee = employeeService.findById(employeeId);
            List<Double> salaryDetails = bankService.calculateTaxAndNetSalary(employee);

            // Set response headers
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=salary_slip.pdf");

            // Create PDF
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            document.add(new Paragraph("Employee Salary Slip"));
            document.add(new Paragraph("================================"));
            document.add(new Paragraph("Employee Name: " + employee.getName()));
            document.add(new Paragraph("Employee ID: " + employee.getId()));
            document.add(new Paragraph("Email: " + employee.getEmail()));
            document.add(new Paragraph("Department: " + (employee.getDepartment() != null ? employee.getDepartment().getDepartmentName() : "N/A")));
            document.add(new Paragraph("Bank: " + employee.getBank().getBankName()));
            document.add(new Paragraph("IFSC: " + employee.getBank().getIfscCode()));
            document.add(new Paragraph("Account Number: " + employee.getBank().getAccountNumber()));
            document.add(new Paragraph("--------------------------------"));
            document.add(new Paragraph("Basic Salary: ₹" + employee.getSalary()));
            document.add(new Paragraph("Tax Applicable: ₹" + salaryDetails.get(0)));
            document.add(new Paragraph("Tax Percentage: " + salaryDetails.get(1) + "%"));
            document.add(new Paragraph("Salary After Tax: ₹" + salaryDetails.get(2)));
            document.add(new Paragraph("Monthly Net Salary: ₹" + (salaryDetails.get(2) / 12)));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate PDF");
        }
    }


    @PostMapping("/updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable("id") Long id,
                                 @RequestBody Map<String, String> data,
                                 RedirectAttributes redirectAttributes) {
        try {
            Employee employee = employeeService.findById(id);
            employee.setPassword(encoder.encode(data.get("password")));
            System.out.println(employee);
            System.out.println("================= password Updatd=====================");
            employeeService.addEmployee(employee);
            return ResponseEntity.ok("Password updated successfully");
            // redirectAttributes.addFlashAttribute("message", "Password updated successfully!");
            // redirectAttributes.addFlashAttribute("alertType", "success");
        } catch (Exception e) {
            System.out.println("==================Exception Occured====================");
            System.out.println(e);
            return ResponseEntity.status(500).body("Failed to update password");
        }

    }

    @PostMapping("/updateBank/{id}")
    public String updateBank(@PathVariable("id") Long employeeId,
                             @RequestParam("bankId") long bankAccountNumber,
                             RedirectAttributes redirectAttributes) {
        try {
            // Fetch employee and bank
            Employee employee = employeeService.findById(employeeId);
            Bank bank = bankService.findBankById(bankAccountNumber);

            // Update bank
            employee.setBank(bank);
            employeeService.addEmployee(employee); // Make sure your service persists the change
            if(!bank.getEmployeeList().contains(employee)){
                List<Employee> employees = bank.getEmployeeList();
                employees.add(employee);
                bank.setEmployeeList(employees);
            }
            redirectAttributes.addFlashAttribute("message", "Bank updated successfully!");
            redirectAttributes.addFlashAttribute("alertType", "success");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to update bank.");
            redirectAttributes.addFlashAttribute("alertType", "danger");
        }

        // Redirect to the dashboard page after update
        return "redirect:/employee/";

    }

    @GetMapping("/getdepartments")
    @ResponseBody
    public List<Department> getDepartments(){
        return departmentService.departmentList();
    }






}

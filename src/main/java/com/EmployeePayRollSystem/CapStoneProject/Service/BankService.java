package com.EmployeePayRollSystem.CapStoneProject.Service;

import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import com.EmployeePayRollSystem.CapStoneProject.Repository.BankRepository;
import com.EmployeePayRollSystem.CapStoneProject.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    private final List<Double> TAX_SLAB = List.of(0D,5D,10D,15D,20D,25D,25D,30D);

    @Autowired
    EmployeeService employeeService;

    public List<Double> calculateTaxAndNetSalary(Employee employee) {
        double salary = employee.getSalary();

        // Income slab limits in rupees
        double[] incomeLimits = {400000, 800000, 1200000, 1600000, 2000000, 2400000};
        List<Double> TAX_SLAB = List.of(0D, 5D, 10D, 15D, 20D, 25D, 30D); // Include 0% for 0–4L

        double tax = 0.0;
        double previousLimit = 0.0;
        double taxPercentage = 0.0;

        for (int i = 0; i < incomeLimits.length; i++) {
            if (salary > incomeLimits[i]) {
                tax += (incomeLimits[i] - previousLimit) * (TAX_SLAB.get(i) / 100.0);
                previousLimit = incomeLimits[i];
            } else {
                tax += (salary - previousLimit) * (TAX_SLAB.get(i) / 100.0);
                taxPercentage = TAX_SLAB.get(i); // Final applied slab
                double netSalary = salary - tax;
                return List.of(tax, taxPercentage, netSalary);
            }
        }

        // If salary > 24L, apply highest slab
        tax += (salary - previousLimit) * (TAX_SLAB.get(TAX_SLAB.size() - 1) / 100.0);
        taxPercentage = TAX_SLAB.get(TAX_SLAB.size() - 1);
        double netSalary = salary - tax;

        return List.of(tax, taxPercentage, netSalary);
    }


    public List<Double> calculateTaxAndNetSalary(long empId) {
        Employee employee = employeeService.findById(empId);
        double salary = employee.getSalary();

        // Income slab limits in rupees
        double[] incomeLimits = {400000, 800000, 1200000, 1600000, 2000000, 2400000};
        List<Double> TAX_SLAB = List.of(0D, 5D, 10D, 15D, 20D, 25D, 30D); // Include 0% for 0–4L

        double tax = 0.0;
        double previousLimit = 0.0;
        double taxPercentage = 0.0;

        for (int i = 0; i < incomeLimits.length; i++) {
            if (salary > incomeLimits[i]) {
                tax += (incomeLimits[i] - previousLimit) * (TAX_SLAB.get(i) / 100.0);
                previousLimit = incomeLimits[i];
            } else {
                tax += (salary - previousLimit) * (TAX_SLAB.get(i) / 100.0);
                taxPercentage = TAX_SLAB.get(i); // Final applied slab
                double netSalary = salary - tax;
                return List.of(tax, taxPercentage, netSalary);
            }
        }

        // If salary > 24L, apply highest slab
        tax += (salary - previousLimit) * (TAX_SLAB.get(TAX_SLAB.size() - 1) / 100.0);
        taxPercentage = TAX_SLAB.get(TAX_SLAB.size() - 1);
        double netSalary = salary - tax;

        return List.of(tax, taxPercentage, netSalary);
    }

    public List<Bank> bankList(){
        return bankRepository.findAll();
    }
    public Bank findBankById(long id){
        return bankRepository.findById(id).orElse(null);
    }
    public Bank saveBank(Bank bank){
        return bankRepository.save(bank);
    }
    public void deleteBankById(long id) {
        try {
            // Step 1: Find the bank
            Bank bank = bankRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Bank not found with ID: " + id));

            // Step 2: Get all employees associated with this bank
            List<Employee> employees = bank.getEmployeeList();
            if (employees != null && !employees.isEmpty()) {
                for (Employee employee : employees) {
                    employee.setBank(null);  // Disassociate employee from this bank
                }
                employeeRepository.saveAll(employees);  // Persist changes
            }

            // Step 3: Delete the bank
            bankRepository.deleteById(id);

            System.out.println("Bank deleted successfully and employees disassociated.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error while deleting bank: " + e.getMessage());
            throw new RuntimeException("Failed to delete bank. Please try again later.", e);
        }
    }

    public Bank updateEmployee(Bank bank) {
        if (!bankRepository.existsById(bank.getAccountNumber())) {
            throw new IllegalArgumentException("Bank with account  " + bank.getAccountNumber() + " does not exist.");
        }


        return bankRepository.save(bank);
    }
}

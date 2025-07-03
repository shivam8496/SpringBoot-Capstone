package com.EmployeePayRollSystem.CapStoneProject.Repository;


import com.EmployeePayRollSystem.CapStoneProject.Model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
}

package com.EmployeePayRollSystem.CapStoneProject.Repository;


import com.EmployeePayRollSystem.CapStoneProject.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<List<Employee>> findByName(String name);
    Optional<Employee> findByEmail(String Email);
}

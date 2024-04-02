package com.example.ExcelToDatabase.repository;

import com.example.ExcelToDatabase.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}

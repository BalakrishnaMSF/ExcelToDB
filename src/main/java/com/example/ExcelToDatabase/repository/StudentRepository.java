package com.example.ExcelToDatabase.repository;

import com.example.ExcelToDatabase.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}

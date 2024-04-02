package com.example.ExcelToDatabase.service.Impl;

import com.example.ExcelToDatabase.entity.Employee;
import com.example.ExcelToDatabase.entity.Student;
import com.example.ExcelToDatabase.repository.EmployeeRepository;
import com.example.ExcelToDatabase.repository.StudentRepository;
import com.example.ExcelToDatabase.service.Impl.ExcelToDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveData(MultipartFile file) {
        try {
            List<Object> data = ExcelToDatabase.getDetails(file.getInputStream(), "Student", "Employee");

            for (Object obj : data) {
                if (obj instanceof Student student) {
                    studentRepository.save(student);
                } else if (obj instanceof Employee employee) {
                    employeeRepository.save(employee);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to process the file");
        }
    }
}

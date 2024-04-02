package com.example.ExcelToDatabase.service;

import com.example.ExcelToDatabase.entity.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelService {
    void saveStudent(MultipartFile multipartFile);
    List<Student> getStudents();
}

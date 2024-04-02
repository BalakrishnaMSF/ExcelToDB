package com.example.ExcelToDatabase.service.Impl;

import com.example.ExcelToDatabase.entity.Student;
import com.example.ExcelToDatabase.repository.StudentRepository;
import com.example.ExcelToDatabase.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StudentService implements ExcelService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void saveStudent(MultipartFile multipartFile) {
        try {
            List<Student> students = ExcelToDatabase.getStudentDetails(multipartFile.getInputStream());
            studentRepository.saveAll(students);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

}
package com.example.ExcelToDatabase.controller;

import com.example.ExcelToDatabase.dto.ResponseData;
import com.example.ExcelToDatabase.entity.Student;
import com.example.ExcelToDatabase.service.Impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadStudentsData(@RequestParam("file") MultipartFile file){
        try {
            studentService.saveStudent(file);
            List<Student> students = studentService.getStudents();
            return ResponseEntity.ok(new ResponseData("success", "200", "Student data added successfully",students));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(new ResponseData("error", "500", "An error occurred while processing the file",null));
        }
    }

    @GetMapping("/getStudents")
    public ResponseEntity<ResponseData> getStudentsData(){
        List<Student> students = studentService.getStudents();
        ResponseData responseData;
        if (students.isEmpty()) {
            responseData = new ResponseData("success", "200", "No student data available", students);
        } else {
            responseData = new ResponseData("success", "200", "Student data retrieved successfully", students);
        }
        return ResponseEntity.ok(responseData);
    }
}

package com.example.ExcelToDatabase.controller;

import com.example.ExcelToDatabase.dto.ResponseData;
import com.example.ExcelToDatabase.entity.Student;
import com.example.ExcelToDatabase.service.Impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService excelService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseData("error", "BAD_REQUEST", "File is empty", null));
        }

        try {
            excelService.saveData(file);
            return ResponseEntity.ok(new ResponseData("success", "DEFAULT", "Data uploaded successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData("error", "INTERNAL_SERVER_ERROR", e.getMessage(), null));
        }
    }
}

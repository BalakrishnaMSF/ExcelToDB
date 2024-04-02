package com.example.ExcelToDatabase.dto;

import com.example.ExcelToDatabase.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseData {
    private String status;
    private String error_code;
    private String message;
    private List<Student> data;
}

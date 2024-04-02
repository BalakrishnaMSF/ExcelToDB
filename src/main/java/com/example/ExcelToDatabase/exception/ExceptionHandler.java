package com.example.ExcelToDatabase.exception;

import com.example.ExcelToDatabase.exceptions.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ExceptionData> handleLabelNotFoundException(FileNotFoundException ex) {
        ExceptionData errorResponse = new ExceptionData(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}

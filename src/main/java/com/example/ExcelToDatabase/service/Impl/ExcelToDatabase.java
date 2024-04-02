package com.example.ExcelToDatabase.service.Impl;

import com.example.ExcelToDatabase.entity.Employee;
import com.example.ExcelToDatabase.entity.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.logging.Logger;

public class ExcelToDatabase {

    private static final Logger logger = Logger.getLogger(ExcelToDatabase.class.getName());

    public static List<Object> getDetails(InputStream inputStream, String... sheetNames) {

        if (inputStream != null && sheetNames != null && sheetNames.length > 0) {
            List<Object> data = new ArrayList<>();

            try {
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

                for (String sheetName : sheetNames) {
                    XSSFSheet sheet = workbook.getSheet(sheetName);

                    if (sheet != null) {
                        int lastRowNum = sheet.getLastRowNum();

                        switch (sheetName.toLowerCase()) {
                            case "student":
                                List<Student> students = new ArrayList<>();
                                for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
                                    Row row = sheet.getRow(rowIndex);
                                    if (row == null || isRowEmpty(row)) {
                                        break;
                                    }
                                    Student student = new Student();
                                    Iterator<Cell> cellIterator = row.cellIterator();
                                    while (cellIterator.hasNext()) {
                                        Cell cell = cellIterator.next();
                                        switch (cell.getColumnIndex()) {
                                            case 0:
                                                student.setId((int) cell.getNumericCellValue());
                                                break;
                                            case 1:
                                                student.setName(cell.getStringCellValue());
                                                break;
                                            case 2:
                                                student.setAge((int) cell.getNumericCellValue());
                                                break;
                                            case 3:
                                                student.setGrade(cell.getStringCellValue());
                                                break;
                                        }
                                    }
                                    students.add(student);
                                }
                                data.addAll(students);
                                break;
                            case "employee":
                                List<Employee> employees = new ArrayList<>();
                                for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
                                    Row row = sheet.getRow(rowIndex);
                                    if (row == null || isRowEmpty(row)) {
                                        break;
                                    }
                                    Employee employee = new Employee();
                                    Iterator<Cell> cellIterator = row.cellIterator();
                                    while (cellIterator.hasNext()) {
                                        Cell cell = cellIterator.next();
                                        switch (cell.getColumnIndex()) {
                                            case 0:
                                                employee.setId((int) cell.getNumericCellValue());
                                                break;
                                            case 1:
                                                employee.setName(cell.getStringCellValue());
                                                break;
                                            case 2:
                                                employee.setSalary(cell.getNumericCellValue());
                                                break;
                                        }
                                    }
                                    employees.add(employee);
                                }
                                data.addAll(employees);
                                break;
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return data;
        } else {
            return new ArrayList<>();
        }
    }

    private static boolean isRowEmpty(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}

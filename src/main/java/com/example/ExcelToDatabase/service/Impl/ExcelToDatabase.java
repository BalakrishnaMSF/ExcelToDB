package com.example.ExcelToDatabase.service.Impl;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelToDatabase {

    private static final Logger logger = Logger.getLogger(ExcelToDatabase.class.getName());

    public static List<Student> getStudentDetails(InputStream inputStream) {

        if (inputStream != null) {
            List<Student> students = new ArrayList<>();

            try {
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = workbook.getSheet("Student");

                int lastRowNum = sheet.getLastRowNum(); // Get the index of the last row containing data

                for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) { // Start from index 1 to skip header row
                    Row row = sheet.getRow(rowIndex);
                    if (row == null || isRowEmpty(row)) {
                        break; // Stop iterating if an empty row is encountered
                    }
                    int rowNum = row.getRowNum(); // Get the current row number
                    Iterator<Cell> cellIterator = row.iterator();
                    int cellIndex = 0;
                    Student student = new Student();
                    boolean validRow = true;
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (cellIndex == 0 && (cell == null || cell.getCellType() == CellType.BLANK || (cell.getCellType() == CellType.STRING && cell.getStringCellValue().isEmpty()))) {
                            validRow = false;
                            logger.log(Level.INFO, "Skipping row {0} due to null or empty ID.", rowNum);
                            break;
                        }
                        switch (cellIndex) {
                            case 0 -> {
                                try {
                                    if (cell.getCellType() == CellType.NUMERIC) {
                                        student.setId((int) cell.getNumericCellValue());
                                    } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                                        student.setId(Integer.parseInt(cell.getStringCellValue()));
                                    }
                                } catch (NumberFormatException e) {
                                    validRow = false;
                                    logger.log(Level.INFO, "Skipping row {0} due to non-numeric ID.", rowNum);
                                }
                            }
                            case 1 -> {
                                if (cell.getCellType() == CellType.STRING) {
                                    student.setName(cell.getStringCellValue());
                                } else {
                                    validRow = false;
                                    logger.log(Level.INFO, "Skipping row {0} due to non-string name.", rowNum);
                                }
                            }
                            case 2 -> {
                                if (cell.getCellType() == CellType.NUMERIC) {
                                    int age = (int) cell.getNumericCellValue();
                                    if (age < 0) {
                                        validRow = false;
                                        logger.log(Level.INFO, "Skipping row {0} due to negative age.", rowNum);
                                    } else {
                                        student.setAge(age);
                                    }
                                } else {
                                    validRow = false;
                                    logger.log(Level.INFO, "Skipping row {0} due to non-numeric age.", rowNum);
                                }
                            }
                            case 3 -> {
                                if (cell.getCellType() == CellType.STRING) {
                                    student.setGrade(cell.getStringCellValue());
                                } else {
                                    validRow = false;
                                    logger.log(Level.INFO, "Skipping row {0} due to non-string grade.", rowNum);
                                }
                            }
                        }
                        cellIndex++;
                    }
                    if (validRow) {
                        students.add(student);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return students;
        } else {
            return null;
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

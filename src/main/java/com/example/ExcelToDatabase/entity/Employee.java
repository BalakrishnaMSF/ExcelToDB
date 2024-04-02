package com.example.ExcelToDatabase.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Employee {
    @Id
    private Integer id;
    private String name;
    private double salary;

}

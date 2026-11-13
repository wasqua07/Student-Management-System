package com.wasqua.student_management;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @Min(1)
    private int roll;

    @NotBlank
    private String name;

    @NotBlank
    private String department;

    public Student() {
    }

    public Student(String name, int roll, String department) {
        this.name = name;
        this.roll = roll;
        this.department = department;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}

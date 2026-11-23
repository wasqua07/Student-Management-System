package com.wasqua.student_management.dto;

public class StudentResponseDTO {
    private int roll;
    private String name;
    private String department;

    public StudentResponseDTO(int roll, String name, String department) {
        this.roll = roll;
        this.name = name;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


package com.wasqua.student_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing student information")
public class StudentResponseDTO {

    @Schema(description = "Student's roll number", example = "101")
    private int roll;

    @Schema(description = "Student's full name", example = "Rahul Sharma")
    private String name;

    @Schema(description = "Name of the student's department", example = "CSE")
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

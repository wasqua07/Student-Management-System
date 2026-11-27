
package com.wasqua.student_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for creating or updating a student")
public class StudentRequestDTO {

    @Schema(
            description = "Unique roll number of the student",
            example = "101",
            minimum = "1"
    )
    @Min(1)
    private int roll;

    @Schema(
            description = "Full name of the student",
            example = "Rahul Sharma",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String name;

    @Schema(
            description = "ID of the department to which the student belongs",
            example = "1",
            minimum = "1"
    )
    @Min(1)
    private int departmentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }
}

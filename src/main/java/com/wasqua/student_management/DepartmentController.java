
package com.wasqua.student_management;

import com.wasqua.student_management.service.DepartmentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/departments")
@Tag(name = "Department Management", description = "APIs for managing departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(
            summary = "Add a new department",
            description = "Creates a department and saves it in the database"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid department data"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Admin access required")
    })

    @PostMapping
    public ResponseEntity<String> addDepartment(@RequestBody Department department) {
        departmentService.saveDepartment(department);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Department added successfully");
    }
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}

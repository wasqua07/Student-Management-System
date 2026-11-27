
package com.wasqua.student_management;

import com.wasqua.student_management.dto.StudentResponseDTO;
import com.wasqua.student_management.dto.StudentRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Set;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Student Management", description = "APIs for managing students")
@RestController
public class HelloController {

    private final StudentService studentService;

    public HelloController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Greet a student by name")
    @GetMapping("/student")
    public String studentInfo(@RequestParam String name) {
        return "Hello " + name;
    }

    @Operation(summary = "Welcome message")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello! Welcome to Student Management System.";
    }

    @Operation(summary = "Get project information")
    @GetMapping("/about")
    public String about() {
        return "This is my first Java Full Stack Project";
    }

    @Operation(summary = "Greet student using path variable")
    @GetMapping("/student/{name}")
    public String greetStudent(@PathVariable String name) {
        return "Hello " + name + "!";
    }

    @Operation(summary = "Get students with pagination")
    @GetMapping("/students/page")
    public Page<StudentResponseDTO> getStudentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return studentService.getStudentsPaginated(page, size);
    }

    @Operation(summary = "Sort students")
    @GetMapping("/students/sort")
    public List<StudentResponseDTO> sortStudents(
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        return studentService.sortStudents(sort);
    }

    @Operation(summary = "Register a new student")
    @PostMapping("/student")
    public ResponseEntity<StudentResponseDTO> addStudent(
            @Valid @RequestBody StudentRequestDTO studentRequestDTO) {

        StudentResponseDTO savedStudent =
                studentService.saveStudent(studentRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    @Operation(summary = "Filter students with pagination and sorting")
    @GetMapping("/students/filter/page")
    public Page<StudentResponseDTO> filterStudentsPaginated(
            @RequestParam String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid page or size");
        }

        if (!Set.of("name", "roll", "department").contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid sorting field");
        }

        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid sorting direction");
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return studentService.filterStudentsPaginated(department, pageable);
    }

    @Operation(
            summary = "Get all students",
            description = "Retrieves the list of all registered students"
    )
    @GetMapping("/students")
    public List<StudentResponseDTO> getStudent() {
        return studentService.getStudents();
    }

    @Operation(summary = "Search students by name")
    @GetMapping("/students/search")
    public List<StudentResponseDTO> searchStudents(@RequestParam String name) {
        return studentService.searchStudentsByName(name);
    }

    @Operation(summary = "Filter students by department")
    @GetMapping("/students/filter")
    public List<StudentResponseDTO> filterByDepartment(
            @RequestParam String department) {

        return studentService.filterByDepartment(department);
    }

    @Operation(summary = "Get student by roll number")
    @GetMapping("/students/{roll}")
    public ResponseEntity<StudentResponseDTO> getStudentByRoll(
            @PathVariable int roll) {

        StudentResponseDTO student = studentService.getStudentOrThrow(roll);

        return ResponseEntity.ok(student);
    }

    @Operation(summary = "Update student details")
    @PutMapping("/students/{roll}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable int roll,
            @Valid @RequestBody StudentRequestDTO updatedStudent) {

        StudentResponseDTO student =
                studentService.updateStudentOrThrow(roll, updatedStudent);

        return ResponseEntity.ok(student);
    }

    @Operation(summary = "Delete student by roll number")
    @DeleteMapping("/students/{roll}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int roll) {
        studentService.deleteStudentOrThrow(roll);
        return ResponseEntity.noContent().build();
    }
}

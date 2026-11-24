package com.wasqua.student_management;
import com.wasqua.student_management.dto.StudentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;
import com.wasqua.student_management.dto.StudentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Set;

@RestController
public class HelloController{
    private final StudentService studentService;

    public HelloController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public String studentInfo(@RequestParam String name){
        return "Hello " + name;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello! Welcome to Student Management System.";
    }

    @GetMapping("/about")
    public String about(){
        return "This is my first Java Full Stack Project";
    }

    @GetMapping("/student/{name}")
    public String greetStudent(@PathVariable String name){
        return "Hello " + name + "!";
    }

    @GetMapping("/students/page")
    public Page<StudentResponseDTO> getStudentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return studentService.getStudentsPaginated(page, size);
    }

    @GetMapping("/students/sort")
    public List<StudentResponseDTO> sortStudents(
            @RequestParam(defaultValue = "name") String field,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        return studentService.sortStudents(sort);
    }

    @PostMapping("/student")
    public StudentResponseDTO addStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        return studentService.saveStudent(studentRequestDTO);
    }

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

    @GetMapping("/students")
    public List<StudentResponseDTO> getStudent(){

        return studentService.getStudents();
    }

    @GetMapping("/students/search")
    public List<StudentResponseDTO> searchStudents(@RequestParam String name) {
        return studentService.searchStudentsByName(name);
    }

    @GetMapping("/students/filter")
    public List<StudentResponseDTO> filterByDepartment(
            @RequestParam String department) {

        return studentService.filterByDepartment(department);
    }

    @GetMapping("/students/{roll}")
    public ResponseEntity<StudentResponseDTO> getStudentByRoll(@PathVariable int roll) {

        StudentResponseDTO student = studentService.getStudentOrThrow(roll);

        return ResponseEntity.ok(student);
    }

    @PutMapping("/students/{roll}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable int roll, @Valid @RequestBody StudentRequestDTO updatedStudent){
        StudentResponseDTO student = studentService.updateStudentOrThrow(roll, updatedStudent);

        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/students/{roll}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int roll){
        studentService.deleteStudentOrThrow(roll);
        return ResponseEntity.noContent().build();
    }

}


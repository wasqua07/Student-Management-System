package com.wasqua.student_management;
import com.wasqua.student_management.dto.StudentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;
import com.wasqua.student_management.dto.StudentRequestDTO;




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


    @PostMapping("/student")
    public StudentResponseDTO addStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        return studentService.saveStudent(studentRequestDTO);
    }

    @GetMapping("/students")
    public List<StudentResponseDTO> getStudent(){

        return studentService.getStudents();
    }

    @GetMapping("/students/search")
    public List<StudentResponseDTO> searchStudents(@RequestParam String name) {
        return studentService.searchStudentsByName(name);
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


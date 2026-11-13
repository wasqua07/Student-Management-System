package com.wasqua.student_management;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;


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
    public Student addStudent(@Valid @RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping("/students")
    public List<Student> getStudent(){
        return studentService.getStudents();
    }

    @GetMapping("/students/{roll}")
    public ResponseEntity<Student> getStudentByRoll(@PathVariable int roll) {

        Student student = studentService.getStudentOrThrow(roll);

        return ResponseEntity.ok(student);
    }

    @PutMapping("/students/{roll}")
    public ResponseEntity<Student> updateStudent(@PathVariable int roll, @Valid @RequestBody Student updatedStudent){
        Student student = studentService.updateStudentOrThrow(roll, updatedStudent);

        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/students/{roll}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int roll){
        studentService.deleteStudentOrThrow(roll);
        return ResponseEntity.noContent().build();
    }

}


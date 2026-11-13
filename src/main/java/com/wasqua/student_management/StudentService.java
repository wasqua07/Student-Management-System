package com.wasqua.student_management;

import com.wasqua.student_management.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByRoll(int roll){
        return studentRepository.findById(roll);
    }

    public Student getStudentOrThrow(int roll) {

        Optional<Student> student = studentRepository.findById(roll);

        if (student.isPresent()) {
            return student.get();
        }

        throw new StudentNotFoundException(
                "Student with roll number " + roll + " not found"
        );
    }

    public Student updateStudentOrThrow(int roll, Student updatedStudent){
        Student student = getStudentOrThrow(roll);

        student.setName(updatedStudent.getName());
        student.setDepartment(updatedStudent.getDepartment());

        return studentRepository.save(student);

    }

    public void deleteStudentOrThrow(int roll){
        getStudentOrThrow(roll);

        studentRepository.deleteById(roll);
    }


}
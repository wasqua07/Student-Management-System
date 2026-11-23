package com.wasqua.student_management;

import com.wasqua.student_management.dto.StudentRequestDTO;
import com.wasqua.student_management.dto.StudentResponseDTO;
import com.wasqua.student_management.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO){

        Student student = new Student(
            studentRequestDTO.getName(),
            studentRequestDTO.getRoll(),
            studentRequestDTO.getDepartment()
        );

        Student savedStudent = studentRepository.save(student);

        return new StudentResponseDTO(
                savedStudent.getRoll(),
                savedStudent.getName(),
                savedStudent.getDepartment()
        );
    }

    public List<StudentResponseDTO> getStudents(){
        List<Student> students = studentRepository.findAll();

        return students.stream().map(student -> new StudentResponseDTO(student.getRoll(),
                student.getName(),
                student.getDepartment()
        )).toList();
    }

    public List<StudentResponseDTO> searchStudentsByName(String name) {
        List<Student> students  = studentRepository.findByNameContainingIgnoreCase(name);

        return students.stream().map(stu -> new StudentResponseDTO(stu.getRoll(), stu.getName(), stu.getDepartment()
        )).toList();
    }


    private Student findStudentOrThrow(int roll) {
        return studentRepository.findById(roll).orElseThrow(() -> new StudentNotFoundException(
                "Student with roll number " + roll + " not found"
        ));
    }

    public StudentResponseDTO getStudentOrThrow(int roll) {

        Student student = findStudentOrThrow(roll);

        return new StudentResponseDTO(
                student.getRoll(),
                student.getName(),
                student.getDepartment()
        );
    }

    public StudentResponseDTO updateStudentOrThrow(int roll, StudentRequestDTO updatedStudent) {

        Student student = findStudentOrThrow(roll);


        student.setName(updatedStudent.getName());
        student.setDepartment(updatedStudent.getDepartment());

        Student savedStudent = studentRepository.save(student);

        return new StudentResponseDTO(
                savedStudent.getRoll(),
                savedStudent.getName(),
                savedStudent.getDepartment()
        );
    }

    public void deleteStudentOrThrow(int roll){
        Student student = findStudentOrThrow(roll);

        studentRepository.delete(student);
    }


}
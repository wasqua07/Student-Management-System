package com.wasqua.student_management;

import com.wasqua.student_management.dto.StudentRequestDTO;
import com.wasqua.student_management.dto.StudentResponseDTO;
import com.wasqua.student_management.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



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

    public Page<StudentResponseDTO> getStudentsPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Student> students = studentRepository.findAll(pageable);

        return students.map(student -> new StudentResponseDTO(
                student.getRoll(),
                student.getName(),
                student.getDepartment()
        ));
    }

    public List<StudentResponseDTO> searchStudentsByName(String name) {
        List<Student> students  = studentRepository.findByNameContainingIgnoreCase(name);

        return students.stream().map(stu -> new StudentResponseDTO(stu.getRoll(), stu.getName(), stu.getDepartment()
        )).toList();
    }

    public List<StudentResponseDTO> filterByDepartment(String department) {

        List<Student> students =
                studentRepository.findByDepartmentIgnoreCase(department);

        return students.stream().map(student ->
                new StudentResponseDTO(
                        student.getRoll(),
                        student.getName(),
                        student.getDepartment()
                )
        ).toList();
    }

    public List<StudentResponseDTO> sortStudents(Sort sort) {

        List<Student> students = studentRepository.findAll(sort);

        return students.stream().map(student ->
                new StudentResponseDTO(
                        student.getRoll(),
                        student.getName(),
                        student.getDepartment()
                )
        ).toList();
    }

    public Page<StudentResponseDTO> filterStudentsPaginated(
            String department, Pageable pageable) {

        Page<Student> students =
                studentRepository.findByDepartmentIgnoreCase(department, pageable);

        return students.map(student -> new StudentResponseDTO(
                student.getRoll(),
                student.getName(),
                student.getDepartment()
        ));
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
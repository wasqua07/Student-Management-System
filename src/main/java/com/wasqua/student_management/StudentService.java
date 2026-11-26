
package com.wasqua.student_management;

import com.wasqua.student_management.dto.StudentRequestDTO;
import com.wasqua.student_management.dto.StudentResponseDTO;
import com.wasqua.student_management.repository.StudentRepository;
import com.wasqua.student_management.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    public StudentService(
            StudentRepository studentRepository,
            DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    private Department findDepartmentOrThrow(int departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Department with ID " + departmentId + " not found"
                ));
    }

    private Student findStudentOrThrow(int roll) {
        return studentRepository.findById(roll)
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student with roll number " + roll + " not found"
                ));
    }

    private StudentResponseDTO convertToDTO(Student student) {
        return new StudentResponseDTO(
                student.getRoll(),
                student.getName(),
                student.getDepartment().getName()
        );
    }

    public StudentResponseDTO saveStudent(StudentRequestDTO request) {

        Department department =
                findDepartmentOrThrow(request.getDepartmentId());

        Student student = new Student(
                request.getName(),
                request.getRoll(),
                department
        );

        Student savedStudent = studentRepository.save(student);

        return convertToDTO(savedStudent);
    }

    public List<StudentResponseDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Page<StudentResponseDTO> getStudentsPaginated(
            int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return studentRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public List<StudentResponseDTO> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<StudentResponseDTO> filterByDepartment(String department) {
        return studentRepository
                .findByDepartment_NameIgnoreCase(department)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<StudentResponseDTO> sortStudents(Sort sort) {
        return studentRepository.findAll(sort)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Page<StudentResponseDTO> filterStudentsPaginated(
            String department, Pageable pageable) {

        return studentRepository
                .findByDepartment_NameIgnoreCase(department, pageable)
                .map(this::convertToDTO);
    }

    public StudentResponseDTO getStudentOrThrow(int roll) {
        return convertToDTO(findStudentOrThrow(roll));
    }

    public StudentResponseDTO updateStudentOrThrow(
            int roll, StudentRequestDTO updatedStudent) {

        Student student = findStudentOrThrow(roll);

        Department department =
                findDepartmentOrThrow(updatedStudent.getDepartmentId());

        student.setName(updatedStudent.getName());
        student.setDepartment(department);

        Student savedStudent = studentRepository.save(student);

        return convertToDTO(savedStudent);
    }

    public void deleteStudentOrThrow(int roll) {
        Student student = findStudentOrThrow(roll);
        studentRepository.delete(student);
    }
}

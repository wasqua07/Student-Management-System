
package com.wasqua.student_management;

import com.wasqua.student_management.dto.StudentRequestDTO;
import com.wasqua.student_management.repository.StudentRepository;
import com.wasqua.student_management.repository.DepartmentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void saveStudentTest() {

        Department department = new Department("CSE");
        department.setId(1);

        when(departmentRepository.findById(1))
                .thenReturn(Optional.of(department));

        StudentRequestDTO request = new StudentRequestDTO();
        request.setRoll(101);
        request.setName("Wasqua");
        request.setDepartmentId(1);

        when(studentRepository.save(any(Student.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var response = studentService.saveStudent(request);

        assertNotNull(response);
        assertEquals(101, response.getRoll());
        assertEquals("Wasqua", response.getName());
        assertEquals("CSE", response.getDepartment());

        verify(departmentRepository).findById(1);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void saveStudentWithInvalidDepartmentTest() {

        StudentRequestDTO request = new StudentRequestDTO();
        request.setRoll(102);
        request.setName("Rahul");
        request.setDepartmentId(999);

        when(departmentRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> studentService.saveStudent(request)
        );

        verify(studentRepository, never()).save(any(Student.class));
    }
}

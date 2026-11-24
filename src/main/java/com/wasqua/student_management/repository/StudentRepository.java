package com.wasqua.student_management.repository;

import com.wasqua.student_management.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByDepartmentIgnoreCase(String department);
//
//
//    List<Student> findByDepartmentIgnoreCase(String department);

    Page<Student> findByDepartmentIgnoreCase(
            String department,
            Pageable pageable
    );
}
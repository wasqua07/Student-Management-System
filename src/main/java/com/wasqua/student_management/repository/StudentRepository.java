
package com.wasqua.student_management.repository;

import com.wasqua.student_management.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByDepartment_NameIgnoreCase(String department);

    Page<Student> findByDepartment_NameIgnoreCase(
            String department,
            Pageable pageable
    );
}

package com.wasqua.student_management.repository;

import com.wasqua.student_management.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<Department, Integer> {
}
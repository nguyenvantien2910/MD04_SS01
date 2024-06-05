package com.ra.session01.service;

import com.ra.session01.model.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(Long departId);

    Department addDepartment(Department department);
    Department updateDepartment(Department department);
    Boolean deleteDepartment(Long departId);
    List<Department> getDepartmentsByDepartmentName(String departmentName);
    Boolean updateDepartmentStatus(Long departId);
}

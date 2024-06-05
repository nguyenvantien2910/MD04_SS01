package com.ra.session01.service;

import com.ra.session01.model.entity.Employee;

import java.util.List;

public interface EmplyeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long emId);

    Employee addEmployee(Employee emp);

    Employee updateEmployee(Employee emp);

    Boolean deleteEmployee(Long emId);

    List<Employee> getEmployeesByDepartment(Long departmentId);

    List<Employee> getAllEmployeeByName(String name);
}

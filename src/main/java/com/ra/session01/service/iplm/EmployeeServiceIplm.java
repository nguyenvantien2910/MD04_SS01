package com.ra.session01.service.iplm;

import com.ra.session01.model.entity.Department;
import com.ra.session01.model.entity.Employee;
import com.ra.session01.repository.DepartmentRepository;
import com.ra.session01.repository.EmployeeRepository;
import com.ra.session01.service.EmplyeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceIplm implements EmplyeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long emId) {
        return employeeRepository.findById(emId).orElseThrow(() -> new NoSuchElementException("Employee Id not found!"));
    }

    @Override
    public Employee addEmployee(Employee emp) {
        return employeeRepository.save(emp);
    }

    @Override
    public Employee updateEmployee(Employee emp) {
        Employee employee = employeeRepository.findById(emp.getEmpId()).orElseThrow(()->new NoSuchElementException("Employee Id not found!"));
        employeeRepository.save(employee);
        return null;
    }

    @Override
    public Boolean deleteEmployee(Long emId) {
        employeeRepository.deleteById(emId);
        return true;
    }

    @Override
    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(()->new NoSuchElementException("Department Id not found!"));
        return employeeRepository.findAll().stream().filter(employee -> employee.getDepartment().equals(department)).collect(Collectors.toList());
    }

    @Override
    public List<Employee> getAllEmployeeByName(String name) {
        return employeeRepository.findAll().stream().filter(employee -> employee.getFullname().equals(name)).collect(Collectors.toList());
    }
}

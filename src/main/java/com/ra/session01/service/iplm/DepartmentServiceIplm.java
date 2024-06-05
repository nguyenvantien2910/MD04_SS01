package com.ra.session01.service.iplm;

import com.ra.session01.model.entity.Department;
import com.ra.session01.repository.DepartmentRepository;
import com.ra.session01.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceIplm implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long departId) {
        return departmentRepository.findById(departId).orElseThrow(() -> new NoSuchElementException("Department Id not found"));
    }

    @Override
    public Department addDepartment(Department department) {
        List<Department> departments = departmentRepository.findAll();
        boolean departmentExists = departments.stream()
                .anyMatch(d -> d.getDepartName().equalsIgnoreCase(department.getDepartName()));

        if (departmentExists) {
            throw new IllegalArgumentException("Department with name " + department.getDepartName() + " already exists!");
        }

        return departmentRepository.save(department);
    }


    @Override
    public Department updateDepartment(Department department) {
        Department departmentOld = departmentRepository.findById(department.getDepartId())
                .orElseThrow(() -> new NoSuchElementException("Department Id not found"));

        boolean nameIsExisted = departmentRepository.findAll().stream()
                .anyMatch(d -> !d.getDepartId().equals(department.getDepartId()) &&
                        d.getDepartName().equalsIgnoreCase(department.getDepartName()));

        if (nameIsExisted) {
            throw new IllegalArgumentException("Department with name " + department.getDepartName() + " already exists!");
        }

        return departmentRepository.save(department);
    }


    @Override
    public Boolean deleteDepartment(Long departId) {
        departmentRepository.deleteById(departId);
        return true;
    }

    @Override
    public List<Department> getDepartmentsByDepartmentName(String departmentName) {
        return departmentRepository.findAll().stream().filter(department -> department.getDepartName().equals(departmentName)).collect(Collectors.toList());
    }

    @Override
    public Boolean updateDepartmentStatus(Long departId) {
        Department department = departmentRepository.findById(departId).orElseThrow(() -> new NoSuchElementException("Department Id not found"));
        department.setStatus(!department.getStatus());
        departmentRepository.save(department);
        return true;
    }
}

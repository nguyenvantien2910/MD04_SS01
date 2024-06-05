package com.ra.session01.controller;

import com.ra.session01.model.entity.Department;
import com.ra.session01.model.entity.Employee;
import com.ra.session01.service.DepartmentService;
import com.ra.session01.service.EmplyeeService;
import com.ra.session01.service.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmplyeeService emplyeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UploadFile uploadFile;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", emplyeeService.getAllEmployees());
        return "employee/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("employee", new Employee());
        List<Department> departments = departmentService.getAllDepartments().stream()
                .filter(Department::getStatus)
                .collect(Collectors.toList());

        model.addAttribute("departments", departments);
        return "employee/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
                      Model model, @RequestParam MultipartFile imageFile) {
        if (bindingResult.hasErrors()) {
            List<Department> departments = departmentService.getAllDepartments().stream()
                    .filter(Department::getStatus)
                    .collect(Collectors.toList());
            model.addAttribute("departments", departments);
            return "employee/add";
        }

        if (!imageFile.isEmpty()) {
            String imageUrl = uploadFile.uploadToLocal(imageFile);
            employee.setImageUrl(imageUrl);
        }
        Employee newEmployee = emplyeeService.addEmployee(employee);
        if (newEmployee != null) {
            return "redirect:/employee";
        } else {
            model.addAttribute("employee", new Employee());
            List<Department> departments = departmentService.getAllDepartments().stream()
                    .filter(Department::getStatus)
                    .collect(Collectors.toList());
            model.addAttribute("departments", departments);
            return "employee/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") Long empId, Model model) {
        model.addAttribute("employee", emplyeeService.getEmployeeById(empId));
        return "employee/edit";
    }

    @PostMapping("/edit")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "employee/edit";
        }

        Employee updateEmployee = emplyeeService.updateEmployee(employee);
        if (updateEmployee != null) {
            return "redirect:/employee";
        } else {
            model.addAttribute("employee", employee);
            return "employee/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long empId, RedirectAttributes redirectAttributes) {
        if (emplyeeService.deleteEmployee(empId)) {
            return "redirect:/employee";
        } else {
            redirectAttributes.addAttribute("message", "Delete employee failed !");
            return "redirect:/employee";
        }
    }
}

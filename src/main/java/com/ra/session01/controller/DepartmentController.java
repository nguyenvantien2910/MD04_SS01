package com.ra.session01.controller;

import com.ra.session01.model.entity.Department;
import com.ra.session01.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "department/list";
    }

    @GetMapping("/add")
    public String addDepartment(Model model) {
        model.addAttribute("department", new Department());
        return "department/add";
    }

    @PostMapping("/add")
    public String saveDepartment(@ModelAttribute Department department, Model model) {
        Department d = departmentService.addDepartment(department);
        if (d != null) {
            return "redirect:/department";
        } else {
            model.addAttribute("department", d);
            return "department/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editDepartment(@PathVariable("id") Long departId, Model model) {
        model.addAttribute("department", departmentService.getDepartmentById(departId));
        return "department/edit";
    }

    @PostMapping("/edit")
    public String updateDepartment(@ModelAttribute("department") Department department, Model model) {
        Department updateDepartment = departmentService.updateDepartment(department);
        if (updateDepartment != null) {
            return "redirect:/department";
        } else {
            model.addAttribute("department", department);
            return "department/edit";
        }
    }

    @GetMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable("id") Long departId, RedirectAttributes redirectAttributes) {
        if (departmentService.updateDepartmentStatus(departId)) {
            return "redirect:/department";
        } else {
            redirectAttributes.addAttribute("message", "Update status failed !");
            return "redirect:/department";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long departId, RedirectAttributes redirectAttributes) {
        if (departmentService.deleteDepartment(departId)) {
            return "redirect:/department";
        } else {
            redirectAttributes.addAttribute("message", "Delete department failed !");
            return "redirect:/department";
        }
    }
}

package com.nguyenvuthanh.springjdbc.controller;

import com.nguyenvuthanh.springjdbc.dto.EmployeeForm;
import com.nguyenvuthanh.springjdbc.dto.EmployeeSearchCriteria;
import com.nguyenvuthanh.springjdbc.model.Employee;
import com.nguyenvuthanh.springjdbc.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(@ModelAttribute("criteria") EmployeeSearchCriteria criteria, Model model) {
        model.addAttribute("employees", employeeService.search(criteria));
        addLookups(model);
        return "employees/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        addLookups(model);
        return "employees/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("employeeForm") EmployeeForm form,
                         BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            addLookups(model);
            return "employees/form";
        }
        Employee saved = employeeService.save(null, form);
        redirect.addFlashAttribute("message", "Đã thêm nhân viên " + saved.getName() + ".");
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "employees/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("employeeForm", employeeService.getForm(id));
        addLookups(model);
        return "employees/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("employeeForm") EmployeeForm form,
                         BindingResult result, Model model, RedirectAttributes redirect) {
        form.setId(id);
        if (result.hasErrors()) {
            addLookups(model);
            return "employees/form";
        }
        Employee saved = employeeService.save(id, form);
        redirect.addFlashAttribute("message", "Đã lưu thay đổi của " + saved.getName() + ".");
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirect) {
        employeeService.delete(id);
        redirect.addFlashAttribute("message", "Đã xóa nhân viên ID " + id + ".");
        return "redirect:/employees";
    }

    @GetMapping("/reports")
    public String reports(@RequestParam(required = false) Integer departmentId, Model model) {
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departmentStats", employeeService.departmentStats());
        model.addAttribute("topEmployees", employeeService.topPaid(departmentId));
        model.addAttribute("emptyDepartments", employeeService.emptyDepartments());
        addLookups(model);
        return "employees/reports";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFound(IllegalArgumentException exception, RedirectAttributes redirect) {
        redirect.addFlashAttribute("error", exception.getMessage());
        return "redirect:/employees";
    }

    private void addLookups(Model model) {
        model.addAttribute("departments", employeeService.departments());
        model.addAttribute("positions", employeeService.positions());
    }
}

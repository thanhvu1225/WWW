package com.nguyenvuthanh.mongodb.controller;

import com.nguyenvuthanh.mongodb.dto.EmployeeForm;
import com.nguyenvuthanh.mongodb.dto.EmployeeSearchCriteria;
import com.nguyenvuthanh.mongodb.model.Employee;
import com.nguyenvuthanh.mongodb.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping({"", "/search"})
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'GUEST')")
    public String list(@ModelAttribute("criteria") EmployeeSearchCriteria criteria, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isGuest = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GUEST"));

        // Phân quyền: Tìm theo salary (dữ liệu nhạy cảm): GUEST: Không được phép, USER & ADMIN: Được phép
        if (isGuest && criteria.getMinSalary() != null) {
            criteria.setMinSalary(null);
            model.addAttribute("error", "GUEST không có quyền tìm kiếm theo mức lương (dữ liệu nhạy cảm)!");
        }

        model.addAttribute("employees", employeeService.search(criteria));
        addLookups(model);
        return "employees/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String newForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        addLookups(model);
        return "employees/form";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'GUEST')")
    public String detail(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "employees/detail";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("employeeForm", employeeService.getForm(id));
        addLookups(model);
        return "employees/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String update(@PathVariable String id,
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
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable String id, RedirectAttributes redirect) {
        employeeService.delete(id);
        redirect.addFlashAttribute("message", "Đã xóa nhân viên ID " + id + ".");
        return "redirect:/employees";
    }

    @GetMapping("/reports")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'GUEST')")
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

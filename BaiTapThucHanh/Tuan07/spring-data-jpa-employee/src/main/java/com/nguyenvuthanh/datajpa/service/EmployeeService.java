package com.nguyenvuthanh.datajpa.service;

import com.nguyenvuthanh.datajpa.dto.DepartmentStat;
import com.nguyenvuthanh.datajpa.dto.EmployeeForm;
import com.nguyenvuthanh.datajpa.dto.EmployeeSearchCriteria;
import com.nguyenvuthanh.datajpa.dto.LookupOption;
import com.nguyenvuthanh.datajpa.model.Department;
import com.nguyenvuthanh.datajpa.model.Employee;
import com.nguyenvuthanh.datajpa.model.Position;
import com.nguyenvuthanh.datajpa.repository.DepartmentRepository;
import com.nguyenvuthanh.datajpa.repository.EmployeeRepository;
import com.nguyenvuthanh.datajpa.repository.PositionRepository;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
                           PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() { return initialize(employeeRepository.findAll(Sort.by("id"))); }

    @Transactional(readOnly = true)
    public List<Employee> search(EmployeeSearchCriteria c) {
        Specification<Employee> specification = (root, query, cb) -> cb.conjunction();
        if (c.getId() != null) specification = specification.and((root, query, cb) -> cb.equal(root.get("id"), c.getId()));
        if (hasText(c.getName())) specification = specification.and((root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + c.getName().trim().toLowerCase() + "%"));
        if (c.getMinAge() != null) specification = specification.and((root, query, cb) -> cb.ge(root.get("age"), c.getMinAge()));
        if (c.getMaxAge() != null) specification = specification.and((root, query, cb) -> cb.le(root.get("age"), c.getMaxAge()));
        if (c.getMinSalary() != null) specification = specification.and((root, query, cb) -> cb.gt(root.get("salary"), c.getMinSalary()));
        if (c.getDepartmentId() != null) specification = specification.and((root, query, cb) ->
                cb.equal(root.join("department", JoinType.LEFT).get("id"), c.getDepartmentId()));
        if (c.getPositionId() != null) specification = specification.and((root, query, cb) ->
                cb.equal(root.join("position", JoinType.LEFT).get("id"), c.getPositionId()));
        return initialize(employeeRepository.findAll(specification, Sort.by("id")));
    }

    @Transactional(readOnly = true)
    public Employee findById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên ID " + id));
        initialize(List.of(employee));
        return employee;
    }

    public List<LookupOption> departments() {
        return departmentRepository.findAll(Sort.by("name")).stream().map(d -> new LookupOption(d.getId(), d.getName())).toList();
    }
    public List<LookupOption> positions() {
        return positionRepository.findAll(Sort.by("id")).stream().map(p -> new LookupOption(p.getId(), p.getTitle())).toList();
    }
    public List<DepartmentStat> departmentStats() { return departmentRepository.findDepartmentStats(); }

    @Transactional(readOnly = true)
    public List<Employee> topPaid(Integer departmentId) {
        return departmentId == null ? List.of() : initialize(employeeRepository.findTop5ByDepartment_IdOrderBySalaryDesc(departmentId));
    }

    public List<LookupOption> emptyDepartments() {
        return departmentRepository.findDepartmentsWithoutEmployees().stream()
                .map(d -> new LookupOption(d.getId(), d.getName())).toList();
    }

    public EmployeeForm getForm(Integer id) {
        Employee e = findById(id);
        EmployeeForm form = new EmployeeForm();
        form.setId(e.getId()); form.setName(e.getName()); form.setRole(e.getRole()); form.setAge(e.getAge());
        form.setSalary(e.getSalary()); form.setDepartmentId(e.getDepartmentId()); form.setPositionId(e.getPositionId());
        return form;
    }

    @Transactional
    public Employee save(Integer id, EmployeeForm form) {
        Employee e = id == null ? new Employee() : employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên ID " + id));
        e.setName(form.getName().trim()); e.setRole(form.getRole().trim()); e.setAge(form.getAge()); e.setSalary(form.getSalary());
        Department department = departmentRepository.findById(form.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Phòng ban không tồn tại"));
        Position position = positionRepository.findById(form.getPositionId())
                .orElseThrow(() -> new IllegalArgumentException("Chức vụ không tồn tại"));
        e.setDepartment(department); e.setPosition(position);
        Employee saved = employeeRepository.save(e);
        initialize(List.of(saved));
        return saved;
    }

    @Transactional
    public void delete(Integer id) {
        if (!employeeRepository.existsById(id)) throw new IllegalArgumentException("Không tìm thấy nhân viên ID " + id);
        employeeRepository.deleteById(id);
    }

    private List<Employee> initialize(List<Employee> employees) {
        employees.forEach(e -> { e.getDepartmentName(); e.getPositionTitle(); });
        return employees;
    }
    private static boolean hasText(String value) { return value != null && !value.isBlank(); }
}

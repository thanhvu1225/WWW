package com.nguyenvuthanh.datajdbc.service;

import com.nguyenvuthanh.datajdbc.dto.DepartmentStat;
import com.nguyenvuthanh.datajdbc.dto.EmployeeForm;
import com.nguyenvuthanh.datajdbc.dto.EmployeeSearchCriteria;
import com.nguyenvuthanh.datajdbc.dto.LookupOption;
import com.nguyenvuthanh.datajdbc.model.Department;
import com.nguyenvuthanh.datajdbc.model.Employee;
import com.nguyenvuthanh.datajdbc.model.Position;
import com.nguyenvuthanh.datajdbc.repository.DepartmentRepository;
import com.nguyenvuthanh.datajdbc.repository.EmployeeRepository;
import com.nguyenvuthanh.datajdbc.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<Employee> findAll() { return enrich(toList(employeeRepository.findAll())); }

    public List<Employee> search(EmployeeSearchCriteria c) {
        List<Employee> base;
        if (c.getId() != null) {
            base = employeeRepository.findById(c.getId()).map(List::of).orElseGet(List::of);
        } else if (hasText(c.getName())) {
            base = employeeRepository.findByNameContainingIgnoreCase(c.getName().trim());
        } else if (c.getDepartmentId() != null && c.getPositionId() != null) {
            base = employeeRepository.findByDepartmentIdAndPositionId(c.getDepartmentId(), c.getPositionId());
        } else if (c.getPositionId() != null) {
            base = employeeRepository.findByPositionId(c.getPositionId());
        } else if (c.getMinAge() != null && c.getMaxAge() != null) {
            base = employeeRepository.findByAgeBetween(c.getMinAge(), c.getMaxAge());
        } else if (c.getMinSalary() != null) {
            base = employeeRepository.findBySalaryGreaterThan(c.getMinSalary());
        } else {
            base = toList(employeeRepository.findAll());
        }
        return enrich(base.stream()
                .filter(e -> c.getId() == null || Objects.equals(e.getId(), c.getId()))
                .filter(e -> !hasText(c.getName()) || e.getName().toLowerCase().contains(c.getName().trim().toLowerCase()))
                .filter(e -> c.getMinAge() == null || e.getAge() >= c.getMinAge())
                .filter(e -> c.getMaxAge() == null || e.getAge() <= c.getMaxAge())
                .filter(e -> c.getMinSalary() == null || e.getSalary() > c.getMinSalary())
                .filter(e -> c.getDepartmentId() == null || Objects.equals(e.getDepartmentId(), c.getDepartmentId()))
                .filter(e -> c.getPositionId() == null || Objects.equals(e.getPositionId(), c.getPositionId()))
                .sorted(Comparator.comparing(Employee::getId)).toList());
    }

    public Employee findById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy nhân viên ID " + id));
        return enrich(List.of(employee)).getFirst();
    }

    public List<LookupOption> departments() {
        return toList(departmentRepository.findAll()).stream().sorted(Comparator.comparing(Department::getName))
                .map(d -> new LookupOption(d.getId(), d.getName())).toList();
    }

    public List<LookupOption> positions() {
        return toList(positionRepository.findAll()).stream().sorted(Comparator.comparing(Position::getId))
                .map(p -> new LookupOption(p.getId(), p.getTitle())).toList();
    }

    public List<DepartmentStat> departmentStats() {
        List<Employee> employees = toList(employeeRepository.findAll());
        return toList(departmentRepository.findAll()).stream().sorted(Comparator.comparing(Department::getId)).map(d -> {
            DoubleSummaryStatistics stats = employees.stream().filter(e -> Objects.equals(e.getDepartmentId(), d.getId()))
                    .mapToDouble(Employee::getSalary).summaryStatistics();
            return new DepartmentStat(d.getId(), d.getName(), stats.getCount(),
                    stats.getCount() == 0 ? 0D : stats.getAverage(), stats.getSum());
        }).toList();
    }

    public List<Employee> topPaid(Integer departmentId) {
        return departmentId == null ? List.of() : enrich(employeeRepository.findTop5ByDepartmentIdOrderBySalaryDesc(departmentId));
    }

    public List<LookupOption> emptyDepartments() {
        Set<Integer> used = toList(employeeRepository.findAll()).stream().map(Employee::getDepartmentId).collect(Collectors.toSet());
        return departments().stream().filter(d -> !used.contains(d.id())).toList();
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
        e.setId(id); e.setName(form.getName().trim()); e.setRole(form.getRole().trim()); e.setAge(form.getAge());
        e.setSalary(form.getSalary()); e.setDepartmentId(form.getDepartmentId()); e.setPositionId(form.getPositionId());
        return enrich(List.of(employeeRepository.save(e))).getFirst();
    }

    @Transactional
    public void delete(Integer id) {
        if (!employeeRepository.existsById(id)) throw new IllegalArgumentException("Không tìm thấy nhân viên ID " + id);
        employeeRepository.deleteById(id);
    }

    private List<Employee> enrich(List<Employee> employees) {
        Map<Integer, Department> departments = toList(departmentRepository.findAll()).stream()
                .collect(Collectors.toMap(Department::getId, Function.identity()));
        Map<Integer, Position> positions = toList(positionRepository.findAll()).stream()
                .collect(Collectors.toMap(Position::getId, Function.identity()));
        employees.forEach(e -> {
            Department department = departments.get(e.getDepartmentId());
            Position position = positions.get(e.getPositionId());
            e.setDepartmentName(department == null ? "Chưa phân phòng" : department.getName());
            e.setPositionTitle(position == null ? "Chưa có chức vụ" : position.getTitle());
        });
        return employees;
    }

    private static boolean hasText(String value) { return value != null && !value.isBlank(); }
    private static <T> List<T> toList(Iterable<T> source) {
        return StreamSupport.stream(source.spliterator(), false).toList();
    }
}

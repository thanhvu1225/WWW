package com.nguyenvuthanh.datajpa.repository;

import com.nguyenvuthanh.datajpa.dto.DepartmentStat;
import com.nguyenvuthanh.datajpa.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("""
            select new com.nguyenvuthanh.datajpa.dto.DepartmentStat(
                d.id, d.name, count(e.id), coalesce(avg(e.salary), 0.0), coalesce(sum(e.salary), 0.0))
            from Department d left join d.employees e
            group by d.id, d.name order by d.id
            """)
    List<DepartmentStat> findDepartmentStats();

    @Query("select d from Department d where d.employees is empty order by d.name")
    List<Department> findDepartmentsWithoutEmployees();
}

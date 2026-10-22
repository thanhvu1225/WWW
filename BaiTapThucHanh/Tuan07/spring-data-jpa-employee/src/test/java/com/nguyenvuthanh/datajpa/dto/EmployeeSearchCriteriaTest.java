package com.nguyenvuthanh.datajpa.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeSearchCriteriaTest {
    @Test
    void storesAllSearchValues() {
        EmployeeSearchCriteria criteria = new EmployeeSearchCriteria();
        criteria.setId(7);
        criteria.setName("Đức");
        criteria.setMinAge(20);
        criteria.setMaxAge(35);
        criteria.setMinSalary(10_000_000D);
        criteria.setDepartmentId(1);
        criteria.setPositionId(2);

        assertThat(criteria.getId()).isEqualTo(7);
        assertThat(criteria.getName()).isEqualTo("Đức");
        assertThat(criteria.getMinAge()).isEqualTo(20);
        assertThat(criteria.getMaxAge()).isEqualTo(35);
        assertThat(criteria.getMinSalary()).isEqualTo(10_000_000D);
        assertThat(criteria.getDepartmentId()).isEqualTo(1);
        assertThat(criteria.getPositionId()).isEqualTo(2);
    }
}


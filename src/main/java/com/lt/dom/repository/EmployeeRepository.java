package com.lt.dom.repository;

import com.lt.dom.oct.Employee;
import com.lt.dom.otcenum.EnumEmployeeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee
			, Long> {


    Page<Employee> findBySuplierId(long id, Pageable of);

    List<Employee> findBySuplierId(long id);

    Optional<Employee> findByUserIdAndStatus(long id, EnumEmployeeStatus active);

    List<Employee> findAllByUserId(long id);

    Page<Employee> findBySuplierIdAndStatus(long id, EnumEmployeeStatus inactive, Pageable pageable);
}
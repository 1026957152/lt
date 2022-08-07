package com.lt.dom.repository;

import com.lt.dom.oct.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee
			, Long> {

    Optional<Employee> findByUserId(long id);

    Page<Employee> findBySuplierId(long id, Pageable of);
}
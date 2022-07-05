package com.lt.dom.repository;

import com.lt.dom.oct.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee
			, Long> {

}
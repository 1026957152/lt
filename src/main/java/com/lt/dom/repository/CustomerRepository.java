package com.lt.dom.repository;

import com.lt.dom.oct.Customer;
import com.lt.dom.oct.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer
			, Long> , JpaSpecificationExecutor<Customer> {


}
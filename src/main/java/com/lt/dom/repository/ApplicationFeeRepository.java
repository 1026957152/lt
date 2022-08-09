package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ApplicationFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationFeeRepository extends JpaRepository<ApplicationFee
			, Long> {


    List<ApplicationFee> findByCharge(long id);
}
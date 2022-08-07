package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ApplyCertification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyCertificationRepository extends JpaRepository<ApplyCertification
			, Long> {


}
package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessValidatorRepository extends JpaRepository<AccessValidator
			, Long> {


	List<AccessValidator> findByValidatorId(long id);
}
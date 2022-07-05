package com.lt.dom.repository;

import com.lt.dom.oct.Validator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ValidatorRepository extends JpaRepository<Validator
			, Long> {

    List<Validator> findAllByUserId(String username);
}
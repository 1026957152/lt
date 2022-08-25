package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.RuleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleAssignmentRepository extends JpaRepository<RuleAssignment
			, Long> {



}
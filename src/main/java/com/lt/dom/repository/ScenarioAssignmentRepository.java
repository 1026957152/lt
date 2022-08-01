package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.ScenarioAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioAssignmentRepository extends JpaRepository<ScenarioAssignment
			, Long> {


	List<ScenarioAssignment> findByScenarioAndSupplier(long id, long id1);

    List<ScenarioAssignment> findByScenario(long id);

    List<ScenarioAssignment> findBySupplier(long id);
}
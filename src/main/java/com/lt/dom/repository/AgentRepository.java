package com.lt.dom.repository;

import com.lt.dom.oct.Agent;
import com.lt.dom.oct.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent
			, Long> {


    Optional<Agent> findByPartnerId(String pid);

}
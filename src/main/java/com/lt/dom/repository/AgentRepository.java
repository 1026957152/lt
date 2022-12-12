package com.lt.dom.repository;

import com.lt.dom.oct.AgentConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<AgentConnection
			, Long> {


    Optional<AgentConnection> findByPartnerId(String pid);

    Page<AgentConnection> findAllByAgent(Long id, Pageable pageable);

    List<AgentConnection> findAllByAgent(Long id);
}
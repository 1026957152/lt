package com.lt.dom.repository;

import com.lt.dom.oct.AgentConnection;
import com.lt.dom.oct.AgentProduct;
import com.lt.dom.oct.AgentProductKey;
import com.lt.dom.serviceOtc.MessageServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentProductRepository extends JpaRepository<AgentProduct
			, AgentProductKey> {


    Page<AgentProduct> findAllByAgentConnection(AgentConnection region, Pageable pageable);

    Optional<AgentProduct> findByCode(String id);
}
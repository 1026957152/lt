package com.lt.dom.repository;

import com.lt.dom.oct.AgentConnection;
import com.lt.dom.oct.AgentProduct;
import com.lt.dom.oct.AgentProductKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentProductRepository extends JpaRepository<AgentProduct
			, AgentProductKey> {


    Page<AgentProduct> findAllByAgent(AgentConnection region, Pageable pageable);
}
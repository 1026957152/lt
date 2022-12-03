package com.lt.dom.repository;

import com.lt.dom.oct.Agent;
import com.lt.dom.oct.AgentProduct;
import com.lt.dom.oct.AgentProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentProductRepository extends JpaRepository<AgentProduct
			, AgentProductKey> {




}
package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewordRepository extends JpaRepository<Reward
			, Long> {



}
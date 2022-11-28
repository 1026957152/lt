package com.lt.dom.repository;

import com.lt.dom.oct.Exhibition;
import com.lt.dom.oct.Museum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<Exhibition
			, Long> {


}
package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute
			, Long> {



}
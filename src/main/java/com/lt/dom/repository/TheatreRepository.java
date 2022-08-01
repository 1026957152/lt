package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheatreRepository extends JpaRepository<Theatre
			, Long> {


}
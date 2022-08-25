package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Publication;
import com.lt.dom.oct.PublicationEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication
			, Long> {



}
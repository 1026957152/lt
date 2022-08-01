package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.PublicationEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicationEntryRepository extends JpaRepository<PublicationEntry
			, Long> {


    List<PublicationEntry> findByVoucherId(long id);
}
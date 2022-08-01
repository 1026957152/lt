package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.RedemptionEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedemptionEntryRepository extends JpaRepository<RedemptionEntry
			, Long> {


	List<RedemptionEntry> findAllBySupplier(long id);

}
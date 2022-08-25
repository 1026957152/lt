package com.lt.dom.repository;

import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.PublicationEntry;
import com.lt.dom.oct.RedemptionEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RedemptionEntryRepository extends JpaRepository<RedemptionEntry
			, Long>, JpaSpecificationExecutor<RedemptionEntry> {


	List<RedemptionEntry> findAllBySupplier(long id);


	Page<RedemptionEntry> findAllBySupplier(long id, Pageable of);

    List<RedemptionEntry> findAllBySupplierAndCreatedAtAfter(long id, LocalDateTime atStartOfDay);

}
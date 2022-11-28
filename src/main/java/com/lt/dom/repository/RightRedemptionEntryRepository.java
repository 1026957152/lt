package com.lt.dom.repository;

import com.lt.dom.oct.RedemptionEntry;
import com.lt.dom.oct.RightRedemptionEntry;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface RightRedemptionEntryRepository extends JpaRepository<RightRedemptionEntry
			, Long>, JpaSpecificationExecutor<RedemptionEntry> {


    List<RightRedemptionEntry> findAllByRelatedObjectTypeAndRelatedObjectId(EnumRelatedObjectType voucher, Long id);

    List<RightRedemptionEntry> findAllByRedemption(long id);

}
package com.lt.dom.repository;

import com.lt.dom.oct.Asset;
import com.lt.dom.oct.BulkIssuanceCardRequest;
import com.lt.dom.otcReq.BulkIssuanceCardRequestReq;
import com.lt.dom.otcenum.EnumAssetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BulkIssuanceCardRequestRepository extends JpaRepository<BulkIssuanceCardRequest
			, Long> {


    Page<BulkIssuanceCardRequest> findAllBySupplier(long id, Pageable pageable);
}
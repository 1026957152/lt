package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner
			, Long> {


    Page<Partner> findAllBySupplier(long id, Pageable pageable);

    List<Partner> findAllBySupplier(long supplierId);

    List<Partner> findAllBySupplierAndPartner(Long supplier_id, Long partner_id);
}
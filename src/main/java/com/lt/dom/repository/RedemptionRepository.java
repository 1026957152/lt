package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Redemption;
import com.lt.dom.oct.RedemptionEntry;
import com.lt.dom.otcenum.EnumAssociatedType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedemptionRepository extends JpaRepository<Redemption
			, Long> {


	List<Redemption> findByRelatedObjectIdAndRelatedObjectType(long id, EnumAssociatedType voucher);

   // List<RedemptionEntry> findAllBySupplierAndRollback(long id, boolean b);

}
package com.lt.dom.repository;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.ValidatorGroup;
import com.lt.dom.oct.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher
			, Long> ,JpaSpecificationExecutor<Voucher> {

    Optional<Voucher> findFirstByCampaign(long id);

    Optional<Voucher> findByCode(String code);

    Optional<Voucher> findFirstByCampaignAndActive(long id, boolean b);

    Page<Voucher> findAllByPublishTo(long id, Pageable pageable);
}
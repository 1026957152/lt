package com.lt.dom.repository;

import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.ValidatorGroup;
import com.lt.dom.oct.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher
			, Long> {

    Optional<Voucher> findFirstByCampaign(long id);
}
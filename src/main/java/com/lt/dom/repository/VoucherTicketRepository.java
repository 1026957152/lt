package com.lt.dom.repository;

import com.lt.dom.oct.Voucher;
import com.lt.dom.oct.VoucherTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VoucherTicketRepository extends JpaRepository<VoucherTicket
			, Long> ,JpaSpecificationExecutor<Voucher> {


    Optional<VoucherTicket> findByCode(String code);
}
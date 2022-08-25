package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment
			, Long> , JpaSpecificationExecutor<Payment> {


    Optional<Payment> findByReference(String orderId);
    Optional<Payment> findByCode(String orderId);
}
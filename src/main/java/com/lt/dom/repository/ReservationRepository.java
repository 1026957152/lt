package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Reservation;
import com.lt.dom.otcenum.EnumBookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation
			, Long> {

    Optional<Reservation> findByCode(String reference);

    Page<Reservation> findAllByStatusIn(List<EnumBookingStatus> statusList, Pageable pageReq);

    Optional<Reservation> findByTrackingId(String orders_id);
}
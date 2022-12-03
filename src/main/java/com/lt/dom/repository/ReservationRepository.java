package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Reservation;
import com.lt.dom.oct.TourBooking;
import com.lt.dom.otcenum.EnumBookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation
			, Long>, JpaSpecificationExecutor<Reservation> {

    Optional<Reservation> findByCode(String reference);

    Page<Reservation> findAllByStatusIn(List<EnumBookingStatus> statusList, Pageable pageReq);

    Optional<Reservation> findByTrackingId(String orders_id);

    Page<Reservation> findAllByAgent(Long id, Pageable pageable);

}
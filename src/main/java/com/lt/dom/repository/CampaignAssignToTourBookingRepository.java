package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.CampaignAssignToTourBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampaignAssignToTourBookingRepository extends JpaRepository<CampaignAssignToTourBooking
			, Long> {


	List<CampaignAssignToTourBooking> findByTourBooking(long id);

    Page<CampaignAssignToTourBooking> findAllByTourBooking(long id, Pageable pageable);

    List<CampaignAssignToTourBooking> findAllByTourBookingIn(List<Long> collect);

    Optional<CampaignAssignToTourBooking> findByCode(String code);
}
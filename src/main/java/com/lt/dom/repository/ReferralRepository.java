package com.lt.dom.repository;

import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Referral;
import com.lt.dom.otcenum.EnumReferralType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReferralRepository extends JpaRepository<Referral
			, Long> {


    Optional<Referral> findByCode(String id);

    List<Referral> findByUserAndType(long id, EnumReferralType fill_up_passager_info);

}
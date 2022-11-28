package com.lt.dom.repository;

import com.lt.dom.oct.Invitation;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumInvitationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationsRepository extends JpaRepository<Invitation
			, Long> {

    List<Invitation> findAllByStatus(EnumInvitationStatus pending);

    Optional<Invitation> findByCode(String code);



    List<Invitation> findAllBySupplierAndPartnerName(long id, String companyName);

    Page<Invitation> findAllBySupplier(long id, Pageable pageable);
}
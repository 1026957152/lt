package com.lt.dom.repository;

import com.lt.dom.oct.Invitation;
import com.lt.dom.oct.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationsRepository extends JpaRepository<Invitation
			, Long> {

}
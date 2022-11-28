package com.lt.dom.repository;

import com.lt.dom.oct.Request;
import com.lt.dom.otcenum.EnumRequestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request
			, Long> {


    List<Request> findByOwner(long id);

    Optional<Request> findByTypeAndIdId(EnumRequestType tour_approve, String code);

    Optional<Request> findByOwnerAndType(Long user_id, EnumRequestType merchants_settled);

    Optional<Request> findByOwnerAndTypeAndActive(Long user_id, EnumRequestType merchants_settled, boolean b);
}
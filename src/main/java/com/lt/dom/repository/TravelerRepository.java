package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.LineItem;
import com.lt.dom.oct.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TravelerRepository extends JpaRepository<Traveler
			, Long> {


    List<Traveler> findAllByBooking(long id);

    List<Traveler> findAllByBookingIn(Set<Long> collect);

    List<Traveler> findAllByReferSku(String referTraveler);

    List<Traveler> findAllByLineItem(LineItem e);
}
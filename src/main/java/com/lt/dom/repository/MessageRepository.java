package com.lt.dom.repository;

import com.lt.dom.oct.Message;
import com.lt.dom.oct.SeatingLayout;
import com.lt.dom.oct.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message
			, Long> {


}
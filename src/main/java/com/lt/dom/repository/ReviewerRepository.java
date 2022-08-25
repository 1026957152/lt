package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewerRepository extends JpaRepository<Reviewer
			, Long> {


    List<Reviewer> findAllByRequestIn(List<Long> collect);

    List<Reviewer> findAllByRequest(long id);
}
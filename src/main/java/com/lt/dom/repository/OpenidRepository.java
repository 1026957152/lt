package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Openid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OpenidRepository extends JpaRepository<Openid
			, Long> {


	Optional<Openid> findByOpenid(String openid);





}
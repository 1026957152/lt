package com.lt.dom.repository;

import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Session;
import com.lt.dom.otcenum.EnumAssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session
			, Long> {


}
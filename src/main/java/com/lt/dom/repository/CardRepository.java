package com.lt.dom.repository;

import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Card;
import com.lt.dom.otcenum.EnumAssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CardRepository extends JpaRepository<Card
			, Long> {

}
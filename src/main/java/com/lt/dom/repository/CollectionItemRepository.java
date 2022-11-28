package com.lt.dom.repository;

import com.lt.dom.oct.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionItemRepository extends JpaRepository<Artwork
			, Long> {


}
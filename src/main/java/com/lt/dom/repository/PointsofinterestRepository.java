package com.lt.dom.repository;

import com.lt.dom.oct.Artwork;
import com.lt.dom.oct.Pointsofinterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsofinterestRepository extends JpaRepository<
		Pointsofinterest
			, Long> {


}
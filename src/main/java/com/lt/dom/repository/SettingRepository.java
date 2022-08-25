package com.lt.dom.repository;

import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Setting;
import com.lt.dom.otcenum.EnumAssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting
			, Long> {


	Setting findByName(String name);
}
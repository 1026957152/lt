package com.lt.dom.repository;

import com.lt.dom.oct.AccessValidator;
import com.lt.dom.oct.Asset;
import com.lt.dom.otcenum.EnumAssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset
			, Long> {


    List<Asset> findAllBySource(long id);

    List<Asset> findAllByTypeAndSourceIn(EnumAssetType qr, List<Long> collect);
    List<Asset> findAllByTypeAndIdIdIn(EnumAssetType qr, List<String> collect);
    List<Asset> findAllBySourceIn( List<Long> collect);

    Optional<Asset> findByTypeAndSource(EnumAssetType qr, long id);

    Optional<Asset> findByTypeAndIdId(EnumAssetType qr, String code);
}
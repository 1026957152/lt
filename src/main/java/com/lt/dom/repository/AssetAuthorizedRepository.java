package com.lt.dom.repository;

import com.lt.dom.oct.AssetAuthorized;
import com.lt.dom.oct.AssetDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetAuthorizedRepository extends JpaRepository<AssetAuthorized, Long> {


    List<AssetAuthorized> findAllByUid(long id);

    Optional<AssetAuthorized> findAllByAssetId(long id);
}
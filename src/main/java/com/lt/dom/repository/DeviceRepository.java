package com.lt.dom.repository;

import com.lt.dom.oct.AssetAuthorized;
import com.lt.dom.oct.Device;
import com.lt.dom.oct.DeviceMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {


    List<Device> findByAssetId(long id);

    List<Device> findByAssetIdIn(List<Long> collect);

    Optional<Device> findByCode(String device);


}
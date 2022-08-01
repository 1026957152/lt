package com.lt.dom.serviceOtc;


import com.lt.dom.config.LtConfig;
import com.lt.dom.config.WxPayConfig;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.repository.AssetRepository;
import com.lt.dom.repository.BookingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl {

    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private AssetRepository assetRepository;



    public Asset newQr(Supplier supplier) {

        Asset asset = new Asset();
        asset.setType(EnumAssetType.qr);
        asset.setSource(supplier.getId());
        asset.setIdId(supplier.getCode());
        asset.setUrl(ltConfig.getUrl()+"/q/"+asset.getIdId());
        asset = assetRepository.save(asset);

        return asset;
    }

    public List<Asset> newQr(List<Voucher> vouchers) {

        List<Asset> assets = vouchers.stream().map(x->{
            Asset asset = new Asset();
            asset.setIdId(x.getCode());
            asset.setSource(x.getId());
            asset.setType(EnumAssetType.qr);
            asset.setUrl(ltConfig.Url(x.getCode()));

            return asset;

        }).collect(Collectors.toList());

        return assets;
    }
}

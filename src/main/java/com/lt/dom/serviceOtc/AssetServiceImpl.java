package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.config.WxPayConfig;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.repository.AssetRepository;
import com.lt.dom.repository.BookingRuleRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl {

    @Autowired
    private LtConfig ltConfig;

    @Autowired
    private AssetRepository assetRepository;

    public Asset newQr(Campaign campaign) {

        Asset asset = new Asset();
        asset.setType(EnumAssetType.qr);
        asset.setSource(campaign.getId());
        asset.setIdId(campaign.getCode());
        asset.setUrl(ltConfig.getUrl()+"/q/"+asset.getIdId());
        asset = assetRepository.save(asset);

        return asset;
    }


    public Asset newQr(Supplier supplier) {

        Asset asset = new Asset();
        asset.setType(EnumAssetType.qr);
        asset.setSource(supplier.getId());
        asset.setIdId(supplier.getCode());
        asset.setUrl(ltConfig.getUrl()+"/q/"+asset.getIdId());
        asset = assetRepository.save(asset);

        return asset;
    }

    public List<Asset> newQrWithSave(List<Pair<String, Long>> collect) {
        List<Asset> assets = collect.stream().map(x->{

            Asset asset = new Asset();
            asset.setIdId(x.getValue0());
            asset.setSource(x.getValue1());
            asset.setType(EnumAssetType.qr);
            asset.setUrl(ltConfig.Url(x.getValue0()));

            return asset;

        }).collect(Collectors.toList());

        return assetRepository.saveAll(assets);
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

    public EntityModel<AssetResp> getQr(Campaign booking) {

        Optional<Asset> assets = assetRepository.findByTypeAndIdId(EnumAssetType.qr,booking.getCode());

        return AssetResp.from(assets.get());
    }

    public List<Asset> getQr(String code) {
        Optional<Asset> assets = assetRepository.findByTypeAndIdId(EnumAssetType.qr,code);

        return Arrays.asList(assets.get());
    }
    public Optional<Asset> getQrOptional(String code) {
        Optional<Asset> assets = assetRepository.findByTypeAndIdId(EnumAssetType.qr,code);

        return assets;
    }

    public Asset newQr(User user) {

        return newQr(user.getCode(),user.getId());

    }

    public Asset newQr(String code,long id) {
        Asset asset = new Asset();
        asset.setType(EnumAssetType.qr);
        asset.setSource(id);
        asset.setIdId(code);
        asset.setUrl(ltConfig.getUrl()+"/q/"+asset.getIdId());
        asset = assetRepository.save(asset);

        return asset;
    }

    public Asset newQr(TourBooking tourBooking) {
        return newQr(tourBooking.getCode(),tourBooking.getId());
    }


    public Map<String, Asset> getQrs(List<String> collect) {

        List<Asset> assets = assetRepository.findByTypeAndIdIdIn(EnumAssetType.qr,new HashSet(collect));

        return assets.stream().collect(Collectors.toMap(e->e.getIdId(),e->e));
    }
}

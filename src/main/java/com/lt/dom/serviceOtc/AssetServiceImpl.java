package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.config.LtConfig;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.repository.AssetRepository;
import com.lt.dom.util.ZxingBarcodeGenerator;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class AssetServiceImpl {

    @Autowired
    private LtConfig ltConfig;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private IdGenServiceImpl idGenService;

    public Asset getWithNew(Campaign campaign) {

        Asset asset = new Asset();
        asset.setType(EnumAssetType.qr);
        asset.setSource(campaign.getId());
        asset.setIdId(campaign.getCode());
        asset.setUrl(ltConfig.getUrl()+"/q/"+asset.getIdId());
        asset = assetRepository.save(asset);

        return asset;
    }


    @Transactional
    public Asset regenerate(Asset asset) {


    //    Asset asset = new Asset();
        asset.setType(EnumAssetType.unvalid);
        asset = assetRepository.save(asset);

        return asset;
    }

    public Asset getWithNew(Supplier supplier) {

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
    public List<Asset> getWithNew(List<Voucher> vouchers) {

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
    public EntityModel<AssetResp> getQrEntityModel(String code) {
        Optional<Asset> assets = assetRepository.findByTypeAndIdId(EnumAssetType.qr,code);
        if(assets.isPresent()){
            return AssetResp.from(assets.get());
        }else{
            return null;
        }

    }

    public Optional<Asset> getQrOptional(String code) {
        Optional<Asset> assets = assetRepository.findByTypeAndIdId(EnumAssetType.qr,code);

        return assets;
    }

    public Asset getWithNew(User user) {

        return getWithNew(user.getCode(),user.getId());

    }

    public Asset getWithNew(String code, long id) {
        Asset asset = new Asset();
        asset.setType(EnumAssetType.qr);
        asset.setSource(id);
        asset.setIdId(code);
        asset.setUrl(ltConfig.getUrl()+"/q/"+asset.getIdId());


        asset = assetRepository.save(asset);

        return asset;
    }

    public Asset getWithNew(String code, Long id, EnumAssetType type) {

        Optional<Asset> assets = assetRepository.findByTypeAndIdId(type,code);

        if(assets.isEmpty()){
            Asset asset = new Asset();
            asset.setType(type);
            asset.setCode(idGenService.nextId(""));
            asset.setSource(id);
            asset.setIdId(code);
            asset.setUrl(ltConfig.getUrl()+"/q/"+asset.getIdId());

            asset = assetRepository.save(asset);
/*            asset.setLinkCode(asset.getId()+"");
            asset = assetRepository.save(asset);*/
            return asset;
        }


        return assets.get();
    }


    public Asset getWithNew(TourBooking tourBooking) {
        return getWithNew(tourBooking.getCode(),tourBooking.getId());
    }


    public Map<String, Asset> getQrs(List<String> collect) {

        List<Asset> assets = assetRepository.findByTypeAndIdIdIn(EnumAssetType.qr,new HashSet(collect));

        return assets.stream().collect(Collectors.toMap(e->e.getIdId(),e->e));
    }
}

package com.lt.dom.serviceOtc;

import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.OctResp.PublicationEntryResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.VoucherResp;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class VonchorServiceImpl {
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private PublicationEntryRepository publicationEntryRepository;
    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private AssetRepository assetRepository;

    public List<ComponentVounch> get票的权益(Voucher voucher){

        List<ComponentVounch> componentVounches = componentVounchRepository.findByVoucherId(voucher.getId());


        return componentVounches;
/*
        referral.get
        if(royaltyRule.getRoyaltyRuleData().stream().filter(x -> (x.getLevel() == referral.getLevel())).findAny().isPresent()){

        }
        referral.getLevel();
        return royaltyRule.getRoyaltyRuleData().stream().map(x-> {
            x.getLevel();
            Royalty royalty = new Royalty();
            royalty.setSettle_account(user.getSettleAccount());
            return royalty;
        }).collect(Collectors.toList());

*/


    }


    public VoucherResp getVoucher(Voucher voucher) {
        VoucherResp voucherResp = new VoucherResp();
        voucherResp.setCode(voucher.getCode());
        voucherResp.setType(voucher.getType());
        Optional<Campaign> optionalCompaign = campaignRepository.findById(voucher.getCampaign());

        if(optionalCompaign.isPresent()){
            voucherResp.setCampaign(optionalCompaign.get().getName());
        }
        if(voucher.getType().equals(EnumVoucherType.DISCOUNT_VOUCHER)){
            Optional<Discount> discount = discountRepository.findById(voucher.getRelateId());

            VoucherResp.DiscountPojo discountPojo = new VoucherResp.DiscountPojo();
            discountPojo.setCategory(discount.get().getType());
            if(discount.get().getType().equals(EnumDiscountVoucherCategory.AMOUNT)){
                discountPojo.setAmount_off(discount.get().getAmount_off());

            }
            if(discount.get().getType().equals(EnumDiscountVoucherCategory.PERCENT)){
                discountPojo.setPercent_off(discount.get().getPercent_off());

            }
            if(discount.get().getType().equals(EnumDiscountVoucherCategory.UNIT)){
                discountPojo.setUnit_off(discount.get().getUnit_off());

            }
            voucherResp.setDiscount(discountPojo);
        }

        if(voucher.getPublished()){

            List<PublicationEntry> publicationEntryList = publicationEntryRepository.findByVoucherId(voucher.getId());
            List<PublicationEntryResp> publicationEntryResps = publicationEntryList.stream().map(x->{
                PublicationEntryResp resp = new PublicationEntryResp();
                resp.setType(x.getToWhoType());
                resp.setCampaign(optionalCompaign.get().getCode());
                return resp;
            }).collect(Collectors.toList());

            VoucherResp.PublicationPojo pojo = new VoucherResp.PublicationPojo();
            pojo.setCount(publicationEntryList.size());
            pojo.setEntries(publicationEntryResps);
            voucherResp.setPublication(pojo);
        }


        List<Asset> assets = assetRepository.findAllBySource(voucher.getId());

        Optional<Asset> asset = assets.stream().filter(x->x.getType().equals(EnumAssetType.qr)).findAny();

        if(asset.isPresent()){
            voucherResp.setAssets(AssetResp.from(asset.get()));
        }
        return voucherResp;
    }
}

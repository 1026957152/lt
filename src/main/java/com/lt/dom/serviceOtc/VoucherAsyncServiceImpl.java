package com.lt.dom.serviceOtc;


import com.lt.dom.config.LtConfig;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.util.VoucherCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Service
public class VoucherAsyncServiceImpl {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private LtConfig ltConfig;


    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetServiceImpl assetService;

    @Async
    @Transactional
    public void 异步新建(CodeConfig config, Campaign campaign) {


        Optional<Campaign> campaign1 = campaignRepository.findById(campaign.getId());

        System.out.println(campaign.getId() + "异步执行了，找到了？"+campaign1.isPresent()+" 看一看 他的 code 啊"+campaign.getCode());
        Campaign finalCampaign = campaign;



        List<Voucher> vouchers = LongStream.range(0,campaign.getVoucher_count()).boxed().map(x->{
            Voucher voucher = new Voucher();
            String no = VoucherCodes.generate(config);
            System.out.println("Execute method asynchronously. "
                    + no);
            voucher.setCode(no);
            voucher.setType(finalCampaign.getVouchertype());

            voucher.setCampaign(finalCampaign.getId());
            voucher.setStatus(EnumVoucherStatus.Created);
            voucher.setPublished(false);

            voucher.setActive(false);
            return voucher;
        }).collect(Collectors.toList());


        vouchers = voucherRepository.saveAll(vouchers);

        Map<Long, Voucher>  voucherMap = vouchers.stream().collect(Collectors.toMap(Voucher::getId, Voucher->Voucher));


        List<Discount> discounts = vouchers.stream().map(x->{
            Discount discount = new Discount();
            discount.setVoucher(x.getId());
            discount.setType(finalCampaign.getDiscountCategory());
            if(discount.getType().equals(EnumDiscountVoucherCategory.AMOUNT)){
                discount.setAmount_off(finalCampaign.getAmount_off());
            }
            if(discount.getType().equals(EnumDiscountVoucherCategory.PERCENT)){
                discount.setPercent_off(finalCampaign.getPercent_off());
            }
            if(discount.getType().equals(EnumDiscountVoucherCategory.UNIT)){
                discount.setUnit_off(finalCampaign.getUnit_off());
            }
            return discount;
        }).collect(Collectors.toList());


        List<Discount> pDiscounts = discountRepository.saveAll(discounts);



        List<Voucher> voucherList = pDiscounts.stream().map(x->{
            Voucher voucher = voucherMap.get(x.getVoucher());
            voucher.setRelateId(x.getId());
            return voucher;
        }).collect(Collectors.toList());

        vouchers = voucherRepository.saveAll(voucherList);


        List<Asset> assets = assetService.newQr(vouchers);

        assets = assetRepository.saveAll(assets);





        //Optional<Campaign> optional = campaignRepository.findById(campaign.getId());


        System.out.println(finalCampaign.getCode()+ finalCampaign.getName()+"我这里异步存储一下 Execute method asynchronously. "
                + Thread.currentThread().getName());
        campaign.setVouchers_generation_status(EnumCampaignCreationStatus.DONE);
        campaign = campaignRepository.save(finalCampaign);
    }







}

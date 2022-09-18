package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.CampaignRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.vo.WrittenOffMerchantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryServiceImpl {


    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    public void redemptionSummary(RedemptionEntry entry, Campaign campaign, WrittenOffMerchantVo writtenOffMerchantVo) {
/*        campaign.setTotal_redeemed(campaign.getTotal_redeemed()+1);
        campaign.setTotal_succeeded(campaign.getTotal_redeemed()+1);

        campaignRepository.save(campaign);*/

        Supplier supplier = writtenOffMerchantVo.getSupplier();
        supplier.setTotal_redeemed(campaign.getTotal_redeemed()+1);
        supplier.setTotal_succeeded(campaign.getTotal_redeemed()+1);
        supplierRepository.save(supplier);
    }


    public void rollbackRedemptionSummary(RedemptionEntry entry, Campaign campaign, Supplier supplier) {
        campaign.setTotal_redeemed(campaign.getTotal_redeemed()+1);
        campaign.setTotal_succeeded(campaign.getTotal_redeemed()+1);

        campaign.setTotal_rolled_back(campaign.getTotal_rolled_back()+1);
        campaign.setTotal_rollback_succeeded(campaign.getTotal_rollback_succeeded()+1);

        campaignRepository.save(campaign);

        supplier.setTotal_redeemed(campaign.getTotal_redeemed()+1);
        supplier.setTotal_succeeded(campaign.getTotal_redeemed()+1);
        supplier.setTotal_rolled_back(campaign.getTotal_rolled_back()+1);
        supplier.setTotal_rollback_succeeded(campaign.getTotal_rollback_succeeded()+1);
        supplierRepository.save(supplier);
    }
}

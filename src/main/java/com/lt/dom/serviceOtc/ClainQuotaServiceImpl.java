package com.lt.dom.serviceOtc;


import com.lt.dom.error.Quantity_exceededException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ClainQuotaReq;
import com.lt.dom.otcReq.QuotaReq;
import com.lt.dom.otcenum.EnumClainQuotaType;
import com.lt.dom.otcenum.EnumQuotaType;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.ClainQuotaRepository;
import com.lt.dom.repository.QuotaRepository;
import com.lt.dom.repository.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClainQuotaServiceImpl {


    @Autowired
    private QuotaRepository quotaRepository;


    @Autowired
    private ScenarioRepository scenarioRepository;


    @Autowired
    private ClainQuotaRepository clainQuotaRepository;



    public ClainQuota createQuota(Campaign campaign, ClainQuotaReq clainQuotaReq) {
        ClainQuota probe = new ClainQuota();
        probe.setCompaign(campaign.getId());
        Example<ClainQuota> example = Example.of(probe);

        List<ClainQuota> quotas = clainQuotaRepository.findAll(example);


        long count = campaign.getVouchers_count() - quotas.stream().mapToLong(x->x.getQuota()).sum();

        if(count >= clainQuotaReq.getQuota()){



            ClainQuota quota = new ClainQuota();
            quota.setQuota(clainQuotaReq.getQuota());
            quota.setCompaign(campaign.getId());
            quota.setType(clainQuotaReq.getType());

            if(quota.getType().equals(EnumClainQuotaType.supplier__id)){
                quota.setSupplier(clainQuotaReq.getSupplier());
            }
            if(quota.getType().equals(EnumClainQuotaType.supplier__scenario)){

                Optional<Scenario> scenario = scenarioRepository.findById(clainQuotaReq.getScenario());
                if(scenario.isPresent()){
                    quota.setScenario(clainQuotaReq.getScenario());
                }else{
                    throw new RuntimeException("找不到 scenario");
                }

            }
            if(quota.getType().equals(EnumClainQuotaType.customer__segment)){

            }
            quota = clainQuotaRepository.save(quota);


            return quota;
        }

        throw new RuntimeException();

    }


    public Quota createQuota(Campaign campaign, QuotaReq clainQuotaReq) {
        Quota probe = new Quota();
        probe.setCompaign(campaign.getId());
        Example<Quota> example = Example.of(probe);

        List<Quota> quotas = quotaRepository.findAll(example);


        long count = campaign.getVouchers_count() - quotas.stream().mapToLong(x->x.getQuota()).sum();


        if(count < clainQuotaReq.getQuota()) {

            throw new Quantity_exceededException(campaign.getId()," 分配配额超过活动 计划数量");

        }

            Quota quota = new Quota();
            quota.setQuota(clainQuotaReq.getQuota());
        quota.setClaim_redeem(clainQuotaReq.getClaim_redeem());
        quota.setName(clainQuotaReq.getName());

            quota.setCompaign(campaign.getId());
            quota.setType(clainQuotaReq.getType());
            if(quota.getType().equals(EnumQuotaType.NominatedSupplier)){
                quota.setSupplier(clainQuotaReq.getSupplier());
            }
            if(quota.getType().equals(EnumQuotaType.Scenario)){

                Optional<Scenario> scenario = scenarioRepository.findById(clainQuotaReq.getScenario());
                if(scenario.isPresent()){
                    quota.setScenario(clainQuotaReq.getScenario());
                }else{
                    throw new RuntimeException("找不到 scenario");
                }

            }

            quota = quotaRepository.save(quota);


            return quota;



    }

}

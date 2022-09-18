package com.lt.dom.serviceOtc;


import com.lt.dom.JwtUtils;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Quantity_exceededException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ClainQuotaReq;
import com.lt.dom.otcReq.QuotaReq;
import com.lt.dom.otcReq.ValueListItemReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.apache.tools.ant.taskdefs.PathConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClainQuotaServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ClainQuotaServiceImpl.class);

    @Autowired
    private QuotaRepository quotaRepository;


    @Autowired
    private ScenarioRepository scenarioRepository;
    @Autowired
    private SupplierRepository supplierRepository;


    @Autowired
    private ClainQuotaRepository clainQuotaRepository;
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private ValueListServiceImpl valueListService;

    @Autowired
    private ValueListRepository valueListRepository;


    @Autowired
    private ValueListItemRepository valueListItemRepository;


    public ClainQuota createQuota(Campaign campaign, ClainQuotaReq clainQuotaReq) {
        ClainQuota probe = new ClainQuota();
        probe.setCompaign(campaign.getId());
        Example<ClainQuota> example = Example.of(probe);

        List<ClainQuota> quotas = clainQuotaRepository.findAll(example);


        long count = campaign.getVoucher_count() - quotas.stream().mapToLong(x->x.getQuota()).sum();

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



            if(quota.getType().equals(EnumClainQuotaType.customer__list)){



/*                ValueListReq valueListReq = new ValueListReq();
                valueListReq.setAlias("");
                valueListReq.setItem_type(EnumValueListItemType.customer_id);
                valueListReq.setName("");
                ValueList valueList = valueListService.createValueList(valueListReq);*/


                quota.setType(EnumClainQuotaType.customer__list);

            }


            quota = clainQuotaRepository.save(quota);


            return quota;
        }

        throw new RuntimeException();

    }


    public Quota createQuota(Campaign campaign, EnumQuotaClaimOrRedeem claimOrRedeem, QuotaReq clainQuotaReq) {
/*        Quota probe = new Quota();
        probe.setCompaign(campaign.getId());
        Example<Quota> example = Example.of(probe);*/



        List<Quota> quotas = quotaRepository.findByCompaignAndClaimRedeem(campaign.getId(),claimOrRedeem);

        long count = campaign.getVoucher_count() - quotas.stream().mapToLong(x->x.getQuota()).sum();

        if(count < clainQuotaReq.getQuota()) {
            throw new Quantity_exceededException(campaign.getId()," 分配配额超过活动 计划数量");
        }


        Quota quota = new Quota();
        quota.setQuota(clainQuotaReq.getQuota());
        quota.setClaimRedeem(claimOrRedeem);
        quota.setName(clainQuotaReq.getName());

        quota.setCode(idGenService.quotaNo());

        quota.setCompaign(campaign.getId());
        quota.setType(clainQuotaReq.getType());
        if(quota.getType().equals(EnumQuotaType.NominatedSupplier)){

            boolean exist = supplierRepository.existsById(clainQuotaReq.getSupplier());
            if(exist) {
                throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 公司");

            }
            quota.setSupplier(Integer.valueOf((int)clainQuotaReq.getId()).longValue());
            quota.setName("clainQuotaReq.getName()");
/*
            ValueListReq valueListReq = new ValueListReq();
            valueListReq.setAlias("");
            valueListReq.setType("inner");
            valueListReq.setItem_type(EnumValueListItemType.customer_id);
            valueListReq.setName("");
            ValueList valueList = valueListService.createValueList(valueListReq);
            quota.setValueList(valueList.getId());*/


        }
        if(quota.getType().equals(EnumQuotaType.supplier_groups)){

            long id = Integer.valueOf((int)clainQuotaReq.getId()).longValue();

            Optional<ValueList> optionalValueList = valueListRepository.findById(id);
            if(optionalValueList.isEmpty()){
                throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到元组数据");

            }
            ValueList valueList = optionalValueList.get();

            quota.setValueList(valueList.getId());

        }



        if(quota.getType().equals(EnumQuotaType.Scenario)){
/*            boolean exist = scenarioRepository.existsById(clainQuotaReq.getScenario());
            if(exist) {
                throw new BookNotFoundException(Enumfailures.resource_not_found,"找不到 Scenaria");

            }*/
            quota.setScenario(Integer.valueOf((int)clainQuotaReq.getId()).longValue());
            quota.setName("clainQuotaReq.getName()");
        }

        quota = quotaRepository.save(quota);
        return quota;



    }













    public boolean validate_writenOff(Supplier supplier,Campaign campaign) {

        String logger = String.format("核验商户是否有权限: %s---------%s", supplier.getId(),campaign.getId());

        System.out.println(logger );


        List<Quota> quotaList = quotaRepository.findByCompaignAndClaimRedeem(campaign.getId(),EnumQuotaClaimOrRedeem.redeem);


        List<ValueListItem> valueListItemList = valueListItemRepository.findAllById(quotaList.stream().map(e->e.getValueList()).collect(Collectors.toList()));

        Map<Long,List<ValueListItem> > longListMap =  valueListItemList.stream().collect(Collectors.groupingBy(e->e.getValueList()));


        Map<Quota,List<ValueListItem> > quotaListMap = quotaList.stream().collect(Collectors.toMap(e->e,e->longListMap.get(e.getValueList())));


        quotaListMap.entrySet().stream().forEach(x->{
            System.out.println("获得 获得的 配额"+ x.getKey());
            System.out.println("获得 获得的 配额"+ x.getValue());
        });

     //   boolean exist =  valueListItemList.stream().filter(e->Long.valueOf(e.getValue()).longValue() == supplier.getId()).findAny().isPresent();




        boolean quota_exist = quotaListMap.entrySet().stream().filter(x->{
           return x.getValue().stream().filter(xx->Long.valueOf(xx.getValue()) == supplier.getId()).findAny().isPresent();

        }).filter(x->{
            return x.getKey().getTotal_redeemed() < x.getKey().getQuota();
        }).findAny().isPresent();

        return quota_exist;
    }





    public void writenOff(Supplier supplier,Campaign campaign) {


        List<Quota> quotaList = quotaRepository.findByCompaignAndClaimRedeem(campaign.getId(),EnumQuotaClaimOrRedeem.redeem);


        List<ValueListItem> valueListItemList = valueListItemRepository.findAllById(quotaList.stream().map(e->e.getValueList()).collect(Collectors.toList()));

        Map<Long,List<ValueListItem> > longListMap =  valueListItemList.stream().collect(Collectors.groupingBy(e->e.getValueList()));



        Map<Quota,List<ValueListItem> > quotaListMap = quotaList.stream().collect(Collectors.toMap(e->e,e->longListMap.get(e.getValueList())));




      //  List<Map.Entry<Quota,List<ValueListItem>>> AA  =quotaListMap.entrySet().stream().filter(e-> e.getValue().stream().filter(ee->Long.valueOf(ee.getValue()) == supplier.getId()).findAny().isPresent()).collect(Collectors.toList());

        List<Quota> quota_exist = quotaListMap.entrySet().stream().filter(x->{

            System.out.println(" 便利所有的 配额 ");
            return x.getValue().stream().filter(xx->{

                System.out.println(" 遍历  配额  的 item list "+ xx.getValue() + " " + supplier.getId());
                return Long.valueOf(xx.getValue()).equals(supplier.getId());
            }).findAny().isPresent();

        }).filter(x->{
            return x.getKey().getTotal_redeemed() <= x.getKey().getQuota();
        }).map(x->x.getKey()).collect(Collectors.toList());

/*        List<Quota> quotaList1 = AA.stream().map(e->{
            Quota quota = e.getKey();
            quota.setTotal_redeemed(quota.getTotal_redeemed()+1);

            return quota;
        }).collect(Collectors.toList());*/

        List<Quota> quotaList1 = quota_exist.stream().map(x->{

            x.setTotal_redeemed(x.getTotal_redeemed()+1);

            return x;
        }).collect(Collectors.toList());

        logger.debug("{配额数据统计局 跟新 之前 {}}", quota_exist);
        System.out.println("{配额数据统计局 跟新 之前 {}}"+ quota_exist);
        quota_exist = quotaRepository.saveAll(quotaList1);
        logger.debug("{配额数据统计局 跟新 之后 {}}", quota_exist);
        System.out.println("{配额数据统计局 跟新 之后 {}}"+quota_exist);
    }





    }

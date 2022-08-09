package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.ClainQuotaStatisticsResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CompaignPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.util.CodeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl {
    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private QuotaRepository quotaRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private VoucherAsyncServiceImpl voucherAsyncService;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ScenarioRepository scenarioRepository;


    @Autowired
    private AssetServiceImpl assetService;





    public Campaign createLoyaltyCompaign(CompaignPojo compaignPojo, Scenario scenario)  {

        if(!compaignPojo.getCampaignType().equals(EnumCompaignType.LOYALTY_PROGRAM)){
            throw  new RuntimeException("");
        }


        Campaign campaign = new Campaign();
        campaign.setClaim_note(compaignPojo.getClaim_note());
        campaign.setClaim_text(compaignPojo.getClaim_text());
        campaign.setClain_limit(compaignPojo.getClain_limit());
        campaign.setClaimable(compaignPojo.isClaimable());
        campaign.setExpiry_days(compaignPojo.getExpiry_days());
        campaign.setActive(compaignPojo.isActive());

        campaign.setCode(idGenService.campaignNo());
        campaign.setName(compaignPojo.getName());
        campaign.setDescription(compaignPojo.getName_long());
        campaign.setScenario(scenario.getId());

        campaign.setCampaignType(compaignPojo.getCampaignType());
        campaign.setVouchers_generation_status(EnumCampaignCreationStatus.IN_PROGRESS);
        campaign.setVouchers_count(compaignPojo.getVouchers_count());
        campaign.setVouchertype(compaignPojo.getVoucher().getType());
       // campaign.setScenario(compaignPojo.getScenario());
        campaign.setCategory(compaignPojo.getCategory());
        campaign.setStart_date(compaignPojo.getStart_date());
        campaign.setExpiration_date(compaignPojo.getExpiration_date());

        if(compaignPojo.getVoucher().getType() == EnumVoucherType.DISCOUNT_VOUCHER){
            campaign.setDiscountCategory(compaignPojo.getVoucher().getCategory());

            if(compaignPojo.getVoucher().getCategory() == EnumDiscountVoucherCategory.AMOUNT){
                campaign.setAmount_off(compaignPojo.getVoucher().getAmount_off());

            }
            if(compaignPojo.getVoucher().getCategory() == EnumDiscountVoucherCategory.PERCENT){
                campaign.setPercent_off(compaignPojo.getVoucher().getPercent_off());

            }
            if(compaignPojo.getVoucher().getCategory() == EnumDiscountVoucherCategory.UNIT){
                campaign.setUnit_off(compaignPojo.getVoucher().getUnit_off());

            }
        }
        CompaignPojo.code_config code_config = compaignPojo.getCode_config();







        CodeConfig config = new CodeConfig(code_config.getLength(),code_config.getCharset(),code_config.getPrefix(),code_config.getPostfix(),code_config.getPattern());

        campaign.setLength(config.getLength());
        campaign.setCharset(config.getCharset());
        campaign.setPrefix(config.getPrefix());
        campaign.setPostfix(config.getPostfix());
        campaign.setPattern(config.getPattern());


        campaign = campaignRepository.save(campaign);

        assetService.newQr(campaign);


        voucherAsyncService.异步新建(config, campaign);







        return campaign;
    }








    public static void computer() {
        List<Integer> template = Arrays.asList(1, 2, 3, 4, 5, 6);
        Random r = new Random();
        StringBuilder random = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            int create = r.nextInt(6);
            // find in array between 1-6 index
            int find = template.get(create);
            random.append(String.valueOf(find));
        }
        System.out.println(random);
    }

    public int 获得折扣金额(Product product, List<Voucher> vouchers) {


        return 0;
    }

    public List<Voucher> 获得各种折扣券(Reservation reservation) {
        return null;
    }



    public Page<Quota> pageQuota(Campaign campaign, Pageable pageable) {
        return quotaRepository.findByCompaign(campaign.getId(),pageable);
    }

    public ClainQuotaStatisticsResp getQuotaStatistics(Campaign campaign) {

        List<Quota> quotas = quotaRepository.findByCompaign(campaign.getId());

        ClainQuotaStatisticsResp clainQuotaStatisticsResp = new ClainQuotaStatisticsResp();
        clainQuotaStatisticsResp.setTotalCount(campaign.getVouchers_count());
        clainQuotaStatisticsResp.setTotalAmount(campaign.getVouchers_count()*campaign.getAmount_off());
        clainQuotaStatisticsResp.setAllocatedQuotaCount(quotas.size());
        clainQuotaStatisticsResp.setAllocatedVoucherCount(quotas.stream().mapToLong(x->x.getQuota()).sum());
        clainQuotaStatisticsResp.setUnAllocatedVoucherCount(campaign.getVouchers_count() - quotas.stream().mapToLong(x->x.getQuota()).sum());


        List<ClainQuotaStatisticsResp.ClainQuotaPojo> clainQuotaPojos =  quotas.stream().filter(x->x.getType().equals(EnumQuotaType.Scenario)).map(x->{
            Optional<Scenario> scenario = scenarioRepository.findById(x.getScenario());
            ClainQuotaStatisticsResp.ClainQuotaPojo clainQuotaPojo = new ClainQuotaStatisticsResp.ClainQuotaPojo();
            clainQuotaPojo.setType(x.getType());
            clainQuotaPojo.setQuota(x.getQuota());
            clainQuotaPojo.setScenarioCode(scenario.get().getCode());
            clainQuotaPojo.setScenario(scenario.get().getName());
            return clainQuotaPojo;
        }).collect(Collectors.toList());

        clainQuotaStatisticsResp.setClainQuotas(clainQuotaPojos);



        ClainQuotaStatisticsResp.Redemptions redemptions = new ClainQuotaStatisticsResp.Redemptions();
        redemptions.setTotal_redeemed(0);
        redemptions.setTotal_failed(0);
        clainQuotaStatisticsResp.setRedemptions(redemptions);
        return clainQuotaStatisticsResp;
    }
}

package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.ClainQuotaStatisticsResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CompaignPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.vo.RedemptionForCustomerVo;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.lt.dom.serviceOtc.JsonParse.GSON;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;


@Service
public class VoucherServiceImpl {
    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private QuotaRepository quotaRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private VoucherTicketRepository voucherTicketRepository;
    @Autowired
    private CardholderRepository cardholderRepository;



    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ScenarioRepository scenarioRepository;


    @Autowired
    private AssetServiceImpl assetService;






    @Transactional(propagation = NOT_SUPPORTED) // we're going to handle transactions manually
    public Pair<CodeConfig,Campaign> createLoyaltyCompaign(CompaignPojo compaignPojo, Scenario scenario)  {
        String code  = idGenService.campaignNo();

        System.out.println(code+"新建啊啊"+compaignPojo.getName());
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
        campaign.setPay(compaignPojo.isPay());

        campaign.setPayAmount(compaignPojo.getPay_amount());

        campaign.setCode(code);

        campaign.setName(compaignPojo.getName());
        campaign.setName_long(compaignPojo.getName_long());
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
        CompaignPojo.Code_config code_config = compaignPojo.getCode_config()!=null?compaignPojo.getCode_config():(new CompaignPojo.Code_config());





        CodeConfig config = new CodeConfig(code_config.getLength(),code_config.getCharset(),code_config.getPrefix(),code_config.getPostfix(),code_config.getPattern());

        campaign.setLength(config.getLength());
        campaign.setCharset(config.getCharset());
        campaign.setPrefix(config.getPrefix());
        campaign.setPostfix(config.getPostfix());
        campaign.setPattern(config.getPattern());


        campaign.setStatus(EnumCampaignStatus.Draft);
        campaign = campaignRepository.saveAndFlush(campaign);

        assetService.getWithNew(campaign);








        return Pair.with(config,campaign);
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
        clainQuotaStatisticsResp.setTotalCount(campaign.getVoucher_count());
        clainQuotaStatisticsResp.setTotalAmount(campaign.getVoucher_count()*campaign.getAmount_off());
        clainQuotaStatisticsResp.setAllocatedQuotaCount(quotas.size());
        clainQuotaStatisticsResp.setAllocatedVoucherCount(quotas.stream().mapToLong(x->x.getQuota()).sum());
        clainQuotaStatisticsResp.setUnAllocatedVoucherCount(campaign.getVoucher_count() - quotas.stream().mapToLong(x->x.getQuota()).sum());


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

    public void update(Voucher voucher, EnumEvents events) {

        voucherRepository.save(voucher);
    }
    public void update(VoucherTicket voucher, EnumEvents events) {

        voucherTicketRepository.save(voucher);
    }

    public List<VoucherTicket> createVoucher(Reservation reservation, LineItem bookingProduct, User payer, int i) {



        List<VoucherTicket> vouchers = LongStream.range(0,bookingProduct.getQuantity()).boxed().map(x->{
            VoucherTicket voucher = new VoucherTicket();
           // String no = VoucherCodes.generate(config);
/*            System.out.println("Execute method asynchronously. "
                    + no);*/

            voucher.setBooking(reservation.getId());
            voucher.setUser(payer.getId());

            voucher.setCode(idGenService.ticketCode());
            voucher.setType(EnumVoucherType.TICKET);

            voucher.setStatus(EnumVoucherStatus.Created);
            voucher.setPublished(false);

            voucher.setActive(false);
            return voucher;
        }).collect(Collectors.toList());

        vouchers = voucherTicketRepository.saveAll(vouchers);
        return vouchers;
    }

    public Optional<RedemptionForCustomerVo> Cardholder(VoucherTicket voucher) {

        if(voucher.getRealnameHolder()== null){
            return Optional.empty();
        }
        Optional<Cardholder> cardholderOptional = cardholderRepository.findById(voucher.getRealnameHolder());

        if(cardholderOptional.isEmpty()) {

            return Optional.empty();
        }
            Cardholder traveler用户 = cardholderOptional.get();

            RedemptionForCustomerVo redemptionForCustomerVo = new RedemptionForCustomerVo();
            redemptionForCustomerVo.setId(traveler用户.getId());
            redemptionForCustomerVo.setRealName(traveler用户.getName());
            redemptionForCustomerVo.setCode(traveler用户.getCode());

            return Optional.of(redemptionForCustomerVo);


    }

    public Optional<RedemptionForCustomerVo> Cardholder(Long voucher) {


        if(voucher== null){
            return Optional.empty();
        }
        Optional<Cardholder> cardholderOptional = cardholderRepository.findById(voucher);

        if(cardholderOptional.isEmpty()) {

            return Optional.empty();
        }
        Cardholder traveler用户 = cardholderOptional.get();

        RedemptionForCustomerVo redemptionForCustomerVo = new RedemptionForCustomerVo();
        redemptionForCustomerVo.setId(traveler用户.getId());
        redemptionForCustomerVo.setRealName(traveler用户.getName());
        redemptionForCustomerVo.setCode(traveler用户.getCode());

        return Optional.of(redemptionForCustomerVo);


    }
}

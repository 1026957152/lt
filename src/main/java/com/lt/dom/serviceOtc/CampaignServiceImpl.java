package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.CampaignResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.notification.event.OnCampaignUpdatedEvent;
import com.lt.dom.notification.event.OnRegistrationCompleteEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.CompaignEditPojo;
import com.lt.dom.otcReq.CompaignPojo;
import com.lt.dom.otcReq.ScenarioReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignServiceImpl {

    @Autowired
    private ScenarioAssignmentRepository scenarioAssignmentRepository;

    @Autowired
    private ScenarioRepository scenarioRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherAsyncServiceImpl voucherAsyncService;


    @Autowired
    private VoucherServiceImpl voucherService;

    @Autowired
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public Scenario getScenario(Scenario scenario) {
        return scenario;
    }

    public Scenario createScenario(ScenarioReq scenarioReq) {
        Scenario scenario = new Scenario();
        scenario.setName(scenarioReq.getName());
        scenario.setCode(idGenService.scenarioNo());
        return scenarioRepository.save(scenario);
    }



    public ScenarioAssignment addScenarioAssignment(Scenario scenario, Supplier supplier, String note) {
        ScenarioAssignment scenarioAssignment = new ScenarioAssignment();

        scenarioAssignment.setScenario(scenario.getId());
        scenarioAssignment.setSupplier(supplier.getId());
        scenarioAssignment.setNote(note);
        scenarioAssignment = scenarioAssignmentRepository.save(scenarioAssignment);
        return scenarioAssignment;
    }

    public Campaign addScenarioCampaign(Scenario scenario, Campaign campaign) {
        campaign.setScenario(scenario.getId());
        campaign = campaignRepository.save(campaign);
        return campaign;
    }

    public List<Campaign> getQualification() {
        List<Campaign> campaigns = campaignRepository.findAll();

        return campaigns.stream().filter(x->x.getVouchers_generation_status().equals(EnumCampaignCreationStatus.DONE)).collect(Collectors.toList());
    }


    public Voucher retain(Campaign campaign) {

        Optional<Voucher> voucherOptional = voucherRepository.findFirstByCampaignAndActive(campaign.getId(),false);
        if(voucherOptional.isEmpty()){
           throw new BookNotFoundException(campaign.getId(),"找不到可发布的券");
        }
        return voucherOptional.get();
    }

    public List<Campaign> getQualification(Product product) {
        List<CampaignAssignToTourProduct> campaignAssignToTourBookingList = campaignAssignToTourProductRepository.findByProduct(product.getId());

        List<Campaign> campaigns = campaignRepository.findAllById(campaignAssignToTourBookingList.stream().map(x->x.getCampaign()).collect(Collectors.toList()));
        return campaigns.stream().filter(x->x.getVouchers_generation_status().equals(EnumCampaignCreationStatus.DONE))
                .filter(x->x.isActive()).collect(Collectors.toList());

    }

    public void updateSummary(Campaign campaign, EnumEvents voucher$published) {
        campaignRepository.save(campaign);
    }






    public CampaignResp.CampaignSummary getSummary(Campaign campaign) {

        CampaignResp.CampaignSummary campaignResp =  CampaignResp.CampaignSummary.from(campaign);





        return campaignResp;
    }

    public Campaign create(CompaignPojo compaignPojo, Scenario scenario) {
        Pair<CodeConfig,Campaign> campaign = voucherService.createLoyaltyCompaign(compaignPojo,scenario);
        voucherAsyncService.异步新建(campaign.getValue0(), campaign.getValue1());

        return campaign.getValue1();
    }

    public Campaign edit(Campaign campaign, CompaignEditPojo compaignPojo, UserVo userVo) {


        System.out.println(campaign.toString());
/*
        campaign.setClaim_note(compaignPojo.getClaim_note());
        campaign.setClaim_text(compaignPojo.getClaim_text());
        campaign.setClain_limit(compaignPojo.getClain_limit());
        campaign.setClaimable(compaignPojo.isClaimable());
        campaign.setExpiry_days(compaignPojo.getExpiry_days());
        campaign.setActive(compaignPojo.isActive());
        campaign.setPay(compaignPojo.isPay());

        campaign.setPayAmount(compaignPojo.getPay_amount());

        campaign.setName(compaignPojo.getName());
        campaign.setName_long(compaignPojo.getName_long());
        campaign.setDescription(compaignPojo.getName_long());


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


        campaign = campaignRepository.save(campaign);*/

        campaign = campaignRepository.save(campaign);
        eventPublisher.publishEvent(new OnCampaignUpdatedEvent(userVo,
                null, EnumEvents.campaign$updated));

        return campaign;
    }
}

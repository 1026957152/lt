package com.lt.dom.serviceOtc;


import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ScenarioReq;
import com.lt.dom.otcenum.EnumCampaignCreationStatus;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CampaignAssignToTourProductRepository campaignAssignToTourProductRepository;

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

    public void updateSummary(Campaign campaign) {
        campaignRepository.save(campaign);
    }
}

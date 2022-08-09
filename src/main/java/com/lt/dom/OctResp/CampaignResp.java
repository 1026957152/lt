package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.AssetRestController;
import com.lt.dom.controllerOct.CampaignRestController;
import com.lt.dom.controllerOct.PublicationRestController;
import com.lt.dom.oct.Campaign;
import com.lt.dom.oct.Document;
import com.lt.dom.oct.Quota;
import com.lt.dom.oct.Scenario;
import com.lt.dom.otcenum.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignResp extends RepresentationModel<CampaignResp> {



    private EnumCompaignType campaignType;

    private Boolean claimable;
    private String name;
    private String scenario;
    private String claim_text;
    private Integer claim_limit;
    private String claim_note;

    private String qr_url;
    private EntityModel<AssetResp> asset;

    public String getQr_url() {
        return qr_url;
    }

    public void setQr_url(String qr_url) {
        this.qr_url = qr_url;
    }

    public Boolean getClaimable() {
        return claimable;
    }

    public void setClaimable(Boolean claimable) {
        this.claimable = claimable;
    }

    private LocalDate start_date;
    private LocalDate expiration_date;

    private String category;
    private List<Quota> quotas;
    private String scenarioCode;
    private String code;
    private List<DocumentResp> documents;
    private String imageLogo;

    public static CampaignResp from(Quartet<Campaign, Optional<Scenario>, List<Quota>, List<Document>> with) {

        CampaignResp campaignResp = getCampaign(Triplet.with(with.getValue0(),with.getValue1(),with.getValue2()));

        Optional<Document> optionalDocument = with.getValue3().stream().filter(x->x.getType().equals(EnumDocumentType.campaign_logo)).findAny();

        if(optionalDocument.isPresent()){
            campaignResp.setImageLogo(FileStorageServiceImpl.url(optionalDocument.get()));
        }
        campaignResp.setDocuments(DocumentResp.Listfrom(with.getValue3()));
      //  with.getValue3().
        return campaignResp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private int vouchers_count;
    private EnumCampaignCreationStatus vouchers_generation_status; //IN_PROGRESS, DONE, ERROR
    private String description;
    private boolean active;




    public EnumCompaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(EnumCompaignType campaignType) {
        this.campaignType = campaignType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }



    public int getVouchers_count() {
        return vouchers_count;
    }

    public void setVouchers_count(int vouchers_count) {
        this.vouchers_count = vouchers_count;
    }


    public EnumCampaignCreationStatus getVouchers_generation_status() {
        return vouchers_generation_status;
    }

    public void setVouchers_generation_status(EnumCampaignCreationStatus vouchers_generation_status) {
        this.vouchers_generation_status = vouchers_generation_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



    private  Integer length ;
    private  String charset;
    private  String prefix;
    private  String postfix;
    private  String pattern;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }










    private EnumVoucherType vouchertype;
    private Integer amount_off;
    private Integer percent_off;
    private Integer unit_off;
    private EnumDiscountVoucherCategory discountCategory;

    public EnumVoucherType getVouchertype() {
        return vouchertype;
    }

    public void setVouchertype(EnumVoucherType vouchertype) {
        this.vouchertype = vouchertype;
    }

    public Integer getAmount_off() {
        return amount_off;
    }

    public void setAmount_off(Integer amount_off) {
        this.amount_off = amount_off;
    }

    public Integer getPercent_off() {
        return percent_off;
    }

    public void setPercent_off(Integer percent_off) {
        this.percent_off = percent_off;
    }

    public Integer getUnit_off() {
        return unit_off;
    }

    public void setUnit_off(Integer unit_off) {
        this.unit_off = unit_off;
    }

    public EnumDiscountVoucherCategory getDiscountCategory() {
        return discountCategory;
    }

    public void setDiscountCategory(EnumDiscountVoucherCategory discountCategory) {
        this.discountCategory = discountCategory;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getScenario() {
        return scenario;
    }

    public void setClainQuotas(List<Quota> quotas) {
        this.quotas = quotas;
    }

    public List<Quota> getClainQuotas() {
        return quotas;
    }

    public void setScenarioCode(String scenarioCode) {

        this.scenarioCode = scenarioCode;
    }

    public String getScenarioCode() {
        return scenarioCode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public static CampaignResp simple(Pair<Campaign,Optional<Scenario>> triplet) {

        Campaign campaign = triplet.getValue0();
        Optional<Scenario> scenario = triplet.getValue1();


        CampaignResp campaignResp = new CampaignResp();
        campaignResp.setCampaignType(campaign.getCampaignType());

        campaignResp.setActive(campaign.isActive());
        campaignResp.setStart_date(campaign.getStart_date());
        campaignResp.setExpiration_date(campaign.getExpiration_date());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setName(campaign.getName());
        campaignResp.setVouchertype(campaign.getVouchertype());
        campaignResp.setVouchers_count(campaign.getVouchers_count());
        campaignResp.setCode(campaign.getCode());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setClaimable(campaign.isActive());
        campaignResp.setClaim_text(campaign.getClaim_text());
        campaignResp.setClaim_limit(campaign.getClain_limit());
        campaignResp.setClaim_note(campaign.getClaim_note());
        campaignResp.setQr_url("未配置");
        campaignResp.setExpiry_days(campaign.getExpiry_days());


        if(campaign.getScenario() != 0){
            if(scenario.isPresent()){
                campaignResp.setScenario(scenario.get().getName());
                campaignResp.setScenarioCode(scenario.get().getCode());
            }
        }

       // campaignResp.setVouchers_generation_status(campaign.getVouchers_generation_status());
        if(campaign.getVouchertype().equals(EnumVoucherType.DISCOUNT_VOUCHER)){
            campaignResp.setDiscountCategory(campaign.getDiscountCategory());
            if(campaign.getDiscountCategory().equals(EnumDiscountVoucherCategory.AMOUNT)){
                campaignResp.setAmount_off(campaign.getAmount_off());
            }
            if(campaign.getDiscountCategory().equals(EnumDiscountVoucherCategory.PERCENT)){
                campaignResp.setPercent_off(campaign.getPercent_off());
            }
            if(campaign.getDiscountCategory().equals(EnumDiscountVoucherCategory.UNIT)){
                campaignResp.setUnit_off(campaign.getUnit_off());
            }
        }


        campaignResp.add(linkTo(methodOn(CampaignRestController.class).clain(campaign.getId(),null)).withRel("clainWithPay"));
        campaignResp.add(linkTo(methodOn(PublicationRestController.class).createPublication(campaign.getId(),null,null)).withRel("clain"));

        campaignResp.setCategory(campaign.getCategory());
        return campaignResp;
    }

    public static CampaignResp getCampaign(Triplet<Campaign,Optional<Scenario>,List<Quota>> triplet) {

        Campaign campaign = triplet.getValue0();
        Optional<Scenario> scenario = triplet.getValue1();

        List<Quota> quotas = triplet.getValue2();

        CampaignResp campaignResp = new CampaignResp();
        campaignResp.setCampaignType(campaign.getCampaignType());

        campaignResp.setActive(campaign.isActive());
        campaignResp.setStart_date(campaign.getStart_date());
        campaignResp.setExpiration_date(campaign.getExpiration_date());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setCharset(campaign.getCharset());
        campaignResp.setName(campaign.getName());
        campaignResp.setLength(campaign.getLength());
        campaignResp.setPostfix(campaign.getPostfix());
        campaignResp.setPrefix(campaign.getPrefix());
        campaignResp.setPattern(campaign.getPattern());
        campaignResp.setVouchertype(campaign.getVouchertype());
        campaignResp.setVouchers_count(campaign.getVouchers_count());
        campaignResp.setCode(campaign.getCode());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setExpiry_days(campaign.getExpiry_days());



        if(campaign.getScenario() != 0){
            if(scenario.isPresent()){
                campaignResp.setScenario(scenario.get().getName());
                campaignResp.setScenarioCode(scenario.get().getCode());
            }
        }

        campaignResp.setVouchers_generation_status(campaign.getVouchers_generation_status());
        if(campaign.getVouchertype().equals(EnumVoucherType.DISCOUNT_VOUCHER)){
            campaignResp.setDiscountCategory(campaign.getDiscountCategory());
            if(campaign.getDiscountCategory().equals(EnumDiscountVoucherCategory.AMOUNT)){
                campaignResp.setAmount_off(campaign.getAmount_off());
            }
            if(campaign.getDiscountCategory().equals(EnumDiscountVoucherCategory.PERCENT)){
                campaignResp.setPercent_off(campaign.getPercent_off());
            }
            if(campaign.getDiscountCategory().equals(EnumDiscountVoucherCategory.UNIT)){
                campaignResp.setUnit_off(campaign.getUnit_off());
            }
        }


        campaignResp.add(linkTo(methodOn(CampaignRestController.class).clain(campaign.getId(),null)).withRel("clainWithPay"));
        campaignResp.add(linkTo(methodOn(PublicationRestController.class).createPublication(campaign.getId(),null,null)).withRel("clain"));


        campaignResp.setClainQuotas(quotas);
        campaignResp.setCategory(campaign.getCategory());
        return campaignResp;
    }

    public static Page<CampaignResp> pageMap(Page<Campaign> campaignPageable) {
        return campaignPageable.map(x->getCampaign(Triplet.with(x,Optional.empty(), Arrays.asList())));
    }


    public static List<CampaignResp> pageMapToList(Page<Campaign> campaignPageable) {
        return  campaignPageable.map(x->getCampaign(Triplet.with(x,Optional.empty(), Arrays.asList()))).getContent();
    }
    public static List<CampaignResp> pageMapToListSimple(Page<Campaign> campaignPageable) {
        return  campaignPageable.map(x->simple(Pair.with(x,Optional.empty()))).getContent();
    }

    public void setDocuments(List<DocumentResp> documents) {
        this.documents = documents;
    }

    public List<DocumentResp> getDocuments() {
        return documents;
    }

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }

    public String getImageLogo() {
        return imageLogo;
    }

    public void setClaim_text(String claim_text) {
        this.claim_text = claim_text;
    }

    public String getClaim_text() {
        return claim_text;
    }

    public void setClaim_limit(Integer claim_limit) {
        this.claim_limit = claim_limit;
    }

    public Integer getClaim_limit() {
        return claim_limit;
    }

    public void setClaim_note(String claim_note) {
        this.claim_note = claim_note;
    }

    public String getClaim_note() {
        return claim_note;
    }

    public void setAsset(EntityModel<AssetResp> asset) {
        this.asset = asset;
    }

    public EntityModel<AssetResp> getAsset() {
        return asset;
    }

    private int expiry_days;

    public int getExpiry_days() {
        return expiry_days;
    }

    public void setExpiry_days(int expiry_days) {
        this.expiry_days = expiry_days;
    }
}

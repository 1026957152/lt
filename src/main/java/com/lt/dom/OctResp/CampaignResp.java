package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.controllerOct.BookingRestController;
import com.lt.dom.controllerOct.CampaignRestController;
import com.lt.dom.controllerOct.PublicationRestController;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.serviceOtc.FileStorageServiceImpl;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignResp  {



    private EnumCompaignType campaignType;

    private Boolean claimable;
    private String name;
    private String scenario;
    private String claim_text;
    private Integer claim_limit;
    private String claim_note;

    private String qr_url;
    private EntityModel<AssetResp> asset;
    private boolean pay;
    private int pay_amount;
    private String expiration_date_text;
    private String start_date_text;
    private String start_expiration_date_text;
    private String voucher_type_text;
    private String name_long;
    private String active_text;
    private String discount_category_text;
    private int totol_count;
    private long published_count;
    private String status_text;
    private long unpublished_count;


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
  //  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
   // @JsonSerialize(using = LocalDateTimeSerializer.class)
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")

   // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start_date;
    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
   // @JsonSerialize(using = LocalDateTimeSerializer.class)
   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")

 // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiration_date;



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

    private int voucher_count;
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

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDateTime expiration_date) {
        this.expiration_date = expiration_date;
    }



    public int getVoucher_count() {
        return voucher_count;
    }

    public void setVoucher_count(int vouchers_count) {
        this.voucher_count = vouchers_count;
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

    public void setVoucher_type(EnumVoucherType vouchertype) {
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

    public void setDiscount_category(EnumDiscountVoucherCategory discountCategory) {
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

    public static EnumLongIdResp enumSimple(Pair<Campaign,Optional<Scenario>> triplet) {

        Campaign campaign = triplet.getValue0();
        CampaignResp campaignResp = new CampaignResp();



        campaignResp.setCampaignType(campaign.getCampaignType());

        campaignResp.setActive(campaign.isActive());
        campaignResp.setStart_date(campaign.getStart_date());
        campaignResp.setExpiration_date(campaign.getExpiration_date());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setName(campaign.getName());
        campaignResp.setVoucher_type(campaign.getVouchertype());
        campaignResp.setVoucher_count(campaign.getVoucher_count());
        campaignResp.setCode(campaign.getCode());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setClaimable(campaign.isActive());
        campaignResp.setClaim_limit(campaign.getClain_limit());
        campaignResp.setClaim_note(campaign.getClaim_note());
        campaignResp.setExpiry_days(campaign.getExpiry_days());


        if(campaign.getVouchertype().equals(EnumVoucherType.DISCOUNT_VOUCHER)){
            campaignResp.setDiscount_category(campaign.getDiscountCategory());
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
        EnumLongIdResp enumLongIdResp = new EnumLongIdResp();
        enumLongIdResp.setId(campaign.getId());
        enumLongIdResp.setText(campaign.getName()+"_"+campaign.getCode());
        enumLongIdResp.setInfo(campaignResp);
        return enumLongIdResp;
    }
    public static CampaignResp simple(Pair<Campaign,Optional<Scenario>> triplet) {

        Campaign campaign = triplet.getValue0();
        Optional<Scenario> scenario = triplet.getValue1();


        CampaignResp campaignResp = new CampaignResp();
        campaignResp.setCampaignType(campaign.getCampaignType());

        campaignResp.setActive(campaign.isActive());
        campaignResp.setStart_date(campaign.getStart_date());
        campaignResp.setExpiration_date(campaign.getExpiration_date());
        ;
        String start = campaign.getStart_date().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH"));
        String end = campaign.getExpiration_date().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH"));

        campaignResp.setStart_date_text(start);
        campaignResp.setExpiration_date_text(end);

        campaignResp.setStart_expiration_date_text(start+"时 ~"+end+"时");


        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setName(campaign.getName());
        campaignResp.setVoucher_type(campaign.getVouchertype());
        campaignResp.setVoucher_type_text(campaign.getVouchertype().toString());
        campaignResp.setVoucher_count(campaign.getVoucher_count());
        campaignResp.setCode(campaign.getCode());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setClaimable(campaign.isActive());
        campaignResp.setClaim_text(campaign.getClaim_text());
        campaignResp.setClaim_limit(campaign.getClain_limit());
        campaignResp.setClaim_note(campaign.getClaim_note());
        campaignResp.setQr_url("未配置");
        campaignResp.setExpiry_days(campaign.getExpiry_days());
        campaignResp.setPay(campaign.getPay());
        campaignResp.setPay_amount(campaign.getPayAmount());
        campaignResp.setTotol_count(campaign.getVoucher_count());
        campaignResp.setPublished_count(campaign.getTotal_published());
        campaignResp.setUnpublished_count(campaign.getVoucher_count()-campaign.getTotal_published());
        campaignResp.setStatus_text(campaign.getStatus().toString());

        if(campaign.getScenario() != 0){
            if(scenario.isPresent()){
                campaignResp.setScenario(scenario.get().getName());
                campaignResp.setScenarioCode(scenario.get().getCode());
            }
        }

       // campaignResp.setVouchers_generation_status(campaign.getVouchers_generation_status());
        if(campaign.getVouchertype().equals(EnumVoucherType.DISCOUNT_VOUCHER)){
            campaignResp.setDiscount_category(campaign.getDiscountCategory());
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
        campaignResp.setActive_text(campaign.isActive()?"活跃":"不活跃");
        campaignResp.setStart_date(campaign.getStart_date());
        campaignResp.setExpiration_date(campaign.getExpiration_date());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setCharset(campaign.getCharset());
        campaignResp.setName(campaign.getName());
        campaignResp.setName_long(campaign.getName_long());
        campaignResp.setLength(campaign.getLength());
        campaignResp.setPostfix(campaign.getPostfix());
        campaignResp.setPrefix(campaign.getPrefix());
        campaignResp.setPattern(campaign.getPattern());
        campaignResp.setVoucher_type(campaign.getVouchertype());
        campaignResp.setVoucher_type_text(campaign.getVouchertype().toString());
        campaignResp.setVoucher_count(campaign.getVoucher_count());
        campaignResp.setCode(campaign.getCode());
        campaignResp.setDescription(campaign.getDescription());
        campaignResp.setExpiry_days(campaign.getExpiry_days());
        campaignResp.setPay(campaign.getPay());
        campaignResp.setPay_amount(campaign.getPayAmount());

        if(EnumDiscountVoucherCategory.AMOUNT.equals(campaign.getDiscountCategory())){
            campaignResp.setVoucher_info(campaign.getDiscountCategory().toString() +"" +campaign.getAmount_off());
            campaignResp.setDiscount_category_text(campaign.getDiscountCategory().toString());
            campaignResp.setDiscount_category(campaign.getDiscountCategory());

        }else{
            campaignResp.setVoucher_info("????");

        }

        if(campaign.getScenario() != 0){
            if(scenario.isPresent()){
                campaignResp.setScenario(scenario.get().getName());
                campaignResp.setScenarioCode(scenario.get().getCode());
            }
        }

        campaignResp.setVouchers_generation_status(campaign.getVouchers_generation_status());
        if(campaign.getVouchertype().equals(EnumVoucherType.DISCOUNT_VOUCHER)){
            campaignResp.setDiscount_category(campaign.getDiscountCategory());
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




        campaignResp.setClainQuotas(quotas);
        campaignResp.setCategory(campaign.getCategory());
        return campaignResp;
    }




    public static Page<CampaignResp> pageMapToList(Page<Campaign> campaignPageable) {
        return  campaignPageable.map(x->getCampaign(Triplet.with(x,Optional.empty(), Arrays.asList())));
    }

    public static Page<CampaignResp> searchMapToList(Page<Campaign> campaignPageable) {
        return  campaignPageable.map(x->getCampaign(Triplet.with(x,Optional.empty(), Arrays.asList())));
    }
    public static Page<CampaignResp> pageMapToListSimple(Page<Campaign> campaignPageable) {
        return  campaignPageable.map(x->simple(Pair.with(x,Optional.empty())));
    }

    public static Page<EnumLongIdResp> pageMapToListEnumSimple(Page<Campaign> campaignPageable) {
        return  campaignPageable.map(x->enumSimple(Pair.with(x,Optional.empty())));
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


    private String voucher_info = "测试展示券的 详细信息，券金额和券类别";

    public String getVoucher_info() {
        return voucher_info;
    }

    public void setVoucher_info(String voucher_info) {
        this.voucher_info = voucher_info;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public boolean getPay() {
        return pay;
    }

    public void setPay_amount(int pay_amount) {
        this.pay_amount = pay_amount;
    }

    public int getPay_amount() {
        return pay_amount;
    }

    public void setExpiration_date_text(String expiration_date_text) {
        this.expiration_date_text = expiration_date_text;
    }

    public String getExpiration_date_text() {
        return expiration_date_text;
    }

    public void setStart_date_text(String start_date_text) {
        this.start_date_text = start_date_text;
    }

    public String getStart_date_text() {
        return start_date_text;
    }

    public void setStart_expiration_date_text(String start_expiration_date_text) {
        this.start_expiration_date_text = start_expiration_date_text;
    }

    public String getStart_expiration_date_text() {
        return start_expiration_date_text;
    }

    public void setVoucher_type_text(String voucher_type_text) {
        this.voucher_type_text = voucher_type_text;
    }

    public String getVoucher_type_text() {
        return voucher_type_text;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getName_long() {
        return name_long;
    }

    public void setActive_text(String active_text) {
        this.active_text = active_text;
    }

    public String getActive_text() {
        return active_text;
    }

    public void setDiscount_category_text(String discount_category_text) {
        this.discount_category_text = discount_category_text;
    }

    public String getDiscount_category_text() {
        return discount_category_text;
    }

    public void setTotol_count(int totol_count) {
        this.totol_count = totol_count;
    }

    public int getTotol_count() {
        return totol_count;
    }

    public void setPublished_count(long published_count) {
        this.published_count = published_count;
    }

    public long getPublished_count() {
        return published_count;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setUnpublished_count(long unpublished_count) {
        this.unpublished_count = unpublished_count;
    }

    public long getUnpublished_count() {
        return unpublished_count;
    }
}

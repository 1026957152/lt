package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Pass;
import com.lt.dom.otcenum.*;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassResp extends BaseResp {



    private String name_on_card ;//digital tickets or PDF tickets


    private String note ;//digital tickets or PDF tickets




    private EnumCardType type ;//digital tickets or PDF tickets

    private String code ;//digital tickets or PDF tickets
    private Long productId;
    private EnumCardFullfullmentStatus fulfillment_status;
    private String fulfillment_status_text;
    private String type_text;
    private Long bulkIssuanceId;
    private LocalDateTime expiringDate;
    private EnumCardStatus status;
    private String status_text;
    private String title;
    private List componentVounch;
    private PhotoResp thumb;
    private EntityModel<AssetResp> asset;
    private String number;
    private String CVC;
    private CardholderResp cardholder;
    private LocalDateTime maxActivationDate;
    private PhotoResp cover;
    private String label;
    private Map additionalInfo;
    private String code_base64_src;
    private long id;

    public String getCode() {
        return code;
    }
    private Long user ;//digital tickets or PDF tickets

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public void setCode(String code) {
        this.code = code;
    }


    List<Long> rights;

    public EnumCardType getType() {
        return type;
    }

    public void setType(EnumCardType type) {
        this.type = type;
    }

    public List<Long> getRights() {
        return rights;
    }

    public void setRights(List<Long> rights) {
        this.rights = rights;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public static PassResp mefrom(Pass e) {

        PassResp passResp = new PassResp();
        passResp.setCode(e.getCode());
        passResp.setTitle("城市主人尊享卡");

        return passResp;
    }

    public static PassResp SimplefromWithId(Pass e) {

        PassResp passResp = new PassResp();
        passResp.setCode(e.getCode());
        passResp.setId(e.getId());
        passResp.setTitle(e.getLabel());
        passResp.setFulfillment_status_text(e.getFulfillment_status().toString());
        passResp.setExpiringDate(e.getExpiringDate());


        passResp.setNumber(e.getNumber());

        passResp.setStatus_text(e.getStatus().toString());
        return passResp;
    }
    public static PassResp Simplfrom(Pass e) {

        PassResp passResp = new PassResp();
        passResp.setCode(e.getCode());
        passResp.setTitle(e.getLabel());
        passResp.setFulfillment_status_text(e.getFulfillment_status().toString());
        passResp.setExpiringDate(e.getExpiringDate());


        passResp.setNumber(e.getNumber());

        passResp.setStatus_text(e.getStatus().toString());
        return passResp;
    }
    public static PassResp from(Pass e) {

        PassResp passResp = new PassResp();

        passResp.setTitle(e.getLabel());
        passResp.setType(e.getType());
        passResp.setType_text(e.getType().toString());
        passResp.setCode(e.getCode());
        passResp.setFulfillment_status(e.getFulfillment_status());

        passResp.setFulfillment_status_text(e.getFulfillment_status().toString());
        passResp.setBulkIssuanceId(e.getBulkIssuanceId());
        passResp.setExpiringDate(e.getExpiringDate());
        passResp.setStatus(e.getStatus());

        passResp.setMaxActivationDate(e.getMaxActivationDate());

        passResp.setStatus_text(e.getStatus().toString());
        passResp.setBulkIssuanceId(e.getBulkIssuanceId());
        passResp.setCreatedDate(e.getCreatedDate());
        passResp.setModifiedDate(e.getModifiedDate());
        passResp.setNumber(e.getNumber());
        passResp.setCreatedDate(e.getCreatedDate());
        passResp.setModifiedDate(e.getModifiedDate());
       // passResp.setExpiringDate(e.get());
        return passResp;
    }

    public static PassResp fromInactive(Pass e) {

        PassResp passResp = new PassResp();

        passResp.setLabel(e.getLabel());
        passResp.setStatus_text(e.getStatus().toString());
        passResp.setStatus(e.getStatus());

        passResp.setNumber(e.getNumber());
        passResp.setType_text(e.getType().toString());
        passResp.setType(e.getType());
        passResp.setType_text(e.getType().toString());
        passResp.setMaxActivationDate(e.getMaxActivationDate()==null?LocalDateTime.now():e.getMaxActivationDate());
        passResp.setCVC(e.getPin());
        passResp.setCreatedDate(e.getCreatedDate());




        passResp.setCode(e.getCode());




        return passResp;
    }


    public static PassResp fromActive(Pass e) {

        PassResp passResp = new PassResp();

        passResp.setLabel(e.getLabel());
        passResp.setType(e.getType());
        passResp.setType_text(e.getType().toString());
        passResp.setCode(e.getCode());
        passResp.setExpiringDate(e.getExpiringDate());
        passResp.setStatus(e.getStatus());

        passResp.setStatus_text(e.getStatus().toString());
        passResp.setBulkIssuanceId(e.getBulkIssuanceId());
        passResp.setCreatedDate(e.getCreatedDate());
        passResp.setModifiedDate(e.getModifiedDate());
        passResp.setNumber(e.getNumber());

        passResp.setModifiedDate(e.getModifiedDate());
        return passResp;
    }
    public void setFulfillment_status(EnumCardFullfullmentStatus fulfillment_status) {
        this.fulfillment_status = fulfillment_status;
    }

    public EnumCardFullfullmentStatus getFulfillment_status() {
        return fulfillment_status;
    }

    public void setFulfillment_status_text(String fulfillment_status_text) {
        this.fulfillment_status_text = fulfillment_status_text;
    }

    public String getFulfillment_status_text() {
        return fulfillment_status_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setBulkIssuanceId(Long bulkIssuanceId) {

        this.bulkIssuanceId = bulkIssuanceId;
    }

    public Long getBulkIssuanceId() {
        return bulkIssuanceId;
    }

    public void setExpiringDate(LocalDateTime expiringDate) {

        this.expiringDate = expiringDate;
    }

    public LocalDateTime getExpiringDate() {
        return expiringDate;
    }

    public void setStatus(EnumCardStatus status) {
        this.status = status;
    }

    public EnumCardStatus getStatus() {
        return status;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public <R> void setComponentVounch(List componentVounch) {
        this.componentVounch = componentVounch;
    }

    public List getComponentVounch() {
        return componentVounch;
    }

    public void setThumb(PhotoResp thumb) {
        this.thumb = thumb;
    }

    public PhotoResp getThumb() {
        return thumb;
    }

    public void setAsset(EntityModel<AssetResp> asset) {
        this.asset = asset;
    }

    public EntityModel<AssetResp> getAsset() {
        return asset;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setCVC(String cvc) {
        this.CVC = cvc;
    }

    public String getCVC() {
        return CVC;
    }

    public void setCardholder(CardholderResp cardholder) {
        this.cardholder = cardholder;
    }

    public CardholderResp getCardholder() {
        return cardholder;
    }

    public void setMaxActivationDate(LocalDateTime maxActivationDate) {
        this.maxActivationDate = maxActivationDate;
    }

    public LocalDateTime getMaxActivationDate() {
        return maxActivationDate;
    }

    public void setCover(PhotoResp cover) {
        this.cover = cover;
    }

    public PhotoResp getCover() {
        return cover;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public <K, V> void setAdditionalInfo(Map additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Map getAdditionalInfo() {
        return additionalInfo;
    }

    public void setCode_base64_src(String code_base64_src) {

        this.code_base64_src = code_base64_src;
    }

    public String getCode_base64_src() {
        return code_base64_src;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

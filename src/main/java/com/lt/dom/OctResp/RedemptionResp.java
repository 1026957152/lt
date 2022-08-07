package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.controllerOct.FileUploadController;
import com.lt.dom.controllerOct.RedemptionRestController;
import com.lt.dom.oct.Redemption;
import com.lt.dom.oct.RedemptionEntry;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.requestvo.PublishTowhoVo;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class RedemptionResp {


    private int quantity;
    private int redeemed_quantity;

    private List<RedemptionEntryResp> entries;
    private LocalDateTime redeem_at;
    private String reHolder;



    public static EntityModel<RedemptionResp> from(Triplet<RedemptionEntry,Redemption, PublishTowhoVo> pair) {

        PublishTowhoVo publishTowhoVo = pair.getValue2();
        RedemptionEntry redemptionEntry = pair.getValue0();
        RedemptionResp redemptionResp = new RedemptionResp();
        redemptionResp.setRedeemed_quantity(pair.getValue1().getRedeemed_quantity());
        redemptionResp.setRelatedObjectId(pair.getValue1().getRelatedObjectId());
        redemptionResp.setRelatedObjectType(pair.getValue1().getRelatedObjectType());

        RedemptionEntryResp redemptionEntryResp = new RedemptionEntryResp();

       // redemptionEntryResp.setResult(pair.getValue0().getResult());


        redemptionEntryResp.setRelatedObjectType(pair.getValue0().getRelatedObjectType());

        redemptionResp.setEntries(Arrays.asList(redemptionEntryResp));
        redemptionResp.setReHolder(redemptionEntry.getHolder());
        redemptionResp.setRedeem_at(redemptionEntry.getRedeem_at());
        redemptionResp.setEntries(Arrays.asList(redemptionEntryResp));
        redemptionResp.setTowho(Towho.from(publishTowhoVo));
        EntityModel<RedemptionResp> redemptionEntryEntityModel =  EntityModel.of(redemptionResp);
        return redemptionEntryEntityModel;
    }

    public static Page<EntityModel<RedemptionEntryResp>> pageFrom(Page<RedemptionEntry> clainQuotas) {
        return clainQuotas.map(x->{
            RedemptionEntryResp entry = new RedemptionEntryResp();
           // entry.setResult(x.getResult());
           // entry.setCustomer_id(x.getCustomer_id());
            entry.setRelatedObjectType(x.getRelatedObjectType());
            entry.setHolder(x.getHolder());
            entry.setRedeem_at(x.getRedeem_at());
            entry.setRedeemed_amount(x.getRedeemed_amount());
            entry.setRedeemed_quantity(x.getRedeemed_quantity());
            entry.setCampaign_name(x.getCampaign_name());
            entry.setVoucher_code(x.getVoucherCode());
            EntityModel<RedemptionEntryResp> redemptionEntryEntityModel =  EntityModel.of(entry);

            redemptionEntryEntityModel.add(linkTo(methodOn(RedemptionRestController.class).getRedemption(x.getId())).withRel("upload_file_url"));

            return redemptionEntryEntityModel;
        });

    }

    public List<RedemptionEntryResp> getEntries() {
        return entries;
    }

    public void setEntries(List<RedemptionEntryResp> entries) {
        this.entries = entries;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setRedeemed_quantity(int redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }


    private String relatedObjectType;
    private long relatedObjectId;

    public String getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(String related_object_type) {
        this.relatedObjectType = related_object_type;
    }

    public long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(long related_object_id) {
        this.relatedObjectId = related_object_id;
    }

    private Towho towho;

    public Towho getTowho() {
        return towho;
    }

    public void setTowho(Towho towho) {
        this.towho = towho;
    }

    public void setRedeem_at(LocalDateTime redeem_at) {
        this.redeem_at = redeem_at;
    }

    public LocalDateTime getRedeem_at() {
        return redeem_at;
    }

    public void setReHolder(String reHolder) {
        this.reHolder = reHolder;
    }

    public String getReHolder() {
        return reHolder;
    }

    public static class Towho {

        private EnumPublicationObjectType type;
        @JsonProperty("email")
        private String email;
        @JsonProperty("telephone")
        private String telephone;
        @JsonProperty("address")
        private String address;
        private String reamName;
        private String id;
        private String phone;

        public EnumPublicationObjectType getType() {
            return type;
        }

        public void setType(EnumPublicationObjectType type) {
            this.type = type;
        }

        public static Towho from(PublishTowhoVo publishTowhoVo) {
            Towho towho = new Towho();
            towho.setType(publishTowhoVo.getToWhoTyp());
            if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.traveler)){
                towho.setReamName(publishTowhoVo.getTraveler().getName());
                towho.setId(publishTowhoVo.getTraveler().getIdNo());
                towho.setPhone(publishTowhoVo.getTraveler().getTel_home());

            }
            if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.customer)){
                towho.setReamName(publishTowhoVo.getUser().getRealName());
                towho.setId(publishTowhoVo.getUser().getIdCard());
                towho.setPhone(publishTowhoVo.getUser().getPhone());

            }
            if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.business)){
                towho.setReamName(publishTowhoVo.getSupplier().getName());
                towho.setId(publishTowhoVo.getSupplier().getName());
                towho.setPhone(publishTowhoVo.getSupplier().getDesc());

            }
            return towho;
        }

        public void setReamName(String reamName) {
            this.reamName = reamName;
        }

        public String getReamName() {
            return reamName;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return phone;
        }
    }

}

package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class RedemptionTryResp {

    private String type_text;

    private Boolean redeemAllowed;

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }


    public void setRedeemAllowed(Boolean redeemAllowed) {
        this.redeemAllowed = redeemAllowed;
    }

    public Boolean isRedeemAllowed() {
        return redeemAllowed;
    }

    public static class PhotoId {

        private PhotoResp faceImage;
        private boolean realname;
        private String dateOfbirth;
        private PhotoResp selfie;
        private PhotoResp document_front;
        private String name;

        public void setFaceImage(PhotoResp faceImage) {
            this.faceImage = faceImage;
        }

        public PhotoResp getFaceImage() {
            return faceImage;
        }

        public void setRealname(boolean realname) {
            this.realname = realname;
        }

        public boolean isRealname() {
            return realname;
        }

        public void setDateOfbirth(String dateOfbirth) {
            this.dateOfbirth = dateOfbirth;
        }

        public String getDateOfbirth() {
            return dateOfbirth;
        }

        public void setSelfie(PhotoResp selfie) {
            this.selfie = selfie;
        }

        public PhotoResp getSelfie() {
            return selfie;
        }

        public void setDocument_front(PhotoResp document_front) {
            this.document_front = document_front;
        }

        public PhotoResp getDocument_front() {
            return document_front;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
    private PhotoId photoId;

    public PhotoId getPhotoId() {
        return photoId;
    }

    public void setPhotoId(PhotoId photoId) {
        this.photoId = photoId;
    }

    public static class RedemptionEntryResp {


        private String lable;
        private Integer limit;
        private Integer remaining;
        private Boolean check_in;
        private String redeem_voucher_key;
        private LocalDate start_date;
        private LocalDate end_date;
        private String redeem_voucher_key_crypt;
        private String redeem_voucher_key_crypt_encode;
        private String redeem_voucher_key_crypt_encode_withoutPadding;
        private Long tryRedeem;

        public void setLable(String lable) {
            this.lable = lable;
        }

        public String getLable() {
            return lable;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setRemaining(Integer remaining) {
            this.remaining = remaining;
        }

        public Integer getRemaining() {
            return remaining;
        }

        public void setCheck_in(Boolean check_in) {
            this.check_in = check_in;
        }

        public Boolean isCheck_in() {
            return check_in;
        }

        public void setRedeem_voucher_key(String redeem_voucher_key) {

            this.redeem_voucher_key = redeem_voucher_key;
        }

        public String getRedeem_voucher_key() {
            return redeem_voucher_key;
        }

        public void setStart_date(LocalDate start_date) {
            this.start_date = start_date;
        }

        public LocalDate getStart_date() {
            return start_date;
        }

        public void setEnd_date(LocalDate end_date) {
            this.end_date = end_date;
        }

        public LocalDate getEnd_date() {
            return end_date;
        }

        public void setRedeem_voucher_key_crypt(String redeem_voucher_key_crypt) {
            this.redeem_voucher_key_crypt = redeem_voucher_key_crypt;
        }

        public String getRedeem_voucher_key_crypt() {
            return redeem_voucher_key_crypt;
        }

        public void setRedeem_voucher_key_crypt_encode(String redeem_voucher_key_crypt_encode) {
            this.redeem_voucher_key_crypt_encode = redeem_voucher_key_crypt_encode;
        }

        public String getRedeem_voucher_key_crypt_encode() {
            return redeem_voucher_key_crypt_encode;
        }

        public void setRedeem_voucher_key_crypt_encode_withoutPadding(String redeem_voucher_key_crypt_encode_withoutPadding) {
            this.redeem_voucher_key_crypt_encode_withoutPadding = redeem_voucher_key_crypt_encode_withoutPadding;
        }

        public String getRedeem_voucher_key_crypt_encode_withoutPadding() {
            return redeem_voucher_key_crypt_encode_withoutPadding;
        }

        public void setTryRedeem(Long tryRedeem) {
            this.tryRedeem = tryRedeem;
        }

        public Long getTryRedeem() {
            return tryRedeem;
        }
    }
        private EnumRedeamptionType type;

    public EnumRedeamptionType getType() {
        return type;
    }

    public void setType(EnumRedeamptionType type) {
        this.type = type;
    }

    private Long quantity;
    private Long redeemed_quantity;

    private List<RedemptionEntryResp> entries;
    private LocalDateTime redeem_at;

    private String crypto_code;
    private String voucher_code;


    public static SingleTowho sigleEntryfrom(Quintet<PublicationEntry, Voucher, PublishTowhoVo, Campaign, Supplier> pair) {

        PublishTowhoVo publishTowhoVo = pair.getValue2();
        PublicationEntry publicationEntry = pair.getValue0();
        Voucher voucher = pair.getValue1();
        Campaign campaign = pair.getValue3();
        Supplier supplier = pair.getValue4();

        SingleTowho redemptionResp = new SingleTowho();

        Towho towho = Towho.from(publishTowhoVo);
        redemptionResp.setType(EnumRedemptionType.single);

        redemptionResp.setHolder_name(towho.getReamName());
        redemptionResp.setHolder_id(towho.getId());
        redemptionResp.setHolder_phone(towho.getPhone());
        redemptionResp.setVoucher_code(voucher.getCode());
        redemptionResp.setCampaign_title(campaign.getName());
        redemptionResp.setVoucher_type(voucher.getType().toString());

        redemptionResp.setVoucher_amount(voucher.getRedeemed_amount());
        redemptionResp.setVoucher_quantity(voucher.getRedeemed_quantity());

        return redemptionResp;
    }



    public static SingleTowho sigleEntryfrom(TourBooking tourBooking, Optional<Campaign> campaigns, List<Traveler> travelerList) {



        Campaign campaign = campaigns.get();


        SingleTowho redemptionResp = new SingleTowho();

        redemptionResp.setType(EnumRedemptionType.multiple);


        redemptionResp.setTour_name(tourBooking.getAdditional_info_tour_title());
        redemptionResp.setTour_code(tourBooking.getAdditional_info_tour_code());
        redemptionResp.setGuide_name(tourBooking.getAdditional_info_guide_name());
        redemptionResp.setGuide_phone(tourBooking.getAdditional_info_guide_phone());
        redemptionResp.setTour_agent(tourBooking.getOwner()+" 旅行社");
        redemptionResp.setTraveler_count(travelerList.size());
        redemptionResp.setCampaign_title(campaign.getName());
        redemptionResp.setVoucher_type(campaign.getVouchertype().toString());

        int num = campaign.getAmount_off()*travelerList.size();

        redemptionResp.setVoucher_amount(num);
        redemptionResp.setVoucher_quantity(Integer.valueOf(travelerList.size()).longValue());

        return redemptionResp;

    }

    public List<RedemptionEntryResp> getEntries() {
        return entries;
    }

    public void setEntries(List<RedemptionEntryResp> entries) {
        this.entries = entries;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getRedeemed_quantity() {
        return redeemed_quantity;
    }

    public void setRedeemed_quantity(Long redeemed_quantity) {
        this.redeemed_quantity = redeemed_quantity;
    }


    private EnumAssociatedType relatedObjectType;
    private Long relatedObjectId;

    public EnumAssociatedType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(EnumAssociatedType related_object_type) {
        this.relatedObjectType = related_object_type;
    }

    public Long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(Long related_object_id) {
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



    public void setCrypto_code(String crypto_code) {
        this.crypto_code = crypto_code;
    }

    public String getCrypto_code() {
        return crypto_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public String getVoucher_code() {
        return voucher_code;
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
            if (publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.traveler)) {
                towho.setReamName(publishTowhoVo.getTraveler().getName());
                towho.setId(publishTowhoVo.getTraveler().getIdNo());
                towho.setPhone(publishTowhoVo.getTraveler().getTel_home());

            }
            if (publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.customer)) {
                towho.setReamName(publishTowhoVo.getUser().getRealName());
                towho.setId(publishTowhoVo.getUser().getIdCard());
                towho.setPhone(publishTowhoVo.getUser().getPhone());

            }
            if (publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.business)) {
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


    public static class SingleTowho {

        private EnumRedemptionType type;
        private String tour_name;
        private String tour_code;
        private String guide_phone;
        private String guide_name;
        private String tour_agent;
        private int traveler_count;

        public EnumRedemptionType getType() {
            return type;
        }

        public void setType(EnumRedemptionType type) {
            this.type = type;
        }

        private String holder_name;
        private String holder_id;
        private String voucher_code;
        private String campaign_title;
        private Long voucher_quantity;
        private int voucher_amount;
        private String voucher_type;
        private String holder_phone;
        private String crypto_code;

        public void setHolder_name(String holder_name) {
            this.holder_name = holder_name;
        }

        public String getHolder_name() {
            return holder_name;
        }

        public void setHolder_id(String holder_id) {
            this.holder_id = holder_id;
        }

        public String getHolder_id() {
            return holder_id;
        }

        public void setVoucher_code(String voucher_code) {
            this.voucher_code = voucher_code;
        }

        public String getVoucher_code() {
            return voucher_code;
        }

        public void setCampaign_title(String campaign_title) {
            this.campaign_title = campaign_title;
        }

        public String getCampaign_title() {
            return campaign_title;
        }

        public void setVoucher_quantity(Long voucher_quantity) {
            this.voucher_quantity = voucher_quantity;
        }

        public Long getVoucher_quantity() {
            return voucher_quantity;
        }

        public void setVoucher_amount(int voucher_amount) {
            this.voucher_amount = voucher_amount;
        }

        public int getVoucher_amount() {
            return voucher_amount;
        }

        public void setVoucher_type(String voucher_type) {
            this.voucher_type = voucher_type;
        }

        public String getVoucher_type() {
            return voucher_type;
        }

        public void setHolder_phone(String holder_phone) {
            this.holder_phone = holder_phone;
        }

        public String getHolder_phone() {
            return holder_phone;
        }

        public void setCrypto_code(String crypto_code) {
            this.crypto_code = crypto_code;
        }

        public String getCrypto_code() {
            return crypto_code;
        }

        public void setTour_name(String tour_name) {
            this.tour_name = tour_name;
        }

        public String getTour_name() {
            return tour_name;
        }

        public void setTour_code(String tour_code) {
            this.tour_code = tour_code;
        }

        public String getTour_code() {
            return tour_code;
        }

        public void setGuide_phone(String guide_phone) {
            this.guide_phone = guide_phone;
        }

        public String getGuide_phone() {
            return guide_phone;
        }

        public void setGuide_name(String guide_name) {
            this.guide_name = guide_name;
        }

        public String getGuide_name() {
            return guide_name;
        }

        public void setTour_agent(String tour_agent) {
            this.tour_agent = tour_agent;
        }

        public String getTour_agent() {
            return tour_agent;
        }

        public void setTraveler_count(int traveler_count) {
            this.traveler_count = traveler_count;
        }

        public int getTraveler_count() {
            return traveler_count;
        }
    }


}
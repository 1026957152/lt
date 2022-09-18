package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssociatedType;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.otcenum.EnumRedemptionType;
import com.lt.dom.requestvo.PublishTowhoVo;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class RedemptionResp {


    private Long quantity;
    private Long redeemed_quantity;

    private List<RedemptionEntryResp> entries;
    private LocalDateTime redeem_at;

    private String crypto_code;
    private String voucher_code;

    public static List<EntityModel<RedemptionResp>> from(List<Triplet<RedemptionEntry, Redemption, PublishTowhoVo>> list) {


        return list.stream().map(pair -> {
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

            redemptionResp.setRedeem_at(redemptionEntry.getRedeemed_at());
            redemptionResp.setEntries(Arrays.asList(redemptionEntryResp));
            redemptionResp.setTowho(Towho.from(publishTowhoVo));
            EntityModel<RedemptionResp> redemptionEntryEntityModel = EntityModel.of(redemptionResp);
            return redemptionEntryEntityModel;
        }).collect(Collectors.toList());


    }

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

    public static RedemptionResp from(Triplet<RedemptionEntry, Redemption, PublishTowhoVo> pair) {

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

        redemptionResp.setRedeem_at(redemptionEntry.getRedeemed_at());
        redemptionResp.setEntries(Arrays.asList(redemptionEntryResp));
        redemptionResp.setTowho(Towho.from(publishTowhoVo));
        return redemptionResp;
    }

    public static Page<RedemptionEntryResp> pageFromWithout(Page<RedemptionEntry> clainQuotas) {
        return clainQuotas.map(x -> {
            RedemptionEntryResp entry = new RedemptionEntryResp();
            // entry.setResult(x.getResult());
            // entry.setCustomer_id(x.getCustomer_id());
            entry.setRelatedObjectType(x.getRelatedObjectType());

            entry.setRedeem_at(x.getRedeemed_at());
            entry.setRedeemed_amount(x.getRedeemed_amount());
            entry.setRedeemed_quantity(x.getRedeemed_quantity());
            entry.setCampaign_name(x.getCampaign_name());
            entry.setVoucher_code(x.getVoucher_code());


            return entry;
        });

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
    private long relatedObjectId;

    public EnumAssociatedType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(EnumAssociatedType related_object_type) {
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
package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.OctResp.CardholderResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.OctResp.PublicationEntryResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.util.AESUtil;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.Column;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherTicketResp {

    private EntityModel performance;
    private List redemptions;
    private Redemption redemption;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;
    private CardholderResp holder;
    private String name;

    public void setPerformance(EntityModel performance) {
        this.performance = performance;
    }

    public EntityModel getPerformance() {
        return performance;
    }

    public <R> void setRedemptions(List redemptions) {
        this.redemptions = redemptions;
    }

    public List getRedemptions() {
        return redemptions;
    }

    public void setRedemption(Redemption redemption) {
        this.redemption = redemption;
    }

    public Redemption getRedemption() {
        return redemption;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setHolder(Optional<Cardholder> holder) {

        if(holder!= null){

            CardholderResp cardholderResp = CardholderResp.Desensitizedfrom(holder.get());
            this.holder = cardholderResp;

        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public static class Redemption {
        private Integer quantity;
        private Integer redeemed_quantity;

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getRedeemed_quantity() {
            return redeemed_quantity;
        }

        public void setRedeemed_quantity(Integer redeemed_quantity) {
            this.redeemed_quantity = redeemed_quantity;
        }

        private List redemption_entries;

        public List getRedemption_entries() {
            return redemption_entries;
        }

        public void setRedemption_entries(List redemption_entries) {
            this.redemption_entries = redemption_entries;
        }

    }
    public static class Booking {
        private String code;
        private LocalDateTime created_at;
        private LocalDateTime paied_at;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public LocalDateTime getCreated_at() {
            return created_at;
        }

        public void setCreated_at(LocalDateTime created_at) {
            this.created_at = created_at;
        }

        public LocalDateTime getPaied_at() {
            return paied_at;
        }

        public void setPaied_at(LocalDateTime paied_at) {
            this.paied_at = paied_at;
        }
    }
    private PhotoResp cover;
    private Object data;
    private String text;

    private EntityModel booked;
    private LocalDateTime booking_created_at;
    private LocalDateTime booking_paied_at;

    public void setCover(PhotoResp cover) {
        this.cover = cover;
    }

    public PhotoResp getCover() {
        return cover;
    }

    public <K, V> void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setBooking_created_at(LocalDateTime booking_created_at) {
        this.booking_created_at = booking_created_at;
    }

    public LocalDateTime getBooking_created_at() {
        return booking_created_at;
    }

    public void setBooking_paied_at(LocalDateTime booking_paied_at) {
        this.booking_paied_at = booking_paied_at;
    }

    public LocalDateTime getBooking_paied_at() {
        return booking_paied_at;
    }

    public EntityModel getBooked() {
        return booked;
    }

    public void setBooked(EntityModel booked) {
        this.booked = booked;
    }

    public void withBookingInfo(EntityModel voucherTicket) {


        this.booked = voucherTicket;


    }

    public static class Venue{
        private LocationResp location;
        private String name;

        public LocationResp getLocation() {
            return location;
        }

        public void setLocation(LocationResp location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class BusTicket{
        private LocationResp location;
        private String name;

        public LocationResp getLocation() {
            return location;
        }

        public void setLocation(LocationResp location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DeliveryOption{
        private EnumDeliveryFormat deliveryFormat;
        private String deliveryValue;
        private String code_base64;

        public EnumDeliveryFormat getDeliveryFormat() {
            return deliveryFormat;
        }

        public void setDeliveryFormat(EnumDeliveryFormat deliveryFormat) {
            this.deliveryFormat = deliveryFormat;
        }

        public String getDeliveryValue() {
            return deliveryValue;
        }

        public void setDeliveryValue(String deliveryValue) {
            this.deliveryValue = deliveryValue;
        }

        public void setCode_base64(String code_base64) {

            this.code_base64 = code_base64;
        }

        public String getCode_base64() {
            return code_base64;
        }
    }

    private DeliveryOption deliveryOption;

    public DeliveryOption getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(DeliveryOption deliveryOption) {
        this.deliveryOption = deliveryOption;
    }




    //##@Column(unique=true)
private String code;

    private Boolean issued;
    private EnumVoucherStatus status;
    private String status_text;
    private String expiry_datetime_text;
    private String amount;
    private String type_text;
    private String crypto_code;
    private String crypto_id;
    private String crypto_code_shorter;

    private String code_base64_src;
    private EntityModel<AssetResp> asset;
    private List components;
    private String lable;
    private EntityModel venue;
    private LocalDate eventDate;
    private String section;
    private Integer seat;
    private Integer row;


    private PublicationPojo publication;

    private EntityModel<AssetResp> assets;


    private LocalDateTime expiry_datetime;
    private Integer expiry_seconds_remaining;

    private Integer expiry_days;

    public Integer getExpiry_days() {
        return expiry_days;
    }

    public void setExpiry_days(int expiry_days) {
        this.expiry_days = expiry_days;
    }

    public LocalDateTime getExpiry_datetime() {
        return expiry_datetime;
    }

    public void setExpiry_datetime(LocalDateTime expiry_datetime) {
        this.expiry_datetime = expiry_datetime;
    }

    public Integer getExpiry_seconds_remaining() {
        return expiry_seconds_remaining;
    }

    public void setExpiry_seconds_remaining(Integer expiry_seconds_remaining) {
        this.expiry_seconds_remaining = expiry_seconds_remaining;
    }
    public static VoucherTicketResp pcfrom(VoucherTicket voucher) {
        VoucherTicketResp voucherResp = new VoucherTicketResp();
        voucherResp.setCode(voucher.getCode());
        voucherResp.setLable(voucher.getLable());



        voucherResp.setType(voucher.getType());
        voucherResp.setType_text(voucher.getType().toString());
        voucherResp.setExpiry_datetime(voucher.getExpiry_datetime());

        voucherResp.setStatus(voucher.getStatus());
        voucherResp.setStatus_text(voucher.getStatus().toString());
        voucherResp.setCreatedDate(voucher.getCreatedDate());
        voucherResp.setModifiedDate(voucher.getModifiedDate());




        return voucherResp;
    }

    public static VoucherTicketResp from(VoucherTicket voucher) {
        VoucherTicketResp voucherResp = new VoucherTicketResp();
        voucherResp.setCode(voucher.getCode());
        voucherResp.setLable(voucher.getLable());



        voucherResp.setType(voucher.getType());
        voucherResp.setType_text(voucher.getType().toString());
        voucherResp.setExpiry_datetime(voucher.getExpiry_datetime());

        voucherResp.setStatus(voucher.getStatus());
        voucherResp.setStatus_text(voucher.getStatus().toString());





        return voucherResp;
    }



    public static VoucherTicketResp from(Quartet<PublicationEntry,Voucher,Campaign,List<Asset>> triplet, Optional<JwtUtils> jwtUtils) {
        PublicationEntry publicationEntry = triplet.getValue0();
        Voucher voucher = triplet.getValue1();
        Campaign campaign = triplet.getValue2();
        List<Asset> assets = triplet.getValue3();

        VoucherTicketResp voucherResp = new VoucherTicketResp();
        voucherResp.setCode(voucher.getCode());

        if(jwtUtils.isPresent()){
            voucherResp.setCrypto_code(jwtUtils.get().generateJwtToken(voucher.getCode()));
            voucherResp.setCrypto_id(jwtUtils.get().generateJwtToken(Long.toString(voucher.getId())));
            try {

                final byte[] xmlBytes = voucher.getCode().getBytes(StandardCharsets.UTF_8);
                final String xmlBase64 = Base64.getEncoder().encodeToString(xmlBytes);

                voucherResp.setCrypto_code_shorter(AESUtil.en(voucher.getCode()));
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }


        }
        voucherResp.setType(voucher.getType());
        voucherResp.setType_text(voucher.getType().toString());
        voucherResp.setAmount(Integer.valueOf(campaign.getAmount_off()).toString());

        voucherResp.setType(voucher.getType());



/*        voucherResp.setCampaign_description(campaign.getDescription());
        voucherResp.setCampaign_code(campaign.getCode());
        voucherResp.setPaied(publicationEntry.getPaied());
        voucherResp.setCharge(publicationEntry.getCharge());*/


        voucherResp.setActive(voucher.isActive());
        if(voucher.isActive()){
            voucherResp.setStart_date(voucher.getStart_date());
            voucherResp.setExpiry_datetime(voucher.getExpiration_date());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = voucher.getExpiration_date().format(formatter);
            voucherResp.setExpiry_datetime_text(formatDateTime);
            voucherResp.setExpiry_seconds_remaining((int)Duration.between(voucher.getExpiration_date(),LocalDateTime.now()).toSeconds());
        }
        voucherResp.setQuantity(voucher.getQuantity());
        voucherResp.setRedeemed_quantity(voucher.getRedeemed_quantity());
        voucherResp.setRedeemed_amount(voucher.getRedeemed_amount());
        voucherResp.setIssued(voucher.getPublished());






        voucherResp.setStatus(voucher.getStatus());

        voucherResp.setStatus_text(voucher.getStatus().toString());

        Optional<Asset> asset = assets.stream().filter(x->x.getType().equals(EnumAssetType.qr)).findAny();

        if(asset.isPresent()){
            voucherResp.setAssets(AssetResp.from(asset.get()));
        }
        return voucherResp;

    }

    public static VoucherTicketResp from(Triplet<Voucher,Campaign,List<Asset>> triplet, Optional<JwtUtils> jwtUtils) {
        Voucher voucher = triplet.getValue0();
        Campaign campaign = triplet.getValue1();
        List<Asset> assets = triplet.getValue2();

        VoucherTicketResp voucherResp = new VoucherTicketResp();
        voucherResp.setCode(voucher.getCode());

        if(jwtUtils.isPresent()){
            voucherResp.setCrypto_code(jwtUtils.get().generateJwtToken(voucher.getCode()));
            voucherResp.setCrypto_id(jwtUtils.get().generateJwtToken(Long.toString(voucher.getId())));
            try {
             //   String co = Base64.getUrlDecoder().decode(voucher.getCode());


          //      final byte[] xmlBytesDecoded = Base64.getDecoder().decode(xmlBase64);
              //  final String xmlDecoded = new String(xmlBytesDecoded, StandardCharsets.UTF_8);
              //  System.out.println(xmlDecoded);
                final byte[] xmlBytes = voucher.getCode().getBytes(StandardCharsets.UTF_8);
                final String xmlBase64 = Base64.getEncoder().encodeToString(xmlBytes);

                voucherResp.setCrypto_code_shorter(AESUtil.en(voucher.getCode()));
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }


        }
        voucherResp.setType(voucher.getType());
        voucherResp.setType_text(voucher.getType().toString());
        voucherResp.setAmount(Integer.valueOf(campaign.getAmount_off()).toString());










        voucherResp.setType(voucher.getType());


        voucherResp.setActive(voucher.isActive());
        if(voucher.isActive()){
            voucherResp.setStart_date(voucher.getStart_date());
            voucherResp.setExpiry_datetime(voucher.getExpiration_date());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = voucher.getExpiration_date().format(formatter);
            voucherResp.setExpiry_datetime_text(formatDateTime);
            voucherResp.setExpiry_seconds_remaining((int)Duration.between(voucher.getExpiration_date(),LocalDateTime.now()).toSeconds());
        }
        voucherResp.setQuantity(voucher.getQuantity());
        voucherResp.setRedeemed_quantity(voucher.getRedeemed_quantity());
        voucherResp.setRedeemed_amount(voucher.getRedeemed_amount());
        voucherResp.setIssued(voucher.getPublished());




        if(voucher.getStatus().equals(EnumVoucherStatus.Published)){
            voucherResp.setStatus(EnumVoucherStatus.Available);
        }else{

            voucherResp.setStatus(voucher.getStatus());

        }




        voucherResp.setStatus_text(voucherResp.getStatus().toString());

        Optional<Asset> asset = assets.stream().filter(x->x.getType().equals(EnumAssetType.qr)).findAny();

        if(asset.isPresent()){
            voucherResp.setAssets(AssetResp.from(asset.get()));
        }
        return voucherResp;

    }

    public void setAssets(EntityModel<AssetResp> assets) {
        this.assets = assets;
    }

    public EntityModel<AssetResp> getAssets() {
        return assets;
    }

    public void setIssued(Boolean issued) {
        this.issued = issued;
    }

    public Boolean getIssued() {
        return issued;
    }

    public void setStatus(EnumVoucherStatus status) {
        this.status = status;
    }

    public EnumVoucherStatus getStatus() {
        return status;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setExpiry_datetime_text(String expiry_datetime_text) {
        this.expiry_datetime_text = expiry_datetime_text;
    }

    public String getExpiry_datetime_text() {
        return expiry_datetime_text;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setCrypto_code(String crypto_code) {
        this.crypto_code = crypto_code;
    }

    public String getCrypto_code() {
        return crypto_code;
    }

    public void setCrypto_id(String crypto_id) {
        this.crypto_id = crypto_id;
    }

    public String getCrypto_id() {
        return crypto_id;
    }

    public void setCrypto_code_shorter(String crypto_code_shorter) {
        this.crypto_code_shorter = crypto_code_shorter;
    }

    public String getCrypto_code_shorter() {
        return crypto_code_shorter;
    }



    public void setCode_base64_src(String code_base64_src) {
        this.code_base64_src = code_base64_src;
    }

    public String getCode_base64_src() {
        return code_base64_src;
    }

    public void setAsset(EntityModel<AssetResp> asset) {
        this.asset = asset;
    }

    public EntityModel<AssetResp> getAsset() {
        return asset;
    }

    public <R> void setComponents(List components) {
        this.components = components;
    }

    public List getComponents() {
        return components;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getLable() {
        return lable;
    }

    public void setVenue(EntityModel venue) {
        this.venue = venue;
    }

    public EntityModel getVenue() {
        return venue;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getRow() {
        return row;
    }


    public static class PublicationPojo {

        private List<PublicationEntryResp> entries;
        private String object = "list";
        private Integer count;
        private String data_ref = "entries";

        public List<PublicationEntryResp> getEntries() {
            return entries;
        }

        public void setEntries(List<PublicationEntryResp> entries) {
            this.entries = entries;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getData_ref() {
            return data_ref;
        }

        public void setData_ref(String data_ref) {
            this.data_ref = data_ref;
        }
    }






    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private Long validatorSupplier;

    public Long getValidatorSupplier() {
        return validatorSupplier;
    }

    public void setValidatorSupplier(Long validatorSupplier) {
        this.validatorSupplier = validatorSupplier;
    }






    private String barcode_data;

    private String redemptionMethod;





    /*
    How the voucher can be redeemed. Possible values are:
    MANIFEST The guest name will be written down and they just need to show up
    DIGITAL The tickets/voucher must be scanned but can be on mobile
    PRINT The tickets/voucher must be printed and presented on arrival
*/






    private GiftVoucher giftVoucher ;







    private LocalDateTime start_date;
    private LocalDateTime expiration_date;

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

    private Boolean active;

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    private ComponentVounch componentVounch ;

    public ComponentVounch getComponentVounch() {
        return componentVounch;
    }

    public void setComponentVounch(ComponentVounch componentVounch) {
        this.componentVounch = componentVounch;
    }

    private EnumVoucherType type;

    public EnumVoucherType getType() {
        return type;
    }

    public void setType(EnumVoucherType type) {
        this.type = type;
    }








    private Long quantity;// (integer, required) - Default: null. How many times a voucher can be redeemed. A null value means unlimited.

    private Long redeemed_quantity;// (integer, required) - How many times a voucher has already been redeemed.

    private Integer redeemed_amount;// (integer) - Total Amount redeemed by the voucher. Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 balance is written as 10000.

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

    public Integer getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_amount(Integer redeemed_amount) {
        this.redeemed_amount = redeemed_amount;
    }




    public DiscountPojo getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountPojo discount) {
        this.discount = discount;
    }


    private DiscountPojo discount;

    public PublicationPojo getPublication() {
        return publication;
    }

    public void setPublication(PublicationPojo publication) {
        this.publication = publication;
    }

    public static class DiscountPojo {



        private Integer amount_off;
        private Integer percent_off;
        private Integer unit_off;
        private EnumDiscountVoucherCategory category;


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

        public EnumDiscountVoucherCategory getCategory() {
            return category;
        }

        public void setCategory(EnumDiscountVoucherCategory category) {
            this.category = category;
        }
    }



    public static class ShowtimeResp {

        private LocalDate eventDate;
        private EntityModel venue;
        @Column(name = "row_")
        private Integer row;
        private String section;
        private Integer seat;


        public static ShowtimeResp from(VoucherTicket.Showtime showtime) {

            ShowtimeResp showtimeResp = new ShowtimeResp();
            showtimeResp.setRow(showtime.getRow());
            showtimeResp.setSeat(showtime.getSeat());
            showtimeResp.setEventDate(showtime.getEventDate());
            showtimeResp.setSection(showtime.getSection());

            return showtimeResp;
        }

        public LocalDate getEventDate() {
            return eventDate;
        }

        public void setEventDate(LocalDate eventDate) {
            this.eventDate = eventDate;
        }

        public EntityModel getVenue() {
            return venue;
        }

        public void setVenue(EntityModel venue) {
            this.venue = venue;
        }

        public Integer getRow() {
            return row;
        }

        public void setRow(Integer row) {
            this.row = row;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public Integer getSeat() {
            return seat;
        }

        public void setSeat(Integer seat) {
            this.seat = seat;
        }

    }

}

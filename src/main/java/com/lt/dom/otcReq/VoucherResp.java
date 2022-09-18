package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.JwtUtils;
import com.lt.dom.OctResp.AssetResp;
import com.lt.dom.OctResp.PublicationEntryResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumVoucherStatus;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.util.AESUtil;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.springframework.hateoas.EntityModel;
import org.springframework.util.ObjectUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class VoucherResp {

    
@Column(unique=true) 
private String code;
    private String campaign;
    private String object = "voucher";
    private boolean issued;
    private EnumVoucherStatus status;
    private String status_text;
    private String expiry_datetime_text;
    private String amount;
    private String type_text;
    private String crypto_code;
    private String crypto_id;
    private String crypto_code_shorter;
    private String campaign_description;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    private PublicationPojo publication;

    private EntityModel<AssetResp> assets;


    private LocalDateTime expiry_datetime;
    private Integer expiry_seconds_remaining;

    private int expiry_days;

    public int getExpiry_days() {
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

    public static VoucherResp from(Voucher voucher) {
        VoucherResp voucherResp = new VoucherResp();
        voucherResp.setCode(voucher.getCode());
        voucherResp.setCampaign(voucher.getCampaign()+"");
        return voucherResp;
    }



    public static VoucherResp from(Quartet<PublicationEntry,Voucher,Campaign,List<Asset>> triplet, Optional<JwtUtils> jwtUtils) {
        PublicationEntry publicationEntry = triplet.getValue0();
        Voucher voucher = triplet.getValue1();
        Campaign campaign = triplet.getValue2();
        List<Asset> assets = triplet.getValue3();

        VoucherResp voucherResp = new VoucherResp();
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


        voucherResp.setCampaign(campaign.getName());
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

    public static VoucherResp from(Triplet<Voucher,Campaign,List<Asset>> triplet,Optional<JwtUtils> jwtUtils) {
        Voucher voucher = triplet.getValue0();
        Campaign campaign = triplet.getValue1();
        List<Asset> assets = triplet.getValue2();

        VoucherResp voucherResp = new VoucherResp();
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







/*
        if(voucher.getType().equals(EnumVoucherType.DISCOUNT_VOUCHER)){


            if(voucher.getType().equals(EnumDiscountVoucherCategory.AMOUNT)){
                discountPojo.setAmount_off(discount.get().getAmount_off());

            }
            if(discount.get().getType().equals(EnumDiscountVoucherCategory.PERCENT)){
                discountPojo.setPercent_off(discount.get().getPercent_off());

            }
            if(discount.get().getType().equals(EnumDiscountVoucherCategory.UNIT)){
                discountPojo.setUnit_off(discount.get().getUnit_off());

            }
            voucherResp.setDiscount(discountPojo);
        }

*/



        voucherResp.setType(voucher.getType());


        voucherResp.setCampaign(campaign.getName());
        voucherResp.setCampaign_description(campaign.getDescription());

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

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public boolean getIssued() {
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

    public void setCampaign_description(String campaign_description) {
        this.campaign_description = campaign_description;
    }

    public String getCampaign_description() {
        return campaign_description;
    }


    public static class PublicationPojo {

        private List<PublicationEntryResp> entries;
        private String object = "list";
        private int count;
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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
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

    private long validatorSupplier;

    public long getValidatorSupplier() {
        return validatorSupplier;
    }

    public void setValidatorSupplier(long validatorSupplier) {
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
    private LocalDate expiration_date;

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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

    private int redeemed_amount;// (integer) - Total Amount redeemed by the voucher. Value is multiplied by 100 to precisely represent 2 decimal places. For example, $100 balance is written as 10000.

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

    public int getRedeemed_amount() {
        return redeemed_amount;
    }

    public void setRedeemed_amount(int redeemed_amount) {
        this.redeemed_amount = redeemed_amount;
    }


    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCampaign() {
        return campaign;
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



        private int amount_off;
        private int percent_off;
        private int unit_off;
        private EnumDiscountVoucherCategory category;


        public int getAmount_off() {
            return amount_off;
        }

        public void setAmount_off(int amount_off) {
            this.amount_off = amount_off;
        }

        public int getPercent_off() {
            return percent_off;
        }

        public void setPercent_off(int percent_off) {
            this.percent_off = percent_off;
        }

        public int getUnit_off() {
            return unit_off;
        }

        public void setUnit_off(int unit_off) {
            this.unit_off = unit_off;
        }

        public EnumDiscountVoucherCategory getCategory() {
            return category;
        }

        public void setCategory(EnumDiscountVoucherCategory category) {
            this.category = category;
        }
    }
}

package com.lt.dom.otcReq;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lt.dom.otcenum.EnumCompaignType;
import com.lt.dom.otcenum.EnumDiscountVoucherCategory;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.util.AESUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.List;


public class CompaignPojo {





    public static class Code_config {

        private Integer length;
        private String charset;
        private String prefix;
        private String postfix;


        private String pattern ="TT-NI-############";

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
        /*           "length": 8,
                   "charset": "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
                   "prefix": null,
                   "postfix": null,
                   "pattern": "TC6-PROMO-#######"*/
    }

    @NotNull
    private EnumCompaignType campaignType = EnumCompaignType.LOYALTY_PROGRAM;

    @NotEmpty
    @Size(max = 50)
    private String name;

    @NotEmpty
    @Size(max = 100)
    private String name_long;

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime start_date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime expiration_date;

    @NotNull
    private Code_config code_config = new CompaignPojo.Code_config();

    @NotNull
    private long scenario;

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getScenario() {
        return scenario;
    }

    public void setScenario(long scenario) {
        this.scenario = scenario;
    }

    public Code_config getCode_config() {
        return code_config;
    }

    public void setCode_config(Code_config code_config) {
        this.code_config = code_config;
    }



  //  @NotNull
    private List<Long> recipients;

    public List<Long> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Long> recipients) {
        this.recipients = recipients;
    }

    @NotNull
    private VoucherPojo voucher;

    public class VoucherPojo {

        @NotNull
        private EnumVoucherType type;


        private int amount_off;
        private int percent_off;
        private int unit_off;
        private EnumDiscountVoucherCategory category;

        public EnumVoucherType getType() {
            return type;
        }

        public void setType(EnumVoucherType type) {
            this.type = type;
        }

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

    public VoucherPojo getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherPojo voucher) {
        this.voucher = voucher;
    }

    private int vouchers_count;
    private String vouchers_generation_status; //IN_PROGRESS, DONE, ERROR

    @NotEmpty
    @Size(max = 500)
    private String description;

    @Size(max = 200)
    private String claim_note;
    @NotEmpty
    @Size(max = 15)
    private String claim_text;

    private boolean claimable;

    @NotNull
    private boolean pay;
    private int pay_amount;

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public int getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(int pay_amount) {
        this.pay_amount = pay_amount;
    }

    private int expiry_days;

    public int getExpiry_days() {
        return expiry_days;
    }

    public void setExpiry_days(int expiry_days) {
        this.expiry_days = expiry_days;
    }




    public boolean isClaimable() {
        return claimable;
    }

    public void setClaimable(boolean claimable) {
        this.claimable = claimable;
    }

    @NotNull
    private Integer clain_limit = 1;

    public Integer getClain_limit() {
        return clain_limit;
    }

    public void setClain_limit(Integer clain_limit) {
        this.clain_limit = clain_limit;
    }
    @NotNull
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



    public int getVouchers_count() {
        return vouchers_count;
    }

    public void setVouchers_count(int vouchers_count) {
        this.vouchers_count = vouchers_count;
    }

    public String getVouchers_generation_status() {
        return vouchers_generation_status;
    }

    public void setVouchers_generation_status(String vouchers_generation_status) {
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


    public String getClaim_note() {
        return claim_note;
    }

    public void setClaim_note(String claim_note) {
        this.claim_note = claim_note;
    }

    public String getClaim_text() {
        return claim_text;
    }

    public void setClaim_text(String claim_text) {
        this.claim_text = claim_text;
    }

    public static class Student implements Serializable {
        private String name;
        private long age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getAge() {
            return age;
        }

        public void setAge(long age) {
            this.age = age;
        }
// standard setters and getters
    }

     public static void main(String[] a)
            throws InvalidKeySpecException, NoSuchAlgorithmException,
            IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
            InvalidAlgorithmParameterException, NoSuchPaddingException {

         LocalDateTime now = LocalDateTime.now();
         Timestamp timestamp = Timestamp.valueOf(now);

         System.out.println(now);            // 2019-06-14T15:50:36.068076300
         System.out.println(timestamp.getTime());      // 2019-06-14 15:50:36.0680763

         //  Timestamp to LocalDateTime
         LocalDateTime localDateTime = timestamp.toLocalDateTime();

         System.out.println(localDateTime);  // 2019-06-14T15:50:36

         Student student = new Student();
         student.setAge(timestamp.getTime());
         student.setName("www.baeldung.com");
         JSON.toJSONString(student);
        String plainText =JSON.toJSONString(student);// "www.baeldung.com"+ timestamp.getTime();
        String password = "baeldung";
        String salt = "12345678";
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        SecretKey key = AESUtil.getKeyFromPassword(password,salt);
        String cipherText = AESUtil.encryptPasswordBased(plainText, key, ivParameterSpec);
        String decryptedCipherText = AESUtil.decryptPasswordBased(
                cipherText, key, ivParameterSpec);

        System.out.println("cipherText"+cipherText);

        System.out.println("plainText"+plainText);
        System.out.println("decryptedCipherText"+decryptedCipherText);

    }
}

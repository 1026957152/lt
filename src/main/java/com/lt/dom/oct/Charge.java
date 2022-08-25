package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumChargeStatus;
import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.EnumRefundStatus;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Charge {

    @Version
    private Integer version;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String orderId;
    private String code;
    private int application_fee_amount;

    @NotNull
    private long payment;
    private long refund;
    private EnumRefundStatus refundStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private LocalDateTime created;
    private Boolean livemode;
    private Boolean paid;
    private boolean refunded;
    private Boolean reversed;
    private String app;

    private EnumPayChannel channel;
    @NotNull
    private long booking;
    private String clientIp;
    private Integer amountX;
    private Integer amountSettle;
    private String currency;
    private String subject;
    private String body;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public Boolean getReversed() {
        return reversed;
    }

    public void setReversed(Boolean reversed) {
        this.reversed = reversed;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public EnumPayChannel getChannel() {
        return channel;
    }

    public void setChannel(EnumPayChannel channel) {
        this.channel = channel;
    }

    public long getBooking() {
        return booking;
    }

    public void setBooking(long orderNo) {
        this.booking = orderNo;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getAmountX() {
        return amountX;
    }

    public void setAmountX(Integer amountX) {
        this.amountX = amountX;
    }

    public Integer getAmountSettle() {
        return amountSettle;
    }

    public void setAmountSettle(Integer amountSettle) {
        this.amountSettle = amountSettle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ExtraDTO getExtra() {
        return extra;
    }

    public void setExtra(ExtraDTO extra) {
        this.extra = extra;
    }

    public LocalDateTime getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(LocalDateTime timePaid) {
        this.timePaid = timePaid;
    }

    public Integer getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(Integer timeExpire) {
        this.timeExpire = timeExpire;
    }

    public LocalDateTime getTimeSettle() {
        return timeSettle;
    }

    public void setTimeSettle(LocalDateTime timeSettle) {
        this.timeSettle = timeSettle;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public RefundsDTO getRefunds() {
        return refunds;
    }

    public void setRefunds(RefundsDTO refunds) {
        this.refunds = refunds;
    }

    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Integer amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getFailureMsg() {
        return failureMsg;
    }

    public void setFailureMsg(String failureMsg) {
        this.failureMsg = failureMsg;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public CredentialDTO getCredential() {
        return credential;
    }

    public void setCredential(CredentialDTO credential) {
        this.credential = credential;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    private ExtraDTO extra;
    private LocalDateTime timePaid;
    private Integer timeExpire;
    private LocalDateTime timeSettle;
    private String transactionNo;
    @Transient
    private RefundsDTO refunds;
    private Integer amountRefunded;
    private String failureCode;
    private String failureMsg;
    @Length(max = 2000)
    private String metadata;
    @Transient
    private CredentialDTO credential;
    private String description;

    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setApplication_fee_amount(int application_fee_amount) {
        this.application_fee_amount = application_fee_amount;
    }

    public int getApplication_fee_amount() {
        return application_fee_amount;
    }

    public void setPayment(long payment) {
        this.payment = payment;
    }

    public long getPayment() {
        return payment;
    }

    public void setRefund(long refund) {
        this.refund = refund;
    }

    public long getRefund() {
        return refund;
    }

    public EnumRefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(EnumRefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public static class ExtraDTO {
    }

    public static class RefundsDTO {
        private String object;
        private String url;
        private Boolean hasMore;
        private List<?> data;
    }


    public static class MetadataDTO {
    }


    public static class CredentialDTO {
        private String object;
        private UpacpDTO upacp;


        public static class UpacpDTO {
            private String tn;
            private String mode;
        }
    }
    private EnumChargeStatus status;

    public EnumChargeStatus getStatus() {
        return status;
    }

    public void setStatus(EnumChargeStatus status) {
        this.status = status;
    }

    private long customer;

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }
/*     "id": "ch_L8qn10mLmr1GS8e5OODmHaL4",
              "object": "charge",
              "created": 1410834527,
              "livemode": true,
              "paid": false,
              "refunded": false,
              "reversed": false,
              "app": "app_1Gqj58ynP0mHeX1q",
              "channel": "upacp",
              "order_no": "123456789",
              "client_ip": "127.0.0.1",
              "amount": 100,
              "amount_settle": 100,
              "currency": "cny",
              "subject": "Your Subject",
              "body": "Your Body",
              "extra":{},
            "time_paid": null,
            "time_expire": 1410838127,
            "time_settle": null,
            "transaction_no": null,
            "refunds": {
        "object": "list",
                "url": "/v1/charges/ch_L8qn10mLmr1GS8e5OODmHaL4/refunds",
                "has_more": false,
                "data": [ ]
    },
            "amount_refunded": 0,
            "failure_code": null,
            "failure_msg": null,
            "metadata": {},
            "credential": {
        "object": "credential",
                "upacp": {
            "tn": "201409161028470000000",
                    "mode": "01"
        }
    },
            "description": null*/


    private long timeStamp;//: '',
    private long nonceStr;//: '',
    private long package_;//: '',
    private long signType;//: 'MD5',
    private long paySign;//: '',
}

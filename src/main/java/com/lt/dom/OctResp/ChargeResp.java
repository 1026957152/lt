package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Charge;
import com.lt.dom.oct.Refund;
import com.lt.dom.otcenum.EnumChargeStatus;
import com.lt.dom.otcenum.EnumPayChannel;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargeResp {


    private String orderId;
    
//##@Column(unique=true)
private String code;


    private LocalDateTime created;
    private Boolean livemode;
    private Boolean paid;
    private Boolean refunded;
    private Boolean reversed;
    private String app;

    private EnumPayChannel channel;
    private long orderNo;
    private String clientIp;
    private Integer amountX;
    private Integer amountSettle;
    private String currency;
    private String subject;
    private String body;
    private String status_text;
    private String channel_text;

    public static ChargeResp from(Charge x) {
        ChargeResp chargeResp = new ChargeResp();
        chargeResp.setAmount(x.getAmount());
        chargeResp.setCode(x.getCode());
        chargeResp.setCreated(x.getCreated());
        chargeResp.setPaid(x.getPaid());
        chargeResp.setRefunded(x.getRefunded());
        chargeResp.setOrderNo(x.getBooking());
        chargeResp.setStatus(x.getStatus());
        chargeResp.setStatus_text(x.getStatus().toString());
        chargeResp.setChannel_text(x.getChannel().toString());
        chargeResp.setChannel(x.getChannel());
        chargeResp.setLivemode(x.getLivemode());

        chargeResp.setCustomer(x.getPayer());
        chargeResp.setBody(x.getBody());
        chargeResp.setTransactionNo(x.getTransactionNo());
        chargeResp.setTransactionNo(x.getTransactionNo());
        chargeResp.setAmountRefunded(x.getAmountRefunded());

        return chargeResp;
    }

    public static ChargeResp from(Charge x, Map<Long, List<Refund>> longListMap) {
        ChargeResp chargeResp = ChargeResp.from(x);

        chargeResp.setRefunds(longListMap.getOrDefault(x.getId(), Arrays.asList()).stream().map(e->{
            return RefundResp.from(e);

        }).collect(Collectors.toList()));
        return chargeResp;
    }

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

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
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

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
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

    public List<RefundResp> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<RefundResp> refunds) {
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

    public MetadataDTO getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataDTO metadata) {
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

    private List<RefundResp> refunds;
    private Integer amountRefunded;
    private String failureCode;
    private String failureMsg;
    @Transient
    private MetadataDTO metadata;
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

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setChannel_text(String channel_text) {
        this.channel_text = channel_text;
    }

    public String getChannel_text() {
        return channel_text;
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

}

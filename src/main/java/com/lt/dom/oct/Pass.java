package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
@Entity
public class Pass  extends Base{



    @NotNull
    String label;
    private String productPassUuid;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    LocalDateTime card_soft_expiry;

    @NotNull
    private EnumCardFullfullmentStatus fulfillment_status ;//digital tickets or PDF tickets



    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumCardPersonalized personal;
    @Enumerated(EnumType.STRING)
    private EnumFormFactorType formFactor;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "pass_cardholder",
            joinColumns =
                    { @JoinColumn(name = "pass_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "cardholder_id", referencedColumnName = "id") })
    private Cardholder cardholder;



    @NotNull
    private String number;
    private long booking;
    private String pin;
    private long bookingLine;
    private long owner;

    public EnumCardFullfullmentStatus getFulfillment_status() {
        return fulfillment_status;
    }

    public void setFulfillment_status(EnumCardFullfullmentStatus fulfillment_status) {
        this.fulfillment_status = fulfillment_status;
    }


    @Enumerated(EnumType.STRING)
    private EnumCardType type ;//digital tickets or PDF tickets

    @NotEmpty
    @Column(updatable = false)
    private String code ;//digital tickets or PDF tickets

    private String card_number ;//digital tickets or PDF tickets

    private String last_four ;//digital tickets or PDF tickets
    private String last4 ;//digital tickets or PDF tickets


    private String cvc_check ;//digital tickets or PDF tickets



    @Enumerated(EnumType.STRING)
    private EnumPassShippingStatus shipping_statis;
    private long product;

    private Long bulkIssuanceId;

    public Long getBulkIssuanceId() {
        return bulkIssuanceId;
    }

    public void setBulkIssuanceId(Long bulk_issuance_id) {
        this.bulkIssuanceId = bulk_issuance_id;
    }

    private long productPass;


    @Enumerated(EnumType.STRING)
    private EnumReasonCode status_reason_code;

    @NotNull
    private Long supplier;


    public String getCode() {
        return code;
    }
    private long user ;//digital tickets or PDF tickets

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public EnumCardType getType() {
        return type;
    }

    public void setType(EnumCardType type) {
        this.type = type;
    }


    @NotNull
    private Integer duration ;//digital tickets or PDF tickets


    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumPassDorationUnit durationUnit ;//digital tickets or PDF tickets
    private LocalDateTime maxActivationDate ;//digital tickets or PDF tickets

    private LocalDateTime expiringDate ;//digital tickets or PDF tickets

    public LocalDateTime getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(LocalDateTime expiringDate) {
        this.expiringDate = expiringDate;
    }

    private LocalDateTime emissionDate ;//digital tickets or PDF tickets


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EnumPassDorationUnit getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(EnumPassDorationUnit durationUnit) {
        this.durationUnit = durationUnit;
    }

    public LocalDateTime getMaxActivationDate() {
        return maxActivationDate;
    }

    public void setMaxActivationDate(LocalDateTime maxActivationDate) {
        this.maxActivationDate = maxActivationDate;
    }

    private Integer balance ;//digital tickets or PDF tickets
    private Integer remaining ;//digital tickets or PDF tickets



    private boolean active ;//digital tickets or PDF tickets\\\


    @Enumerated(EnumType.STRING)
    private EnumCardStatus status ;//digital tickets or PDF tickets

    private EnumPassShippingStatus shipping_status ;//digital tickets or PDF tickets


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public EnumCardStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCardStatus status) {
        this.status = status;
    }

    private LocalDateTime deliver_date ;//digital tickets or PDF tickets



    @Transient
    List activities;

    public void setShipping_statis(EnumPassShippingStatus shipping_statis) {
        this.shipping_statis = shipping_statis;
    }

    public EnumPassShippingStatus getShipping_statis() {
        return shipping_statis;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getProduct() {
        return product;
    }

    public void setProductPass(long productPass) {
        this.productPass = productPass;
    }

    public long getProductPass() {
        return productPass;
    }

    public void setStatus_reason_code(EnumReasonCode status_reason_code) {
        this.status_reason_code = status_reason_code;
    }

    public EnumReasonCode getStatus_reason_code() {
        return status_reason_code;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setPersonal(EnumCardPersonalized personal) {
        this.personal = personal;
    }

    public EnumCardPersonalized getPersonal() {
        return personal;
    }

    public void setFormFactor(EnumFormFactorType formFactor) {
        this.formFactor = formFactor;
    }

    public EnumFormFactorType getFormFactor() {
        return formFactor;
    }

    public Cardholder getCardholder() {
        return cardholder;
    }

    public void setCardholder(Cardholder cardholder) {
        this.cardholder = cardholder;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }

    public long getBooking() {
        return booking;
    }

    public void setPin(String cvc) {
        this.pin = cvc;
    }

    public String getPin() {
        return pin;
    }

    public void setBookingLine(long bookingLine) {
        this.bookingLine = bookingLine;
    }

    public long getBookingLine() {
        return bookingLine;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getOwner() {
        return owner;
    }

    public void setProductPassUuid(String productPassUuid) {
        this.productPassUuid = productPassUuid;
    }

    public String getProductPassUuid() {
        return productPassUuid;
    }


    // https://www.giftrocket.com/docs
}

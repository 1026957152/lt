package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class VoucherTicket extends Base{




//##@Column(unique=true) 
    private String code;

    private long relateId;
    private boolean published;
    private String additionalInfo;

    private LocalDateTime expiry_datetime;


    @Enumerated(EnumType.STRING)
    private EnumVoucherStatus status;
    private long publishTo;


    @Enumerated(EnumType.STRING)
    private EnumDuration duration;
    private Long booking;
    private Long user;
    private Long voucher;
    private String lable;
    private String data_json;

    public void setData_json(String data_json) {
        this.data_json = data_json;
    }

    public String getData_json() {
        return data_json;
    }

    public static class Showtime {

        private LocalDate eventDate;
        private Long venue;
        @Column(name = "row_")
        private Integer row;
        private String section;
        private Integer seat;
        private String venueName;

        public LocalDate getEventDate() {
            return eventDate;
        }

        public void setEventDate(LocalDate eventDate) {
            this.eventDate = eventDate;
        }

        public Long getVenue() {
            return venue;
        }

        public void setVenue(Long venue) {
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

        public String getVenueName() {
            return venueName;
        }

        public void setVenueName(String venueName) {
            this.venueName = venueName;
        }
    }


    public static class Attraction {


        private Long attraction;


        public void setAttraction(Long attraction) {
            this.attraction = attraction;
        }

        public Long getAttraction() {
            return attraction;
        }
    }


    public static class BusTicket {

        private Long sku;

        public Long getSku() {
            return sku;
        }

        public void setSku(Long sku) {
            this.sku = sku;
        }
    }



    public EnumDuration getDuration() {
        return duration;
    }

    public void setDuration(EnumDuration duration) {
        this.duration = duration;
    }


    @Enumerated(EnumType.STRING)
    private EnumPublicationObjectType publishToType;

    public LocalDateTime getExpiry_datetime() {
        return expiry_datetime;
    }

    public void setExpiry_datetime(LocalDateTime expiry_datetime) {
        this.expiry_datetime = expiry_datetime;
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


    @Transient
    private List<ComponentVounch> componentVounchList;

    public List<ComponentVounch> getComponentRightVounchList() {
        return componentVounchList;
    }

    public void setComponentRightVounchList(List<ComponentVounch> componentVounchList) {
        this.componentVounchList = componentVounchList;
    }

    /*
    How the voucher can be redeemed. Possible values are:
    MANIFEST The guest name will be written down and they just need to show up
    DIGITAL The tickets/voucher must be scanned but can be on mobile
    PRINT The tickets/voucher must be printed and presented on arrival
*/






    @Transient
    private Discount discount ;
    @Transient
    private GiftVoucher giftVoucher ;




    @Transient
    private List assets;



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

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Transient
    private ComponentVounch componentVounch ;

    public ComponentVounch getComponentVounch() {
        return componentVounch;
    }

    public void setComponentVounch(ComponentVounch componentVounch) {
        this.componentVounch = componentVounch;
    }

    @Enumerated(EnumType.STRING)
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



    public void setRelateId(long relateId) {
        this.relateId = relateId;
    }

    public long getRelateId() {
        return relateId;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean getPublished() {
        return published;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }


    public void setStatus(EnumVoucherStatus status) {
        this.status = status;
    }

    public EnumVoucherStatus getStatus() {
        return status;
    }

    public void setPublishTo(long publishTo) {
        this.publishTo = publishTo;
    }

    public long getPublishTo() {
        return publishTo;
    }

    public void setPublishToType(EnumPublicationObjectType publishToType) {
        this.publishToType = publishToType;
    }

    public EnumPublicationObjectType getPublishToType() {
        return publishToType;
    }

    public void setBooking(Long booking) {
        this.booking = booking;
    }

    public Long getBooking() {
        return booking;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getUser() {
        return user;
    }

    public void setVoucher(Long voucher) {
        this.voucher = voucher;
    }

    public Long getVoucher() {
        return voucher;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getLable() {
        return lable;
    }



    public static class MultiTicket {

        private LocalDate eventDate;
        private Long venue;
        @Column(name = "row_")
        private Integer row;
        private String section;
        private Integer seat;
        private String venueName;

        public LocalDate getEventDate() {
            return eventDate;
        }

        public void setEventDate(LocalDate eventDate) {
            this.eventDate = eventDate;
        }

        public Long getVenue() {
            return venue;
        }

        public void setVenue(Long venue) {
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

        public String getVenueName() {
            return venueName;
        }

        public void setVenueName(String venueName) {
            this.venueName = venueName;
        }
    }


}

package com.lt.dom.oct;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class Publication {



    private String source_id;
    private long campaign_id;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;


    private int count;
    private long campaign;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Transient
    private List<Long> voucher_id;

    @Transient
    List<Customer> customerList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(List<Long> voucher_id) {
        this.voucher_id = voucher_id;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }


    public LocalDateTime published_at;

    public LocalDateTime getPublished_at() {
        return published_at;
    }

    public void setPublished_at(LocalDateTime published_at) {
        this.published_at = published_at;
    }

    public void setCampaign(long campaign) {
        this.campaign = campaign;
    }

    public long getCampaign() {
        return campaign;
    }
}

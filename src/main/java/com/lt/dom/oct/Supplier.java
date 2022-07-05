package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.domain.SettleAccount;

import javax.persistence.*;

@Entity
public class Supplier {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Transient
    private PingxxUser pingxxUser;

    @Transient
    private SettleAccount settleAccount;

    public SettleAccount getSettleAccount() {
        return settleAccount;
    }

    public void setSettleAccount(SettleAccount settleAccount) {
        this.settleAccount = settleAccount;
    }

    @JsonProperty("no")
    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @JsonProperty("name")
    private String name;
    @JsonProperty("endpoint")
    private String endpoint;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    @JsonProperty("contact")
    private ContactDTO contact;


    @Transient
    private Supplier parant;

    public static class ContactDTO {
        @JsonProperty("website")
        private String website;
        @JsonProperty("email")
        private String email;
        @JsonProperty("telephone")
        private String telephone;
        @JsonProperty("address")
        private String address;
    }
}

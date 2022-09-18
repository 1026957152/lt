package com.lt.dom.otcReq;

import com.lt.dom.oct.Reservation;
import com.lt.dom.otcenum.EnumBookingOjbectType;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class WindowsTicketBookingPojo {



    @NotNull
    TravelerReq traveler;
    private String id;

    public TravelerReq getTraveler() {
        return traveler;
    }

    public void setTraveler(TravelerReq traveler) {
        this.traveler = traveler;
    }

    @Size(min=1, max=100)
    List<TravelerReq> travelers;


    @NotNull
    @Size(min=1, max=100)
    List<ProductReq> products;
    private Reservation reservation;

    public List<ProductReq> getProducts() {
        return products;
    }

    public void setProducts(List<ProductReq> products) {
        this.products = products;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static class ProductReq {

        private Long count;///
        @NotEmpty
        private String id;

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }



    public List<TravelerReq> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<TravelerReq> travelers) {
        this.travelers = travelers;
    }


    public static class TravelerReq {

        public EnumProductPricingTypeByPerson getBy() {
            return by;
        }

        public void setBy(EnumProductPricingTypeByPerson by) {
            this.by = by;
        }

        private EnumProductPricingTypeByPerson by;
        @NotEmpty
        private String name;///

        private String family_name;///
        private String given_name;///
        @NotEmpty
        private String id;
        @NotEmpty
        private String tel_home;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFamily_name() {
            return family_name;
        }

        public void setFamily_name(String family_name) {
            this.family_name = family_name;
        }

        public String getGiven_name() {
            return given_name;
        }

        public void setGiven_name(String given_name) {
            this.given_name = given_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel_home() {
            return tel_home;
        }

        public void setTel_home(String tel_home) {
            this.tel_home = tel_home;
        }
    }

}

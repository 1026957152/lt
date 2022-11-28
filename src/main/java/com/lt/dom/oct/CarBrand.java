package com.lt.dom.oct;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//https://metmuseum.github.io/
@Entity
@Table(name = "t_car_brand")
public class CarBrand extends Base {


    private String name;

    private String capital;
    private String logo_url;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }
}

//https://allmyles.docs.apiary.io/#reference/rental-cars/rental-cars-collection/search-cars?console=1
package com.lt.dom.OctResp;


import com.lt.dom.oct.Base;
import com.lt.dom.oct.CarBrand;

import javax.persistence.Entity;
import javax.persistence.Table;


//https://metmuseum.github.io/

public class CarBrandResp {

    
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

    public static CarBrandResp of(CarBrand e) {

        CarBrandResp carBrandResp = new CarBrandResp();
        carBrandResp.setCapital(e.getCapital());
        carBrandResp.setLogo_url(e.getLogo_url());
        carBrandResp.setName(e.getName());
        return carBrandResp;
    }
}

//https://allmyles.docs.apiary.io/#reference/rental-cars/rental-cars-collection/search-cars?console=1
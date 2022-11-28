package com.lt.dom.oct;


import javax.persistence.Column;
import javax.persistence.Entity;


//https://metmuseum.github.io/
@Entity
public class CarRant extends Base {


    private String name;
    @Column(name = "rank_")
    private Integer rank;
    private String description;
    private String name_long;




    private String desc_short;
    private String desc_long;
    private String traits_transmission;
    private String traits_air_conditioning;
    private String traits_type;
    private String traits_class;

    private String price;

    private Boolean unlimited;
    private Boolean available;


}

//https://allmyles.docs.apiary.io/#reference/rental-cars/rental-cars-collection/search-cars?console=1
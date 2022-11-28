package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumSkuType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Sku extends Base{

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="zonePricings", nullable=false)
    private MovieProduct movieProduct;
    private long product;


    @Enumerated(EnumType.STRING)
    private EnumSkuType skuType;

    public MovieProduct getMovieProduct() {
        return movieProduct;
    }

    public void setMovieProduct(MovieProduct movieProduct) {
        this.movieProduct = movieProduct;
    }

    private Long seatingLayoutId;//	The displayable seat name
    private Long pricingType;
    private Long zone;

    public Long getSeatingLayoutId() {
        return seatingLayoutId;
    }

    public void setSeatingLayoutId(Long seatingLayoutId) {
        this.seatingLayoutId = seatingLayoutId;
    }


    @Transient
    private List<ZoneRow> rows;//	The seat's row

    private String name;//	The displayable seat name


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPricingType(Long pricingType) {
        this.pricingType = pricingType;
    }

    public Long getPricingType() {
        return pricingType;
    }

    public void setZone(Long zone) {
        this.zone = zone;
    }

    public Long getZone() {
        return zone;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getProduct() {
        return product;
    }
}

package com.lt.dom.oct;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class MovieProduct extends Base{




    @OneToMany(mappedBy="movieProduct",
            cascade = CascadeType.ALL)
    private List<Sku> skus;

    public List<Sku> getZonePricings() {
        return skus;
    }

    public void setZonePricings(List<Sku> skus) {
        this.skus = skus;
    }

    private long product;


    private Long movie;
    private Long seatingLayout;
    private Long theatre;

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public Long getMovie() {
        return movie;
    }

    public void setMovie(Long movie) {
        this.movie = movie;
    }

    public void setSeatingLayout(Long seatingLayout) {
        this.seatingLayout = seatingLayout;
    }

    public Long getSeatingLayout() {
        return seatingLayout;
    }

    public void setTheatre(Long theatre) {
        this.theatre = theatre;
    }

    public Long getTheatre() {
        return theatre;
    }


// https://www.giftrocket.com/docs
}

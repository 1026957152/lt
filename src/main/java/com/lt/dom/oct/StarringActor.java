package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public  class StarringActor extends Base{
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name="movie", nullable=false)
    private Movie movie;



        private String name;//	The name or title.


    @Column(name = "desc_")
        private String desc;//	The short print friendly name.
    private String uuid;


    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }
}
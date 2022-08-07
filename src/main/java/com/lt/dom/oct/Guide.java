package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumValidatorRedemExtent;

import javax.persistence.*;
import java.util.List;


@Entity
public class Guide {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;



    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

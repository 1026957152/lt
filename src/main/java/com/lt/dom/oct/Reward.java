package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumRewardType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Reward {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    private EnumRewardType type;

    private String idId;
    private String url;
    @NotNull
    private long stock;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Voucher {
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

    private String barcode_data;

    private String redemptionMethod;


    @Transient
    private List<ComponentRightVounch> componentRightVounchList;

    public List<ComponentRightVounch> getComponentRightVounchList() {
        return componentRightVounchList;
    }

    public void setComponentRightVounchList(List<ComponentRightVounch> componentRightVounchList) {
        this.componentRightVounchList = componentRightVounchList;
    }

    /*
    How the voucher can be redeemed. Possible values are:
    MANIFEST The guest name will be written down and they just need to show up
    DIGITAL The tickets/voucher must be scanned but can be on mobile
    PRINT The tickets/voucher must be printed and presented on arrival
*/

}

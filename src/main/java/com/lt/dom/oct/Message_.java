package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumProductMessage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Message_ {
    @Version
    private Integer version;
    @Id
    private long id;

    private String channel;

    private EnumProductMessage type;
}

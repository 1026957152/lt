package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumProductMessage;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message {

    @Id
    private long id;

    private String channel;

    private EnumProductMessage type;
}

package com.lt.dom.oct;


import javax.persistence.*;

@Entity
public class Dispute {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

}

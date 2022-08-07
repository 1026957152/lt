package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dispute {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

}

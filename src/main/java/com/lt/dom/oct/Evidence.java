package com.lt.dom.oct;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Evidence {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
}

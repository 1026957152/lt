package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumCancel_reason;
import com.lt.dom.otcenum.EnumValidatorRedemExtent;

import javax.persistence.*;
import java.util.List;


@Entity
public class Cancellation {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;



    private EnumCancel_reason cancel_reason;
    private String note;

}

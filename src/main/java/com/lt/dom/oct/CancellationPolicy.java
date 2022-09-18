package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumCancellUntil;
import com.lt.dom.otcenum.EnumPenaltyType;
import com.lt.dom.otcenum.EnumValidatorRedemExtent;

import javax.persistence.*;
import java.util.List;


//https://developer.expediapartnersolutions.com/reference/constructing-cancellation-policies/@lombok.NoArgsConstructor
@lombok.Data
@Entity
public class CancellationPolicy {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;


    private String start;
    private String end;
    private String formatted;

    private EnumPenaltyType penaltyType;

    private Integer amount;
    private Integer percent;

    private EnumCancellUntil until;

    private Integer Penalty_after_reservation; //Penalty (%) after reservation
    private Integer Penalty_or_night_reservation; //Penalty (% or night) after deadline


}

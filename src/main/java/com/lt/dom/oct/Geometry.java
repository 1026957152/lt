package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumQuotaClaimOrRedeem;
import com.lt.dom.otcenum.EnumQuotaType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
//https://www.ibm.com/docs/en/icvi/base?topic=routing-points-interest-poi-api
@Entity
public class Geometry {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;



/*
    {
        "type": "Feature",
            "id": "poi-xxx-yyy-zzz",
            "properties": {
        "building_name": "IBM Hakozaki",
                "phone_number": "090-1234-56789-000025",
                "navigation_points": [
        {
            "coordinates": [
            139.786601,
                    35.679782
                ],
            "heading": 180
        }
        ]
    },
        "geometry": {
        "type": "Point",
                "coordinates": [
        35.678581,
                139.787306
        ]
    }
    }*/

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

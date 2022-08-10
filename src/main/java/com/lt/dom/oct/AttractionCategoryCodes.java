package com.lt.dom.oct;

import javax.persistence.Version;

public enum AttractionCategoryCodes {


    market("market",29),
    restaurant("restaurant",41),
    mountain("mountain",31),
    lake("lake",25),

    river("river",42),
    beach("beach",5),
    seaocean("seaocean",33),
    skilift("skilift",45),

    generalsupplies("generalsupplies",47),
    cafebar("cafebar",73);

    AttractionCategoryCodes(String name , int id) {
    };


}

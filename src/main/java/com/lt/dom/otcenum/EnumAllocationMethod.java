package com.lt.dom.otcenum;

public enum EnumAllocationMethod {

    Proportional("Allocates costs across your targets based on the proportional weighted cost of each target."),

    Fixed("Allocates costs across your targets based on your defined allocation percentage."),

    Even("split - Allocates costs evenly across all targets."),
    ;


    EnumAllocationMethod(String desc) {

    }
}

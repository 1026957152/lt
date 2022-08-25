package com.lt.dom.otcenum;

public enum EnumOperation {

    $in("$in"),
    $not_in("$not_in"),
    $is("$is"),
    $is_not("$is_not"),
    $has_value("$has_value"),

    $is_unknown("$is_unknown"),
    $contains("$contains"),
    $starts_with("$starts_with"),
    $ends_with("$ends_with"),
    $more_than("$more_than"),

    $less_than("$less_than"),
    $more_than_equal("$more_than_equal"),
    $less_than_equal("$less_than_equal"),


    ;


    EnumOperation(String barcode) {

    }
}

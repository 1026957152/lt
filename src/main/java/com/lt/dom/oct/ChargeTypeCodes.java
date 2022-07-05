package com.lt.dom.oct;

public enum ChargeTypeCodes {

  //  CODE VALUE	CODE NAME	COMMENT
    Per_day( 1,	"Per day",	"Only parking service, internet service"),
    Per_hour(2,	"Per hour",	"Only parking service, internet service"),
    Per_minute(10,	"Per minute",	"Only internet service"),
    Per_stay(12,	"Per stay",	"All except internet service"),
    Per_week(17,	"Per week",	"Only parking service"),
    Per_night(19,	"Per night",	"All except parking service, internet service"),
    Per_person(20,	"Per person", "per stay	All except parking service, internet service"),
    Per_person_per_night(21, "Per person per night",	"All except parking service, internet service"),
    ;




    ChargeTypeCodes(int CODE, String NAME, String COMMENT) {

    }
}

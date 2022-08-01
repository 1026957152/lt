package com.lt.dom.otcenum;

public enum EnumContactProfileType {
    general("general",	"Primary point of contact for the property",	"Required"),
    contract("contract","Contact for contract matters",	"Optional"),
    reservations("reservations",	"Contact for reservations","Optional"),
    invoices("invoices",	"Contact for accounts payable","Required"),
    availability("availability","Contact for questions about availability",	"Optional"),

    site_content("site_content","Contact for photos, descriptions, and other website content",	"Optional"),
    parity("parity",	"Contact for pricing and rate matters",	"Optional"),
    requests("requests",	"Contact for special requests",	"Optional"),
    central_reservations("central_reservations","Contact for central reservations. Applies to properties that manage reservations from another location",	"Optional"),
    PhysicalLocation("PhysicalLocation",	"Address details for the property's physical location",	"Required"),
    ;


    EnumContactProfileType(String physicalLocation, String s, String required) {


    }
}

package com.lt.dom.otcenum;

public enum EnumCapabilities {

    access_venue_bookings_("access_venue_bookings_{venue_id}",	"Bookings",	"Read only permission for a single venue"),
    access_venue_group_bookings_("access_venue_group_bookings_{venue_group_id}",	"Bookings",	"Read only permission for all venues in the group"),
    confirm_venue_bookings_("confirm_venue_bookings_{venue_id}",	"Bookings",	"Restricted user permission for a single venue"),
    confirm_venue_group_bookings_("confirm_venue_group_bookings_{venue_group_id}",	"Bookings",	"Restricted user permission for all venues in the group"),
    manage_venue_bookings_("manage_venue_bookings_{venue_id}",	"Bookings",	"Manage bookings for a single venue"),
    manage_venue_group_bookings_("manage_venue_group_bookings_{venue_group_id}",	"Bookings",	"Manage bookings for all venues in the group (includes those with no venue"),
    manage_no_venue_bookings_("manage_no_venue_bookings_{venue_group_id}",	"Bookings",	"Manage bookings that have no venue against them"),
    manage_venue_refunds_("manage_venue_refunds_{venue_id}",	"Bookings",	"Manage refunds for a single venue"),
    manage_venue_group_refunds_("manage_venue_group_refunds_{venue_group_id}",	"Bookings",	"Manage refunds for all venues in the group"),

    view_venue_customers_("view_venue_customers_{venue_id}",	"Customers",	"View customer details for a single venue"),
    view_venue_group_customers_("view_venue_group_customers_{venue_group_id}",	"Customers",	"View customer details for all venues in the group"),
    download_venue_customers_ ("download_venue_customers_{venue_id}",	"Customers",	"Download customer details for a single venue"),
    download_venue_group_customers_ ("download_venue_group_customers_{venue_group_id}",	"Customers",	"Download customer details for all venues in the group"),

    view_venue_reports_("view_venue_reports_{venue_id}",	"Reports",	"View reports for a single venue"),
            view_venue_group_reports_("view_venue_group_reports_{venue_group_id}",	"Reports",	"View reports for all venues in the group"),
            download_venue_reports_("download_venue_reports_{venue_id}",	"Reports",	"Download reports for a single venue"),
    download_venue_group_reports_("download_venue_group_reports_{venue_group_id}",	"Reports",	"Download reports for all venues in the group"),

    manage_venue_booking_rules_("manage_venue_booking_rules_{venue_id}",	"Venues",	"Manage booking rules for a single venue"),
    manage_venue_group_booking_rules_("manage_venue_group_booking_rules_{venue_group_id}",	"Venues",	"Manage booking rules for all venues in the group"),
    manage_page_("manage_page_{venue_id}",	"Venues",	"Manage the DesignMyNight venue page for a single venue"),
    manage_venue_group_venues_("manage_venue_group_venues_{venue_group_id}",	"Venues",	"Manage the DesignMyNight venue page for all venues in the group"),

    manage_venue_permissions_("manage_venue_permissions_{venue_id}",	"Users",	"Manage user permissions for a single venue"),
    manage_venue_group_permissions_("manage_venue_group_permissions_{venue_group_id}",	"Users",	"Manage user permissions for all venues in the group"),
    ;

    EnumCapabilities(String s, String users, String s1) {

    }
}

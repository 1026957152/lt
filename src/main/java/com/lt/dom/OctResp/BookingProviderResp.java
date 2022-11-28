package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.BookingProvider;
import com.lt.dom.oct.BookingResource;
import com.lt.dom.oct.BookingService;
import com.lt.dom.otcReq.BookingServiceResp;

import javax.persistence.Entity;


//https://developer.makeplans.com/#slots

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingProviderResp  extends BaseResp {

    private BookingResourceResp resource;
    private BookingServiceResp service;



    private Boolean active;
    private MediaResp media;

    public static BookingProviderResp from(BookingProvider e) {
        BookingProviderResp bookingProviderResp = new BookingProviderResp();
        bookingProviderResp.setActive(e.getActive());
        bookingProviderResp.setCreatedDate(e.getCreatedDate());
        bookingProviderResp.setModifiedDate(e.getModifiedDate());
        return bookingProviderResp;
    }

    public static BookingProviderResp from(BookingProvider e, BookingResource bookingResource, BookingService bookingService) {
        BookingProviderResp bookingProviderResp = BookingProviderResp.from(e);

        bookingProviderResp.setResource(BookingResourceResp.from(bookingResource));
        bookingProviderResp.setService(BookingServiceResp.from(bookingService));
        return bookingProviderResp;
    }

    public static BookingProviderResp from(BookingProvider e, BookingResourceResp bookingResource, BookingService bookingService) {
        BookingProviderResp bookingProviderResp = BookingProviderResp.from(e);

        bookingProviderResp.setResource(bookingResource);
        bookingProviderResp.setService(BookingServiceResp.from(bookingService));
        return bookingProviderResp;
    }
    public BookingResourceResp getResource() {
        return resource;
    }

    public void setResource(BookingResourceResp resource) {
        this.resource = resource;
    }

    public BookingServiceResp getService() {
        return service;
    }

    public void setService(BookingServiceResp service) {
        this.service = service;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setMedia(MediaResp media) {
        this.media = media;
    }

    public MediaResp getMedia() {
        return media;
    }
}

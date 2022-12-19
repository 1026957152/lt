package com.lt.dom.proto;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.hateoas.EntityModel;

import java.util.List;


public class HomeBusProto {


    private List lines;

    public <T> void setLines(List lines) {
        this.lines = lines;
    }

    public List getLines() {
        return lines;
    }




}

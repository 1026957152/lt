package com.lt.dom.OctResp.home;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.MediaResp;
import com.lt.dom.OctResp.MuseumResp;

import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeBusResp {


    private List lines;
    private List<EntityModel> availableFares;



    public <T> void setLines(List lines) {
        this.lines = lines;
    }

    public List getLines() {
        return lines;
    }

    public void setAvailableFares(List<EntityModel> availableFares) {
        this.availableFares = availableFares;
    }

    public List<EntityModel> getAvailableFares() {
        return availableFares;
    }



}

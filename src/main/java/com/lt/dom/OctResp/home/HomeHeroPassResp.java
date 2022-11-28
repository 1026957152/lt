package com.lt.dom.OctResp.home;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.MediaResp;
import org.javatuples.Pair;
import org.springframework.hateoas.EntityModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeHeroPassResp {


    private String lable;
    private List lines;
    private boolean hasPass;
    private EntityModel defaultPass;



    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getLable() {
        return lable;
    }

    public <T> void setLines(List lines) {

        this.lines = lines;
    }

    public List getLines() {
        return lines;
    }

    public void setHasPass(boolean hasPass) {
        this.hasPass = hasPass;
    }

    public boolean isHasPass() {
        return hasPass;
    }

    public void setDefaultPass(EntityModel defaultPass) {
        this.defaultPass = defaultPass;
    }

    public EntityModel getDefaultPass() {
        return defaultPass;
    }
}

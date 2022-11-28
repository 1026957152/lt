package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumProvider;

import javax.persistence.*;

@Entity
public class Description extends Base{

    private String text;

    private String link;

    private EnumProvider provider;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public EnumProvider getProvider() {
        return provider;
    }

    public void setProvider(EnumProvider provider) {
        this.provider = provider;
    }
}

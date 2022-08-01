package com.lt.dom.otcReq;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CommentPojo {

    @Id
    private long id;
    private String name;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

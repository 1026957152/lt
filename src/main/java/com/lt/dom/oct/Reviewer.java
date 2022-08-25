package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumRequestType;
import com.lt.dom.otcenum.EnumReviewerType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Reviewer {

    private long request;

    private long reviewer;

    private EnumReviewerType type;

    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    public long getRequest() {
        return request;
    }

    public void setRequest(long request) {
        this.request = request;
    }

    public long getReviewer() {
        return reviewer;
    }

    public void setReviewer(long reviewer) {
        this.reviewer = reviewer;
    }

    public EnumReviewerType getType() {
        return type;
    }

    public void setType(EnumReviewerType type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

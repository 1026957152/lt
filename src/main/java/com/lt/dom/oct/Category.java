package com.lt.dom.oct;

import javax.persistence.*;

@Entity
public class Category {
    @Version
    private Integer version;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String category;
    private String code;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

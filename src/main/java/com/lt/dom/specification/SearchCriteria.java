package com.lt.dom.specification;

public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String lastName, String s, Object doe) {
        this.key = lastName;
        this.operation = s;
        this.value = doe;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
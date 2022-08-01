package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ScenarioAssignment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String note;

    private long scenario;
    private long supplier;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getScenario() {
        return scenario;
    }

    public void setScenario(long scenario) {
        this.scenario = scenario;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }
}

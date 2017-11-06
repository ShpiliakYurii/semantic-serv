package com.kpi.drproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TClass extends AbstractEntity {

    private String name;

    private String caption;

    @JsonIgnore
    @OneToMany(mappedBy = "tClass", cascade = CascadeType.ALL)
    private List<Thesis> theses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<Thesis> getTheses() {
        return theses;
    }

    public void setTheses(List<Thesis> theses) {
        this.theses = theses;
    }

    public TClass() {
    }

    public TClass(String name, String caption) {
        this.name = name;
        this.caption = caption;
    }
}


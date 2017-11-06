package com.kpi.drproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class CClass extends AbstractEntity {

    private String name;

    private String caption;

    @JsonIgnore
    @OneToMany(mappedBy = "cClass", cascade = CascadeType.ALL)
    private List<Concept> concepts;

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

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public CClass() {
    }

    public CClass(String name, String caption) {
        this.name = name;
        this.caption = caption;
    }
}


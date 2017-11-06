package com.kpi.drproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Thesis extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "concept")
    private Concept concept;

    @Lob
    private String thesis;

    @ManyToOne
    @JoinColumn(name = "tPage")
    private Page tPage;

    @ManyToOne
    @JoinColumn(name = "tClass")
    private TClass tClass;

    @JsonIgnore
    @OneToMany(mappedBy = "tThesis", cascade = CascadeType.ALL)
    private List<ConceptInThesis> conceptInTheses;

    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }


    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public TClass gettClass() {
        return tClass;
    }

    public void settClass(TClass tClass) {
        this.tClass = tClass;
    }

    public Thesis() {
    }

    public Thesis(String thesis) {
        this.thesis = thesis;
    }

    public Page gettPage() {
        return tPage;
    }

    public void settPage(Page tPage) {
        this.tPage = tPage;
    }

    public List<ConceptInThesis> getConceptInTheses() {
        return conceptInTheses;
    }

    public void setConceptInTheses(List<ConceptInThesis> conceptInTheses) {
        this.conceptInTheses = conceptInTheses;
    }
}

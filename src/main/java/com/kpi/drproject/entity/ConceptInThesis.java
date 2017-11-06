package com.kpi.drproject.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ConceptInThesis extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "tConcept")
    private Concept tConcept;

    @ManyToOne
    @JoinColumn(name = "tThesis")
    private Thesis tThesis;

    public ConceptInThesis() {
    }

    public Concept gettConcept() {
        return tConcept;
    }

    public void settConcept(Concept tConcept) {
        this.tConcept = tConcept;
    }

    public Thesis gettThesis() {
        return tThesis;
    }

    public void settThesis(Thesis tThesis) {
        this.tThesis = tThesis;
    }

    public ConceptInThesis(Concept tConcept, Thesis tThesis) {
        this.tConcept = tConcept;
        this.tThesis = tThesis;
    }
}

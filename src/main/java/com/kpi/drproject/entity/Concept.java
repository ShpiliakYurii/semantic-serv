package com.kpi.drproject.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Concept.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Concept extends AbstractEntity {

    private String concept;

    @ManyToOne
    @JoinColumn(name = "cPage")
    private Page cPage;

    @ManyToOne
    @JoinColumn(name = "cClass")
    private CClass cClass;

    private String forms;

    @OneToMany(mappedBy = "concept", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Thesis> theses;

    @JsonIgnore
    @OneToMany(mappedBy = "tConcept", cascade = CascadeType.ALL)
    private List<ConceptInThesis> conceptInTheses;

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Page getcPage() {
        return cPage;
    }

    public void setcPage(Page cPage) {
        this.cPage = cPage;
    }

    public CClass getcClass() {
        return cClass;
    }

    public void setcClass(CClass cClass) {
        this.cClass = cClass;
    }

    public String getForms() {
        return forms;
    }

    public void setForms(String forms) {
        this.forms = forms;
    }

    public List<Thesis> getTheses() {
        return theses;
    }

    public void setTheses(List<Thesis> theses) {
        this.theses = theses;
    }

    public Concept() {
    }

    public Concept(String concept, String forms) {
        this.concept = concept;
        this.forms = forms;
    }

    public List<ConceptInThesis> getConceptInTheses() {
        return conceptInTheses;
    }

    public void setConceptInTheses(List<ConceptInThesis> conceptInTheses) {
        this.conceptInTheses = conceptInTheses;
    }

    @Override
    public String toString() {
        return "Concept{" +
                "concept='" + concept + '\'' +
                ", cPage=" + cPage +
                ", cClass=" + cClass +
                ", forms='" + forms + '\'' +
                ", theses=" + theses +
                ", conceptInTheses=" + conceptInTheses +
                '}';
    }
}

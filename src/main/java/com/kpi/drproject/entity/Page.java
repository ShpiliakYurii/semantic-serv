package com.kpi.drproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Page extends AbstractEntity {

    @Lob
    private String nativeUrl;

    @Lob
    private String title;

    @ManyToOne
    @JoinColumn(name = "origin")
    private Origin origin;


    @JsonIgnore
    @OneToMany(mappedBy = "cPage", cascade = CascadeType.ALL)
    private List<Concept> concepts;

    @JsonIgnore
    @OneToMany(mappedBy = "tPage", cascade = CascadeType.ALL)
    private List<Thesis> theses;

    public Page() {
    }

    public Page(String nativeUrl) {
        this.nativeUrl = nativeUrl;
    }

    public String getNativeUrl() {
        return nativeUrl;
    }

    public void setNativeUrl(String nativeUrl) {
        this.nativeUrl = nativeUrl;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public List<Thesis> getTheses() {
        return theses;
    }

    public void setTheses(List<Thesis> theses) {
        this.theses = theses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Page{" +
                "nativeUrl='" + nativeUrl + '\'' +
                ", title='" + title + '\'' +
                ", origin=" + origin +
                ", id=" + getId()+
                '}';
    }
}

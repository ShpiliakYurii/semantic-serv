package com.kpi.drproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Origin extends AbstractEntity {

    private String origin;

    @JsonIgnore
    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL)
    private List<Page> pages;
    public Origin() {
    }

    public Origin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}

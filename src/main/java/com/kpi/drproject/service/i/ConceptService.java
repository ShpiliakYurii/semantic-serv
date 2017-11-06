package com.kpi.drproject.service.i;

import com.kpi.drproject.entity.Concept;

import java.util.List;

public interface ConceptService extends AbstractService<Concept> {

    List<Concept> findAllByPage(long id);
    Concept addNewConcept(Concept concept);
    List<Concept> findAllOrderByConcept();
    List<Concept> findAllByOrigin(long id);
    List<Concept> findByConcept(String str);
}

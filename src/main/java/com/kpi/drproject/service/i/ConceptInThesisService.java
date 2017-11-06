package com.kpi.drproject.service.i;

import com.kpi.drproject.entity.Concept;
import com.kpi.drproject.entity.ConceptInThesis;

import java.util.List;
import java.util.Map;

public interface ConceptInThesisService extends AbstractService<ConceptInThesis> {
    List<ConceptInThesis> findAllRelatedConcepts(Concept concept);

    void updateAllRelatedConcepts(List<Concept> concepts);

    void updateRelatedConcepts(Concept concept);

    Map<String, Object> getConceptsForWidget(List<Concept> concepts);

    Map<String, Object> getRelatedConceptsForWidget(Concept concept);
}

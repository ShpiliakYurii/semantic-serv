package com.kpi.drproject.repository;

import com.kpi.drproject.entity.Concept;
import com.kpi.drproject.entity.ConceptInThesis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConceptInThesisRepository extends AbstractRepository<ConceptInThesis, Long> {

    @Query("select cInT from ConceptInThesis cInT where cInT.tConcept = ?1")
    List<ConceptInThesis> findAllRelatedConcepts(Concept concept);

    //    @Query("select cInT from ConceptInThesis cInT where cInT.tConcept = ?1")
    @Query("SELECT distinct cInT FROM ConceptInThesis cInT where (cInT.tConcept = ?1) or cInT.tThesis in (select t.id from Thesis t where t.concept = ?1)")
    List<ConceptInThesis> findByConceptId(Concept concept);
}

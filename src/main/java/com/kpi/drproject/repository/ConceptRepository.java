package com.kpi.drproject.repository;

import com.kpi.drproject.entity.Concept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConceptRepository extends AbstractRepository<Concept, Long> {

    @Query(value = "select c from Concept c where c.cPage.origin.id = (select p.origin.id from Page p where p.id = ?1)")
    List<Concept> findAllByPage(long id);

    @Query(value = "select c from Concept c where c.cPage.origin.id = ?1")
    List<Concept> findAllByOrigin(long id);

    @Query(value = "select c from Concept c order by c.concept")
    List<Concept> findAllOrderByConcept();

    @Query(value = "select c from Concept c where lower(c.concept) like CONCAT('%',:str,'%')")
    List<Concept> findByConcept(@Param("str") String str);
}

package com.kpi.drproject.service.impl;

import com.kpi.drproject.entity.Concept;
import com.kpi.drproject.entity.ConceptInThesis;
import com.kpi.drproject.entity.Thesis;
import com.kpi.drproject.repository.ConceptRepository;
import com.kpi.drproject.service.i.ConceptInThesisService;
import com.kpi.drproject.service.i.ConceptService;
import com.kpi.drproject.service.i.ThesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ConceptServiceImpl extends AbstractServiceImpl<Concept> implements ConceptService {

    private ConceptRepository conceptRepository;
    private ConceptInThesisService conceptInThesisService;

    @Autowired
    public ConceptServiceImpl(ConceptRepository conceptRepository, ConceptInThesisService conceptInThesisService) {
        super(conceptRepository);
        this.conceptRepository = conceptRepository;
        this.conceptInThesisService = conceptInThesisService;
    }

    @Override
    public List<Concept> findAllByPage(long id) {
        return this.conceptRepository.findAllByPage(id);
    }

    @Override
    public Concept addNewConcept(Concept concept) {
        this.conceptRepository.save(concept);
        this.conceptInThesisService.updateRelatedConcepts(concept);
        return concept;
    }

    @Override
    public void update(Concept entity) {
        this.conceptRepository.save(entity);
        this.conceptInThesisService.updateRelatedConcepts(entity);
    }

    @Override
    public List<Concept> findAllOrderByConcept() {
        return this.conceptRepository.findAllOrderByConcept();
    }

    @Override
    public List<Concept> findAllByOrigin(long id) {
        return this.conceptRepository.findAllByOrigin(id);
    }

    @Override
    public List<Concept> findByConcept(String str) {
        return this.conceptRepository.findByConcept(str.toLowerCase());
    }

}

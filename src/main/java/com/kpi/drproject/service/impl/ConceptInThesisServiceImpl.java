package com.kpi.drproject.service.impl;

import com.kpi.drproject.entity.Concept;
import com.kpi.drproject.entity.ConceptInThesis;
import com.kpi.drproject.entity.Thesis;
import com.kpi.drproject.repository.ConceptInThesisRepository;
import com.kpi.drproject.service.i.ConceptInThesisService;
import com.kpi.drproject.service.i.ConceptService;
import com.kpi.drproject.service.i.ThesisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConceptInThesisServiceImpl extends AbstractServiceImpl<ConceptInThesis> implements ConceptInThesisService {

    private ConceptInThesisRepository conceptInThesisRepository;
    private ThesisService thesisService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConceptInThesisServiceImpl.class);

    @Autowired
    public ConceptInThesisServiceImpl(ConceptInThesisRepository conceptInThesisRepository,
                                      ThesisService thesisService) {
        super(conceptInThesisRepository);
        this.thesisService = thesisService;
        this.conceptInThesisRepository = conceptInThesisRepository;
    }

    public List<ConceptInThesis> findAllRelatedConcepts(Concept concept) {
        return this.conceptInThesisRepository.findAllRelatedConcepts(concept);
    }

    @Override
    public void updateAllRelatedConcepts(List<Concept> concepts) {
        concepts.forEach(this::updateRelatedConcepts);
    }

    @Override
    public void updateRelatedConcepts(Concept concept) {
        List<ConceptInThesis> currentlyRelatedConcepts = this.findAllRelatedConcepts(concept);
        List<Thesis> allRelatedTheses = this.thesisService.findByWord(concept.getConcept());
        Arrays.stream(concept.getForms().split(";")).forEach(s -> {
            LOGGER.debug("form: {}", s);
//            if (s.contains(";"))
//                allRelatedTheses.addAll(this.thesisService.findByWord(s.split(";")[0]));
//            else
                allRelatedTheses.addAll(this.thesisService.findByWord(s));
        });
        allRelatedTheses.forEach(thesis -> {
            ConceptInThesis cInT = currentlyRelatedConcepts.stream().filter(conceptInThesis ->
                    Objects.equals(conceptInThesis.gettThesis().getId(), thesis.getId())).findFirst().orElse(null);
            if (cInT == null && !concept.getId().equals(thesis.getConcept().getId())) {
                cInT = new ConceptInThesis(concept, thesis);
                this.add(cInT);
                currentlyRelatedConcepts.add(cInT);
            }
        });
    }

    @Override
    public Map<String, Object> getConceptsForWidget(List<Concept> concepts) {
        Map<String, Object> result = new HashMap<>();
        List<Object> edges = new ArrayList<>();
        List<Object> nodes = new ArrayList<>();
        concepts.forEach(concept -> {
            List<ConceptInThesis> conceptInTheses = this.conceptInThesisRepository.findByConceptId(concept);
            nodes.add(initNode(concept, conceptInTheses.size() + 100));
            conceptInTheses.forEach(conceptInThesis -> edges.add(initEdge(conceptInThesis)));
        });
        result.put("edges", edges);
        result.put("nodes", nodes);
        return result;
    }

    @Override
    public Map<String, Object> getRelatedConceptsForWidget(Concept concept) {
        LOGGER.debug("concept: {}", concept);
        Map<String, Object> result = new HashMap<>();
        List<Object> edges = new ArrayList<>();
        List<ConceptInThesis> conceptInTheses = this.conceptInThesisRepository.findByConceptId(concept);
        List<Object> nodes = new ArrayList<>();
        result.put("concept", concept);
        nodes.add(initNode(concept, 10));
        conceptInTheses.forEach(conceptInThesis -> {
            if (conceptInThesis.gettConcept().getId().equals(concept.getId())) {
                nodes.add(initNode(conceptInThesis.gettThesis().getConcept(), 5));
            } else {
                nodes.add(initNode(conceptInThesis.gettConcept(), 5));
            }
        });
        conceptInTheses.forEach(conceptInThesis -> edges.add(initEdge(conceptInThesis)));
        result.put("edges", edges);
        result.put("nodes", nodes);
        return result;
    }

    private Map<String, Object> initNode(Concept concept, long value) {
        Map<String, Object> node = new HashMap<>();
        node.put("id", concept.getId());
        node.put("label", concept.getConcept());
        node.put("title", concept.getConcept() +
                "<br>" + concept.getcPage().getNativeUrl());
        node.put("group", concept.getcPage().getOrigin().getOrigin());
        node.put("value", value);
        return node;
    }

    private Map<String, Object> initEdge(ConceptInThesis conceptInThesis) {
        Map<String, Object> edge = new HashMap<>();
        edge.put("from", conceptInThesis.gettConcept().getId());
        edge.put("to", conceptInThesis.gettThesis().getConcept().getId());
        return edge;
    }
}

package com.kpi.drproject.controller.rest;

import com.kpi.drproject.entity.Concept;
import com.kpi.drproject.entity.Origin;
import com.kpi.drproject.entity.Page;
import com.kpi.drproject.service.i.ConceptService;
import com.kpi.drproject.service.i.OriginService;
import com.kpi.drproject.service.i.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping(value = "/api/concept")
public class ConceptController extends AbstractEntityController<Concept> {

    private Logger logger = LoggerFactory.getLogger(ConceptController.class);
    private ConceptService conceptService;
    private PageService pageService;
    private OriginService originService;

    @Autowired
    public ConceptController(ConceptService conceptService, PageService pageService,
                             OriginService originService) {
        super(conceptService);
        this.conceptService = conceptService;
        this.pageService = pageService;
        this.originService = originService;
    }

    @GetMapping(value = "/page/{id}")
    public ResponseEntity<?> getConceptsByPage(@PathVariable("id") long id) {
        return new ResponseEntity<Object>(conceptService.findAllByPage(id), HttpStatus.OK);
    }

    @GetMapping(value = "/origin/{id}")
    public ResponseEntity<?> getConceptsByOrigin(@PathVariable("id") long id) {
        return new ResponseEntity<Object>(conceptService.findAllByOrigin(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Concept concept) {
        logger.debug("New concept: {}", concept);
        if (concept.getcPage().getId() == null) {
            concept.getcPage().setOrigin(originService.addNewOrigin(concept.getcPage().getNativeUrl()));
            this.pageService.add(concept.getcPage());
        }
        this.conceptService.addNewConcept(concept);
        return new ResponseEntity<Object>(concept, HttpStatus.CREATED);
    }

    @GetMapping(value = "/ordered")
    public ResponseEntity<?> findAllOrderByConcept() {
        return new ResponseEntity<Object>(this.conceptService.findAllOrderByConcept(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByStr(@RequestParam("str") String str){
        return new ResponseEntity<Object>(this.conceptService.findByConcept(str), HttpStatus.OK);
    }
}

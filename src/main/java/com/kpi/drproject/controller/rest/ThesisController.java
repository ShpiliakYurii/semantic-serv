package com.kpi.drproject.controller.rest;

import com.kpi.drproject.entity.Thesis;
import com.kpi.drproject.service.i.ConceptInThesisService;
import com.kpi.drproject.service.i.ConceptService;
import com.kpi.drproject.service.i.ThesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/thesis")
public class ThesisController extends AbstractEntityController<Thesis> {

    private ThesisService thesisService;
    private ConceptService conceptService;
    private ConceptInThesisService conceptInThesisService;

    @Autowired
    public ThesisController(ThesisService thesisService, ConceptService conceptService,
                            ConceptInThesisService conceptInThesisService) {
        super(thesisService);
        this.thesisService = thesisService;
        this.conceptService = conceptService;
        this.conceptInThesisService = conceptInThesisService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") long id) {
        this.thesisService.delete(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @PostMapping
    @Override
    public ResponseEntity<?> create(@RequestBody Thesis entity) {
        this.thesisService.add(entity);
        this.conceptInThesisService.updateAllRelatedConcepts(conceptService.getAll());
        return new ResponseEntity<Object>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    @Override
    public ResponseEntity<?> update(@RequestBody Thesis entity) {
        this.thesisService.update(entity);
        this.conceptInThesisService.updateAllRelatedConcepts(conceptService.getAll());
        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

}

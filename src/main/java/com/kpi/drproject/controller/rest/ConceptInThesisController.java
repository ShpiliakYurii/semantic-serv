package com.kpi.drproject.controller.rest;

import com.kpi.drproject.entity.ConceptInThesis;
import com.kpi.drproject.service.i.ConceptInThesisService;
import com.kpi.drproject.service.i.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/concept-in-thesis")
public class ConceptInThesisController extends AbstractEntityController<ConceptInThesis> {

    private ConceptInThesisService conceptInThesisService;
    private ConceptService conceptService;

    @Autowired
    public ConceptInThesisController(ConceptInThesisService conceptInThesisService, ConceptService conceptService) {
        super(conceptInThesisService);
        this.conceptInThesisService = conceptInThesisService;
        this.conceptService = conceptService;
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<Object>(conceptInThesisService.getConceptsForWidget(conceptService.getAll()), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        return new ResponseEntity<Object>(conceptInThesisService.getRelatedConceptsForWidget(conceptService.get(id)),
//        return new ResponseEntity<Object>(conceptService.get(id),
                HttpStatus.OK);
    }
}

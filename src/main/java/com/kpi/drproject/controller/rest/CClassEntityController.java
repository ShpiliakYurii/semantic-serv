package com.kpi.drproject.controller.rest;

import com.kpi.drproject.service.i.CClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/c-class")
public class CClassEntityController extends AbstractEntityController {

    private CClassService cClassService;

    @Autowired
    public CClassEntityController(CClassService cClassService) {
        super(cClassService);
        this.cClassService = cClassService;
    }

//    @GetMapping
//    public ResponseEntity<?> getCClasses() {
//        return new ResponseEntity<Object>(this.cClassService.getAll(), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCClass(@PathVariable("id") long id) {
//        return new ResponseEntity<Object>(this.cClassService.get(id), HttpStatus.OK);
//    }

}

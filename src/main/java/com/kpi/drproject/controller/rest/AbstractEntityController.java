package com.kpi.drproject.controller.rest;

import com.kpi.drproject.entity.AbstractEntity;
import com.kpi.drproject.service.i.AbstractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://rd-client-side.com")
public abstract class AbstractEntityController<T extends AbstractEntity> {

    private AbstractService<T> service;

    AbstractEntityController(AbstractService<T> service) {
        super();
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<Object>(this.service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        return new ResponseEntity<Object>(this.service.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody T entity) {
        this.service.add(entity);
        return new ResponseEntity<Object>(entity, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody T entity) {
        this.service.update(entity);
        return new ResponseEntity<Object>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") long id) {
        this.service.remove(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}







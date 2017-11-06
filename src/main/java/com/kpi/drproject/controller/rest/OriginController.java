package com.kpi.drproject.controller.rest;

import com.kpi.drproject.entity.Origin;
import com.kpi.drproject.service.i.OriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/origin")
public class OriginController extends AbstractEntityController<Origin> {

    private OriginService originService;

    @Autowired
    OriginController(OriginService originService) {
        super(originService);
        this.originService = originService;
    }


    @Override
    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<Object>(this.originService.findAllOrderById(), HttpStatus.OK);
    }
}

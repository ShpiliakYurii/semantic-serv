package com.kpi.drproject.controller.rest;

import com.kpi.drproject.service.i.TClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/t-class")
public class TClassEntityController extends AbstractEntityController {

    private TClassService tClassService;

    @Autowired
    public TClassEntityController(TClassService tClassService) {
        super(tClassService);
        this.tClassService = tClassService;
    }

}

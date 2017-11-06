package com.kpi.drproject.controller.rest;

import com.kpi.drproject.entity.Page;
import com.kpi.drproject.service.i.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/wiki-page")
public class PageController extends AbstractEntityController<Page> {

    private PageService wikiPageService;

    @Autowired
    public PageController(PageService wikiPageService) {
        super(wikiPageService);
        this.wikiPageService = wikiPageService;
    }
}

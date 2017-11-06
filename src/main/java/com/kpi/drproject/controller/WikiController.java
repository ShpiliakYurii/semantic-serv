package com.kpi.drproject.controller;

import com.kpi.drproject.consts.ProjectConstants;
import com.kpi.drproject.entity.Page;
import com.kpi.drproject.service.i.OriginService;
import com.kpi.drproject.service.i.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WikiController {

    private PageService pageService;
    private OriginService originService;

    @Autowired
    public WikiController(PageService pageService,
                          OriginService originService) {
        this.pageService = pageService;
        this.originService = originService;
    }

    @RequestMapping(value = "/wiki", method = RequestMethod.GET)
    public @ResponseBody
    Map getWikiPage(@RequestParam("s") String search) {
        String url = "https://uk.wikipedia.org/wiki/" + search;
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("page", pageService.getAndSavePageByUrl(new Page(url),
                    ProjectConstants.WIKI_TEMP_FILE_URL, search));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getWikiPage(@RequestParam("id") long id) {
        try {
            pageService.getAndSavePageByUrl(id, ProjectConstants.WIKI_TEMP_FILE_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "wiki-page.html";
    }

    @RequestMapping(value = "/p", method = RequestMethod.GET)
    public @ResponseBody
    Map getPage(@RequestParam("url") String url) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            Page page = pageService.getPageByUrl(url, originService.getOriginByUrl(new URL(url)));
            response.put("page", pageService.getAndSavePageByUrl(page,
                    ProjectConstants.WIKI_TEMP_FILE_URL, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}

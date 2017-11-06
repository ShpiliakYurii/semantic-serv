package com.kpi.drproject.service.impl;

import com.kpi.drproject.consts.ProjectConstants;
import com.kpi.drproject.entity.Origin;
import com.kpi.drproject.entity.Page;
import com.kpi.drproject.repository.OriginRepository;
import com.kpi.drproject.repository.PageRepository;
import com.kpi.drproject.service.i.OriginService;
import com.kpi.drproject.service.i.PageService;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

@Service
public class PageServiceImpl extends AbstractServiceImpl<Page> implements PageService {

    private static Logger logger = LoggerFactory.getLogger(PageServiceImpl.class);
    private PageRepository pageRepository;
    private OriginService originService;

    @Autowired
    public PageServiceImpl(PageRepository pageRepository,
                           OriginService originService) {
        super(pageRepository);
        this.pageRepository = pageRepository;
        this.originService = originService;
    }

    public void getAndSavePageByUrl(long pageId, String fileToSave) throws IOException {
        Page page = pageRepository.getOne(pageId);
        getAndSavePageByUrl(page, fileToSave, null);
    }


    /**
     * Get wiki page by url ang save it to temp .html file
     *
     * @param fileToSave file url
     * @throws IOException
     */
    public Page getAndSavePageByUrl(Page inputPage, String fileToSave, String search) throws IOException {
        if (!inputPage.getNativeUrl().contains("http"))
            inputPage.setNativeUrl(inputPage.getOrigin().getOrigin() + inputPage.getNativeUrl());
        URL url1 = new URL(inputPage.getNativeUrl());
        String originStr = url1.getProtocol() + "://" + url1.getHost();
        Origin origin = originService.getOriginByUrl(url1);
        inputPage.setOrigin(origin);
        if (!inputPage.getNativeUrl().contains("http"))
            inputPage.setNativeUrl(inputPage.getOrigin().getOrigin() + inputPage.getNativeUrl());
        Document doc;
        logger.debug("Input page: {}", inputPage);
        try {
            doc = Jsoup.connect(inputPage.getNativeUrl()).get();
        } catch (HttpStatusException e) {
            if (search != null) {
                inputPage.setNativeUrl("https://uk.wikipedia.org/w/index.php?search=" + URLEncoder.encode(search, "UTF-8") +
                        "&title=%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:%D0%9F%D0%BE%D0%B8%D1%81%D0%BA&go=%D0%9F%D0%B5%D1%80%D0%B5%D0%B9%D1%82%D0%B8&searchToken=6h3ffu3v634sh4vvjxoi41sxn");
                doc = Jsoup.connect(inputPage.getNativeUrl()).get();
            } else
                return null;
        }
        Elements title = doc.getElementsByTag("title");
        inputPage.setTitle(title.first().text());

        pageRepository.save(inputPage);
        Elements elements = doc.getElementsByTag("a");

        elements.forEach(element -> {
            String href = element.attr("href");
            Page page = getPageByUrl(href, origin);
            element.attr("href", "http://localhost:8090/page?id=" + page.getId());
        });
        doc.getElementsByTag("link").forEach(element -> {
            if (!element.attr("href").contains("http")) {
                if (element.attr("href").charAt(0) == '/')
                    element.attr("href", originStr + element.attr("href"));
                else
                    element.attr("href", originStr + "/" + element.attr("href"));
            }
        });
        doc.getElementsByTag("script").forEach(element -> {
            if (element.hasAttr("src")) {
                if (!element.attr("src").contains("http")) {
                    if (element.attr("src").charAt(0) == '/')
                        element.attr("src", originStr + element.attr("src"));
                    else
                        element.attr("src", originStr + "/" + element.attr("src"));
                }
            }
        });
        doc.getElementsByTag("img").forEach(element -> {
            if (element.hasAttr("src") && !element.attr("src").contains("upload")) {
                if (!element.attr("src").contains("http")) {
                    if (element.attr("src").charAt(0) == '/')
                        element.attr("src", originStr + element.attr("src"));
                    else
                        element.attr("src", originStr + "/" + element.attr("src"));
                }
            }
        });
        File file = new ClassPathResource(fileToSave).getFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(doc.toString());
        writer.write(ProjectConstants.POST_MESSAGE_FRAME_SCRIPT);
        writer.close();
        return inputPage;
    }

    public List<Page> findByNativeUrl(String url) {
        return this.pageRepository.findByNativeUrl(url);
    }

    @Cacheable("getPageByUrl")
    public Page getPageByUrl(String url, Origin origin) {
        List<Page> pages = pageRepository.findByNativeUrl(url);
        Page page;
        if (pages == null || pages.size() == 0) {
            page = new Page(url);
            page.setOrigin(origin);
            pageRepository.save(page);
        } else {
            page = pages.get(0);
        }
        return page;
    }


}

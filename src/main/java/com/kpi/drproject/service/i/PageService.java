package com.kpi.drproject.service.i;

import com.kpi.drproject.entity.Origin;
import com.kpi.drproject.entity.Page;

import java.io.IOException;
import java.util.List;

public interface PageService extends AbstractService<Page> {
    void getAndSavePageByUrl(long pageId, String fileToSave) throws IOException;

    Page getAndSavePageByUrl(Page url, String fileToSave, String search) throws IOException;

    List<Page> findByNativeUrl(String url);

    Page getPageByUrl(String url, Origin origin);
}

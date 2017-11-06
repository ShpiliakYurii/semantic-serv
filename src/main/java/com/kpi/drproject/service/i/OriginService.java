package com.kpi.drproject.service.i;

import com.kpi.drproject.entity.Origin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public interface OriginService extends AbstractService<Origin> {
    Origin findByOrigin(String origin);

    Origin addNewOrigin(String url);

    Origin getOriginByUrl(URL url) throws MalformedURLException;

    List<Origin> findAllOrderById();
}

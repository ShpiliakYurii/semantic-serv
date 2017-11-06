package com.kpi.drproject.service.impl;

import com.kpi.drproject.entity.Origin;
import com.kpi.drproject.repository.OriginRepository;
import com.kpi.drproject.service.i.OriginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class OriginServiceImpl extends AbstractServiceImpl<Origin> implements OriginService {

    private static Logger logger = LoggerFactory.getLogger(OriginServiceImpl.class);
    private OriginRepository originRepository;

    @Autowired
    public OriginServiceImpl(OriginRepository originRepository) {
        super(originRepository);
        this.originRepository = originRepository;
    }

    public Origin findByOrigin(String origin) {
        return this.originRepository.findByOrigin(origin);
    }

    public Origin addNewOrigin(String url) {
        Origin origin = null;
        try {
            URL url1 = new URL(url);
            String originUrl = url1.getProtocol() + "://" + url1.getHost();
            logger.debug("Origin url: {}", originUrl);
            origin = this.findByOrigin(originUrl);
            if (origin == null) {
                origin = new Origin(originUrl);
                this.add(origin);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return origin;
    }

    public Origin getOriginByUrl(URL url1) throws MalformedURLException {
        String originStr = url1.getProtocol() + "://" + url1.getHost();
        Origin origin = originRepository.findByOrigin(originStr);
        if (origin == null) {
            origin = new Origin(originStr);
            originRepository.save(origin);
        }
        return origin;
    }


    @Override
    public List<Origin> findAllOrderById() {
        return this.originRepository.findAllOrderById();
    }
}

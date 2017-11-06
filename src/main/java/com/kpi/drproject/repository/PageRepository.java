package com.kpi.drproject.repository;

import com.kpi.drproject.entity.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends AbstractRepository<Page, Long> {

    List<Page> findByNativeUrl(String url);
}

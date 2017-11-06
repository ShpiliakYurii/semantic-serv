package com.kpi.drproject.service.i;

import com.kpi.drproject.entity.Thesis;

import java.util.List;

public interface ThesisService extends AbstractService<Thesis> {
    List<Thesis> findByWord(String word);

    void delete(long id);
}

package com.kpi.drproject.service.impl;

import com.kpi.drproject.entity.TClass;
import com.kpi.drproject.repository.TClassRepository;
import com.kpi.drproject.service.i.TClassService;
import org.springframework.stereotype.Service;

@Service
public class TClassServiceImpl extends AbstractServiceImpl<TClass> implements TClassService {

    private TClassRepository tClassRepository;

    public TClassServiceImpl(TClassRepository tClassRepository) {
        super(tClassRepository);
        this.tClassRepository = tClassRepository;
    }
}

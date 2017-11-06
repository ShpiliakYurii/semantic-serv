package com.kpi.drproject.service.impl;

import com.kpi.drproject.entity.CClass;
import com.kpi.drproject.repository.CClassRepository;
import com.kpi.drproject.service.i.CClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CClassServiceImpl extends AbstractServiceImpl<CClass> implements CClassService{

    private CClassRepository cClassRepository;

    @Autowired
    public CClassServiceImpl(CClassRepository cClassRepository) {
        super(cClassRepository);
        this.cClassRepository = cClassRepository;
    }
}

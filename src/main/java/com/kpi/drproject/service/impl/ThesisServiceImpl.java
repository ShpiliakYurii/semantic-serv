package com.kpi.drproject.service.impl;

import com.kpi.drproject.entity.Thesis;
import com.kpi.drproject.repository.ThesisRepository;
import com.kpi.drproject.service.i.ThesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThesisServiceImpl extends AbstractServiceImpl<Thesis> implements ThesisService {

    private ThesisRepository thesisRepository;

    @Autowired
    public ThesisServiceImpl(ThesisRepository thesisRepository) {
        super(thesisRepository);
        this.thesisRepository = thesisRepository;
    }

    @Override
    public List<Thesis> findByWord(String word){
        return this.thesisRepository.findByWord(word.toLowerCase());
    }

    public void delete(long id){
        this.thesisRepository.delete(id);
    }
}

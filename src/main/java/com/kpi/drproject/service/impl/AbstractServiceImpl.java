package com.kpi.drproject.service.impl;

import com.kpi.drproject.entity.AbstractEntity;
import com.kpi.drproject.repository.AbstractRepository;
import com.kpi.drproject.service.i.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public abstract class AbstractServiceImpl<T extends AbstractEntity> implements AbstractService<T> {


    private AbstractRepository<T, Long> repository;

    public AbstractServiceImpl(AbstractRepository<T, Long> repository) {
        super();
        this.repository = repository;
    }

    @Override
    @Transactional
    public void add(T entity) {
        repository.save(entity);
    }

    @Override
    @Transactional
    public void addAll(Collection<T> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void update(T entity) {
        repository.save(entity);
    }

    @Override
    @Transactional
    public T get(Long id) {
        return repository.getOne(id);
    }

    @Override
    @Transactional
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void remove(T entity) {
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void remove(Collection<T> entities) {
        repository.deleteAll(entities);
    }

    @Override
    @Transactional
    public void removeAll() {
        repository.deleteAll();
    }

    @Override
    @Transactional
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public long getAllCount(){return repository.count();};

}

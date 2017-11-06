package com.kpi.drproject.repository;

import com.kpi.drproject.entity.AbstractEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstractRepository<T extends AbstractEntity, ID extends Number> extends JpaRepository<T, ID> {
}

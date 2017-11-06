package com.kpi.drproject.repository;

import com.kpi.drproject.entity.Thesis;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ThesisRepository extends AbstractRepository<Thesis, Long> {

    @Query("select t from Thesis t where lower(t.thesis) like CONCAT('%',:str,'%')")
    List<Thesis> findByWord(@Param("str") String str);

    @Transactional
    @Modifying
    @Query("delete from Thesis t where t.id = ?1")
    void delete(long id);
}

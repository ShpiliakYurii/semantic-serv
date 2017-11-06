package com.kpi.drproject.repository;

import com.kpi.drproject.entity.Origin;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OriginRepository extends AbstractRepository<Origin, Long> {
    @Cacheable("originByUrl")
    @Query("select o from Origin o where origin like CONCAT('%',:origin,'%')")
    Origin findByOrigin(@Param("origin") String origin);

    @Query("select o from Origin o order by id asc")
    List<Origin> findAllOrderById();
}

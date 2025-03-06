package com.fgr.apirest.repository;

import com.fgr.apirest.entity.EvaluacionItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionItemRepository extends JpaRepository<EvaluacionItem, Integer> {
    Optional<EvaluacionItem> findById(Integer id);
}

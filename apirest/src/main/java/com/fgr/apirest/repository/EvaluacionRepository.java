package com.fgr.apirest.repository;

import com.fgr.apirest.entity.Evaluacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {
    Optional<Evaluacion> findTopByOrderByIdEvaluacionDesc();  
}

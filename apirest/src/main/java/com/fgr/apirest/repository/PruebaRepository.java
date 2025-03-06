package com.fgr.apirest.repository;

import com.fgr.apirest.entity.Prueba;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PruebaRepository extends JpaRepository<Prueba, Integer> {
    Optional<Prueba> findById(Integer id);
    Optional<Prueba> findByEspecialidad_IdEspecialidad(Integer especialidadId);
    Optional<Prueba> findTopByOrderByIdPruebaDesc();


}

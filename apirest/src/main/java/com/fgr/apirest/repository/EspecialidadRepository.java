package com.fgr.apirest.repository;

import com.fgr.apirest.entity.Especialidad;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {
    Optional<Especialidad> findById(Integer id);
    Optional<Especialidad> findByNombre(String nombre);
}

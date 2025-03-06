package com.fgr.apirest.repository;

import com.fgr.apirest.entity.Participante;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {
    Optional<Participante> findById(Integer id);
    List<Participante> findByEspecialidad_IdEspecialidad(Integer especialidadId);
}

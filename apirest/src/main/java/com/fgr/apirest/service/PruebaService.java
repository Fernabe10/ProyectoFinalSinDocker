package com.fgr.apirest.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fgr.apirest.dto.EspecialidadDTO;
import com.fgr.apirest.dto.PruebaDTO;
import com.fgr.apirest.entity.Especialidad;
import com.fgr.apirest.entity.Prueba;
import com.fgr.apirest.repository.EspecialidadRepository;
import com.fgr.apirest.repository.PruebaRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PruebaService {

    private final PruebaRepository repository;
    private final EspecialidadRepository especialidadRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public PruebaService(PruebaRepository repository, EspecialidadRepository especialidadRepository){
        this.repository = repository;
        this.especialidadRepository = especialidadRepository;
    }

    //traer todas las pruebas

    public List<PruebaDTO> findAll(){
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //traer una prueba por id

    public Optional<PruebaDTO> findById(Integer id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    //insertar una prueba

    public PruebaDTO save(PruebaDTO pruebaDTO) {
        Prueba prueba = new Prueba();
        prueba.setEnunciado(pruebaDTO.getEnunciado());
        prueba.setPuntuacionMaxima(pruebaDTO.getPuntuacionMaxima());

        if (pruebaDTO.getEspecialidad() != null && pruebaDTO.getEspecialidad().getIdEspecialidad() != null) {
            Especialidad especialidad = especialidadRepository.findById(pruebaDTO.getEspecialidad().getIdEspecialidad())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
            prueba.setEspecialidad(especialidad);
        } else {
            prueba.setEspecialidad(null);
        }

        return convertToDTO(repository.save(prueba));
    }

    //actualizar una prueba

    public Optional<PruebaDTO> update(Integer id, PruebaDTO pruebaDTO) {
        return repository.findById(id).map(existingPrueba -> {
            existingPrueba.setEnunciado(pruebaDTO.getEnunciado());
            existingPrueba.setPuntuacionMaxima(pruebaDTO.getPuntuacionMaxima());

            if (pruebaDTO.getEspecialidad() != null && pruebaDTO.getEspecialidad().getIdEspecialidad() != null) {
                Especialidad especialidad = especialidadRepository.findById(pruebaDTO.getEspecialidad().getIdEspecialidad())
                    .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
                existingPrueba.setEspecialidad(especialidad);
            } else {
                existingPrueba.setEspecialidad(null);
            }

            return convertToDTO(repository.save(existingPrueba));
        });
    }

    //borrar una prueba

    public boolean delete(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    //convertidor a DTO de prueba

    private PruebaDTO convertToDTO(Prueba prueba) {
        return new PruebaDTO(
            prueba.getIdPrueba(),
            prueba.getEnunciado(),
            convertEspecialidadToDTO(prueba.getEspecialidad()),
            prueba.getPuntuacionMaxima()
        );
    }

    //convertidor a DTO de especialidad

    private EspecialidadDTO convertEspecialidadToDTO(Especialidad especialidad) {
        if (especialidad == null) return null;
        return new EspecialidadDTO(
            especialidad.getIdEspecialidad(),
            especialidad.getCodigo(),
            especialidad.getNombre()
        );
    }

    // metodo para guardar el archivo
    public String saveFile(MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs(); // creo el directorio si no existe
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), path); 
        return path.toString();
    }

    //traer la ultima prueba que hay

    public Optional<Integer> findLastPruebaId() {
        return repository.findTopByOrderByIdPruebaDesc().map(Prueba::getIdPrueba);
    }
}

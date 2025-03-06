package com.fgr.apirest.service;

import com.fgr.apirest.dto.UserDTO;
import com.fgr.apirest.dto.UserRegisterDTO;
import com.fgr.apirest.entity.Especialidad;
import com.fgr.apirest.entity.User;
import com.fgr.apirest.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder){
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    //traer user por username

    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    //insertar un usuario

    public UserDTO save(UserRegisterDTO userDTO) {
        // PASAR DtoInput -> Entidad
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setIdUser(null);
        user.setRole(userDTO.getRole());
        user.setNombre(userDTO.getNombre());
        user.setApellidos(userDTO.getApellidos());
        user.setDni(userDTO.getDni());

        if (userDTO.getEspecialidadId() != null) {
            Especialidad especialidad = new Especialidad();
            especialidad.setIdEspecialidad(userDTO.getEspecialidadId());
            user.setEspecialidad(especialidad);
        }

        // Guardar Entidad en BBDD
        User userCreated = this.userRepository.save(user);

        // PASAR Entidad -> DtoOutput
        return convertToDTO(userCreated);
    }

    public List<UserDTO> findAllByRole(String role) {
        List<User> users = userRepository.findAllByRole(role);
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO);
    }

    public Optional<UserDTO> update(Integer id, UserDTO userDTO) {
        if (userRepository.existsById(id)) {
            User user = convertToEntity(userDTO);
            user.setIdUser(id);
            userRepository.save(user);
            return Optional.of(convertToDTO(user));
        }
        return Optional.empty();
    }

    public boolean delete(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(user.getIdUser());
        userDTO.setRole(user.getRole());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setNombre(user.getNombre());
        userDTO.setApellidos(user.getApellidos());
        userDTO.setDni(user.getDni());
        userDTO.setEspecialidadId(user.getEspecialidad() != null ? user.getEspecialidad().getIdEspecialidad().longValue() : null);

        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        user.setApellidos(userDTO.getApellidos());
        user.setDni(userDTO.getDni());
        user.setNombre(userDTO.getNombre());
        user.setPassword(userDTO.getPassword());

        if (userDTO.getEspecialidadId() != null) {
            Especialidad especialidad = new Especialidad();
            especialidad.setIdEspecialidad(userDTO.getEspecialidadId().intValue());
            user.setEspecialidad(especialidad);
        }

        return user;
    }
}

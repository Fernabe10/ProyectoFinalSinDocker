package com.fgr.apirest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;



import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer idUser;
    private String role;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String dni;
    private Long especialidadId;
}


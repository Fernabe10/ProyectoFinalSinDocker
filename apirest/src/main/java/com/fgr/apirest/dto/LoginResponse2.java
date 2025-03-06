package com.fgr.apirest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse2 {
    private String username;
    private List<String> authorities;
    private String token;
    private Integer especialidadId;
    private Integer idUser;
}


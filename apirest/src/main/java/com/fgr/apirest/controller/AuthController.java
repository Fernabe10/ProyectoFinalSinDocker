package com.fgr.apirest.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fgr.apirest.dto.LoginRequest;

import com.fgr.apirest.dto.LoginResponse2;
import com.fgr.apirest.dto.UserDTO;
import com.fgr.apirest.dto.UserRegisterDTO;
import com.fgr.apirest.service.AuthService;
import com.fgr.apirest.service.UserService;


import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public UserDTO save(@RequestBody UserRegisterDTO userDTO) {
        return this.service.save(userDTO);
    }

    @GetMapping("/publico")
    public String publico() {
        return "¡Hola mundo!";
    }

    @GetMapping("/privado")
    public ResponseEntity<String> privado() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return ResponseEntity.ok().body("¡Hola, estás logueado!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO estás logueado");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse2> login(@RequestBody LoginRequest loginDTO) {
        return ResponseEntity.ok(authService.authenticate(loginDTO));
    }
}


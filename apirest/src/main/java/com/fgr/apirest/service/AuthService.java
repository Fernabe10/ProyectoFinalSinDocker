package com.fgr.apirest.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.fgr.apirest.dto.LoginRequest;
import com.fgr.apirest.dto.LoginResponse2;
import com.fgr.apirest.entity.User;
import com.fgr.apirest.security.JwtTokenProvider;



@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    public AuthService(AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse2 authenticate(LoginRequest loginDTO) {
        Authentication authDTO = new UsernamePasswordAuthenticationToken(
            loginDTO.username(), loginDTO.password()
        );
        Authentication authentication = this.authManager.authenticate(authDTO);

        // Obtener el usuario autenticado
        User user = (User) authentication.getPrincipal();

        // Obtener id de la especialidad si existe
        Integer especialidadId = (user.getEspecialidad() != null) ? user.getEspecialidad().getIdEspecialidad() : null;

        // Generar token
        String token = this.tokenProvider.generateToken(authentication);

        return new LoginResponse2(
            loginDTO.username(),
            authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
            token,
            especialidadId,
            user.getIdUser()
        );
    }
}

package com.fgr.apirest.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuración de seguridad de la API.
 */
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             PasswordEncoder passwordEncoder,
                                             UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors() // habilita CORS
            .and()
            .authorizeRequests(requests -> requests
                .requestMatchers(HttpMethod.GET, "/publico").permitAll()

                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                // ESPECIALIDAD CON AUTENTICACION
                .requestMatchers(HttpMethod.GET, "/api/especialidad").hasAnyAuthority("ROLE_ADMIN", "ROLE_EXPERTO")
                .requestMatchers(HttpMethod.GET, "/api/especialidad/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_EXPERTO")
                .requestMatchers(HttpMethod.POST, "/api/especialidad").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/especialidad/*").hasAuthority("ROLE_ADMIN")

                // EXPERTOS CON AUTENTICACION
                .requestMatchers(HttpMethod.GET, "/api/experto").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/experto/**").hasAuthority("ROLE_ADMIN")

                //PRUEBA CON AUTENTICACION
                .requestMatchers(HttpMethod.GET, "/api/prueba").hasAuthority("ROLE_EXPERTO")
                .requestMatchers(HttpMethod.POST, "/api/prueba").hasAuthority("ROLE_EXPERTO")
                .requestMatchers(HttpMethod.PUT, "/api/prueba/**").hasAuthority("ROLE_EXPERTO")

                // PARTICIPANTE CON AUTENTICACION
                .requestMatchers(HttpMethod.GET, "/api/participante").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/participante").hasAuthority("ROLE_EXPERTO")
                .requestMatchers(HttpMethod.PUT, "/api/participante/**").hasAuthority("ROLE_EXPERTO")

                
                // ITEM SIN AUTENTICACION
                .requestMatchers(HttpMethod.GET, "/api/item").hasAuthority("ROLE_EXPERTO")
                .requestMatchers(HttpMethod.GET, "/api/item/**").hasAuthority("ROLE_EXPERTO")
                .requestMatchers(HttpMethod.POST, "/api/item").hasAuthority("ROLE_EXPERTO")


                // EVALUACION CON AUTENTICACION
                .requestMatchers(HttpMethod.GET, "/api/evaluacion").hasAnyAuthority("ROLE_ADMIN", "ROLE_EXPERTO")
                .requestMatchers(HttpMethod.GET, "/api/evaluacion/**").hasAuthority("ROLE_EXPERTO")
                .requestMatchers(HttpMethod.POST, "/api/evaluacion").hasAuthority("ROLE_EXPERTO")

                // EVALUACION-ITEM CON AUTENTICACION
                .requestMatchers(HttpMethod.GET, "/api/evaluacionitem").hasAuthority("ROLE_EXPERTO")
                .requestMatchers(HttpMethod.POST, "/api/evaluacionitem").hasAuthority("ROLE_EXPERTO")




                // LOGIN Y REGISTRO
                .requestMatchers(HttpMethod.POST, "/register", "/auth/register").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/login").permitAll()

                // otras rutas con autenticacion
                .anyRequest().permitAll());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(source); 
    }
}

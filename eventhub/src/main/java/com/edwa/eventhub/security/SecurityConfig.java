package com.edwa.eventhub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // allow anyone to see our events on the website
                        .requestMatchers("/api/events/**").permitAll()

                        // for the MVP all people can see reservations (test purposes)
                        //TODO after initial tests, change below so only logged in users can reserve
                        .requestMatchers("/api/reservations/**").permitAll()

                        // public routes for login/register
                        .requestMatchers("/api/auth/**").permitAll()

                        // everything else needs authentication
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    // Aici definim regulile CORS pe care Spring Security le va aplica
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Frontend-ul de React
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));

        // Metodele HTTP permise
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitem toate headerele (foarte important pentru a trimite mai târziu token-ul JWT)
        configuration.setAllowedHeaders(List.of("*"));

        // Permitem trimiterea de cookies/credentials
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplică pe toate rutele

        return source;
    }
}
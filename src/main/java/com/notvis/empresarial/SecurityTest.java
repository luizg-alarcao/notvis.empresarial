package com.notvis.empresarial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//Preciso excluir essa classe ao final dos testes do projeto
//Essa classe so serve para ignorar as permissões do projeto, com ela o conteudo fica vulneravel e acessível por todos.
@Configuration
public class SecurityTest {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // nova forma
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}



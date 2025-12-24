package com.algaworks.algafood_api.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {

    public Authentication getAuthentication () {
//        Pegando o objeto de autenticação que vai representar o token usado
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public Long getUsuarioId () {
//        Como esse vai ser o único modelo de token eu não vou fazer uma
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
//        A gente pega o id do usuário autenticado, se o usuário não estiver autenticado ele não consegue fazer o pedido
        return jwt.getClaim("usuario_id");
    }

}

package com.algaworks.algafood_auth.passwordflux;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PasswordAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter("grant_type");
        if (!"password".equals(grantType)) {
            return null;
        }

        // --- 1. Autenticação do Cliente ---
        // Obtém o cliente que já foi autenticado pelo filtro Basic Auth/Client Secret
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        if (clientPrincipal == null || !clientPrincipal.isAuthenticated() || !(clientPrincipal instanceof OAuth2ClientAuthenticationToken)) {
            // Se o cliente não estiver autenticado (Basic Auth falhou) ou não for do tipo esperado, retorna null.
            return null;
        }

        // --- 2. Extração dos Parâmetros ---
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Set<String> scopes = Collections.emptySet();
        String scopeParam = request.getParameter("scope");
        if (scopeParam != null) {
            scopes = Stream.of(scopeParam.split(" "))
                    .collect(Collectors.toSet());
        }

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("username", username);
        additionalParameters.put("password", password);
        // Coloca todos os outros parâmetros que não são padrões
        request.getParameterMap().forEach((key, value) -> {
            if (!key.equals("grant_type") && !key.equals("username") && !key.equals("password") && !key.equals("scope")) {
                additionalParameters.put(key, value[0]);
            }
        });

        // --- 3. Criação do Token Customizado ---
        AuthorizationGrantType authorizationGrantType = new AuthorizationGrantType(grantType);

        return new OAuth2ResourceOwnerPasswordAuthenticationToken(
                authorizationGrantType,
                clientPrincipal,
                scopes, // Agora passando o scopes
                additionalParameters
        );
    }
}
package com.algaworks.algafood_auth.passwordflux;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class OAuth2ResourceOwnerPasswordAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final String username;
    private final Authentication clientPrincipal;
    private final String password;
    private final Set<String> scopes;

    public OAuth2ResourceOwnerPasswordAuthenticationToken(
            AuthorizationGrantType authorizationGrantType,
            Authentication clientPrincipal,
            @Nullable Set<String> scopes,
            @Nullable Map<String, Object> additionalParameters) {
        super(authorizationGrantType, clientPrincipal, additionalParameters);

        this.clientPrincipal = clientPrincipal;
        this.username = (String) additionalParameters.get("username");
        this.password = (String) additionalParameters.get("password");
        this.scopes = (scopes != null) ? Collections.unmodifiableSet(scopes) : Collections.emptySet();
    }

    public Authentication getClientPrincipal () {
        return this.clientPrincipal;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getScopes() {
        return scopes;
    }
}
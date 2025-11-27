package com.algaworks.algafood_auth.passwordflux;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

public class PasswordGrantAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationManager authenticationManager;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<?> tokenGenerator;

    // Use o construtor com todas as dependências necessárias para o fluxo completo
    public PasswordGrantAuthenticationProvider(
            AuthenticationManager authenticationManager,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<?> tokenGenerator) {

        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");

        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OAuth2ResourceOwnerPasswordAuthenticationToken passwordAuthentication =
                (OAuth2ResourceOwnerPasswordAuthenticationToken) authentication;

        // 1. Obter Cliente Autenticado
        OAuth2ClientAuthenticationToken clientPrincipal = (OAuth2ClientAuthenticationToken)
                passwordAuthentication.getClientPrincipal();

        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        // 2. Autenticar o Usuário Final (User/Password)
        // Criamos o token que o Spring Security padrão entende
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        passwordAuthentication.getUsername(),
                        passwordAuthentication.getPassword());

        // Autentica o usuário usando o AuthenticationManager injetado (via UserDetailsService)
        Authentication authenticatedUser = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        DefaultOAuth2TokenContext tokenContext = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(authenticatedUser)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizationGrantType(new AuthorizationGrantType("password"))
                .authorizedScopes(registeredClient.getScopes())
                .authorizationGrant(passwordAuthentication)
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .build();

        OAuth2AccessToken accessToken = (OAuth2AccessToken) this.tokenGenerator.generate(tokenContext);
        if (accessToken == null) {
            throw new AuthenticationException("Failed to generate access token") {};
        }
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(authenticatedUser.getName())
                .authorizationGrantType(new AuthorizationGrantType("password"))
                // O método getAuthorizedScopes() é chamado diretamente no tokenContext, o que está correto.
                .authorizedScopes(tokenContext.getAuthorizedScopes())
                .attribute(Authentication.class.getName(), authenticatedUser);

        OAuth2Authorization authorization = authorizationBuilder
                .token(accessToken)
                .build();


        this.authorizationService.save(authorization);

        // 6. Retornar o Token de Acesso OAuth2 de SAÍDA
        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient, clientPrincipal, accessToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // Suporta APENAS o nosso token customizado de REQUISIÇÃO
        return OAuth2ResourceOwnerPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
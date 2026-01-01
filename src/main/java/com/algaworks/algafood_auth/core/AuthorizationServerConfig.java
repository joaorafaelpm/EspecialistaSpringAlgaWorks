package com.algaworks.algafood_auth.core;

import com.algaworks.algafood_auth.main.model.Usuario;
import com.algaworks.algafood_auth.main.repository.UsuarioRepository;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.hibernate.sql.exec.spi.JdbcOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;
import java.util.*;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();
        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(Customizer.withDefaults())
                )
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .anyRequest().authenticated()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );;

        return http.build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder , JdbcOperations jdbcOperation) {
        RegisteredClient algafoodend = RegisteredClient
                .withId("1")
                .clientId("algafood-end")
                .clientSecret(passwordEncoder.encode("backend123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("WRITE")
                .scope("READ")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();

//        Authorization_Code grant_type + PKCE
//        localhost:8081/oauth2/authorize?response_type=code&client_id=algafood-analytics&state=abc&redirect_uri=http://client-application
        RegisteredClient algafoodanalytics = RegisteredClient
                .withId("2")
                .clientId("algafood-analytics")
                .clientSecret(passwordEncoder.encode("analytics123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8082/callback")
                .scope("WRITE")
                .scope("READ")
                .clientSettings(ClientSettings.builder()
//                        Isso torna o PKCE obrigatório
                        .requireProofKey(true)
                        .requireAuthorizationConsent(true)
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))

                        .build())
                .build();
        RegisteredClient algafoodWeb = RegisteredClient
                .withId("3")
                .clientId("algafood-web")
                .clientSecret(passwordEncoder.encode("web123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8081/laele")
                .scope("WRITE")
                .scope("READ")
                .clientSettings(ClientSettings.builder()
//                        Isso torna o PKCE obrigatório
                        .requireProofKey(true)
                        .requireAuthorizationConsent(true)
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .reuseRefreshTokens(false)
                        .build())
                .build();

//        Implicit grant_type, está depreciado, por isso eu não vou replicar, até para não perder tanto tempo como no password, mas esse estilo de autenticação segue o mesmo padrão do authorization_code, a diferença é que ele não retorna um código na url que nós usamos para acessar o token, ele retorna o token diretamente
//        Esse método é muito menos seguro, não atoa foi depreciado, então é por isso que eu escolho não seguir com ele, mas a teoria é basicamente a mesma. E assim como a teoria é a mesma, a url é exatamente igual, a única diferença é no tipo de retorno, que passa de "code" para "token"
//        localhost:8081/oauth2/authorize?response_type=token&client_id=algafood-web&state=abc&redirect_uri=http://client-application

        JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcOperation);

        jdbcRegisteredClientRepository.save(algafoodend);
        jdbcRegisteredClientRepository.save(algafoodWeb);
        jdbcRegisteredClientRepository.save(algafoodanalytics);
        return jdbcRegisteredClientRepository;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception {
//        Aqui a gente pega a nossa classe do par de chaves do servidor de autenticação e monta o nosso jwt com base nisso, a classe e sua respectiva configuração é feita a partir do application.properties
        char[] keyStorePass = properties.getPassword().toCharArray();
        String keypairAlias = properties.getKeypairAlias();

        Resource jksLocation = properties.getJksLocation();
        InputStream inputStream = jksLocation.getInputStream();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keyStorePass);

        RSAKey rsaKey = RSAKey.load(keyStore, keypairAlias, keyStorePass);

        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer (UsuarioRepository usuarioRepository) {
        return context -> {
            Authentication authentication = context.getPrincipal();

            if (authentication.getPrincipal() instanceof User) {
                User user = (User) authentication.getPrincipal();

                Usuario usuario = usuarioRepository.findByEmail(user.getUsername()).orElseThrow();

                Set<String> authorities = new HashSet<>();

                for (GrantedAuthority authority: user.getAuthorities()) {
                    authorities.add(authority.getAuthority());
                }

                context.getClaims().claim("usuario_id" , usuario.getId());
                context.getClaims().claim("authorities" , authorities);
            }
        };
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = jwt.getClaimAsStringList("authorities");
            if (authorities == null) {
                return Collections.emptyList();
            }

            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> grantedAuthorities = authoritiesConverter.convert(jwt);

            grantedAuthorities.addAll(authorities
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList()
            );

            return grantedAuthorities;
        });

        return converter ;
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(AlgaFoodSecurityProperties properties) {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8081")
                .build();
    }

}
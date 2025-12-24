package com.algaworks.algafood_api.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST , "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                        .requestMatchers(HttpMethod.PUT , "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                        .requestMatchers(HttpMethod.GET , "/v1/cozinhas/**").authenticated()
//                        .requestMatchers("/oauth2/**").authenticated()

                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> {
//                            Adicionando uri para verificação dos tokens
                            jwtConfigurer.jwkSetUri("http://localhost:8081/oauth2/jwks");
//                            Adicionando leitura de permissões
                            jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter());
                        })

                )
                .formLogin(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter () {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            Pego a minha lista de permissões
            List<String> authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null) {
                return Collections.emptyList();
            }

            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

//            Converto isso para permissões reconhecidas pelo sistema
            Collection<GrantedAuthority> grantedAuthorities = authoritiesConverter.convert(jwt);

//            Eu adiciono todas as permissões para essa lista de permissões reconhecidas
            grantedAuthorities.addAll(authorities
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList());

            return grantedAuthorities;
        });

        return converter;
    }


}
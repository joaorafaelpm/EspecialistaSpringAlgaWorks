package com.algaworks.algafood_auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }


    //    Usando usuário padrão, já que alguma biblioteca do meu Spring substituiu a configuração padrão do spring security e já não é possível definir isso no application.properties
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userRonaldo = User
                .withUsername("ronaldo")
//                O Spring oferece uma segurança quando se trata de senhas, então é necessário colocar o prefixo "{noop}" antes de passar a senha do usuário na memória, "{noop}" antes da senha indica que a senha não vai ser criptografada, e se não for usado alguma criptografia e nem especificar a senha com o "{noop}" o próprio spring retorna o status 401 de não autorizado até que a senha seja devidamente protegida
//                Diferente do {noop} nós podemos usar outros meios de criptografia, usando o "{bcrypt}" ou usando a classe "PasswordEncoder" e redefinindo o padrão de criptografia de todas as senhas
                .password(passwordEncoder().encode("123456"))
                .roles("ADMIN")
                .build();

        UserDetails userUsuario = User
                .withUsername("usuario")
                .password(passwordEncoder().encode("laelepontocom"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userRonaldo , userUsuario);
    }

}

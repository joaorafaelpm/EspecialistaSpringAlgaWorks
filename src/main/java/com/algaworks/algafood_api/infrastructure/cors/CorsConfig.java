package com.algaworks.algafood_api.infrastructure.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Mapeia todas as rotas da sua aplicação
                .allowedOrigins("http://127.0.0.1:5500") // Permite requisições da origem "http://127.0.0.1:5500"
                .allowedMethods("*") // Permite os métodos HTTP especificados
                .allowedHeaders("*"); // Permite todos os cabeçalhos
        //.allowCredentials(true); // Permite o envio de credenciais (como cookies e autenticação)
    }
}
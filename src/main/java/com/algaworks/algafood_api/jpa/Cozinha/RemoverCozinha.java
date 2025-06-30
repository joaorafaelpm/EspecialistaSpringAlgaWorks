package com.algaworks.algafood_api.jpa.Cozinha;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RemoverCozinha {

    public static void main(String[] args) {

         ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                 .web(WebApplicationType.NONE)
                 .run(args);

        CozinhaRepository controllerCozinha = applicationContext.getBean(CozinhaRepository.class) ;

        Cozinha cozinha = new Cozinha();
         cozinha.setId(1L);

         controllerCozinha.remove(1L);




    }
}

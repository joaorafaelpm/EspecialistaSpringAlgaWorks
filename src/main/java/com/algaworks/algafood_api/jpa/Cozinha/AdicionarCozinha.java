package com.algaworks.algafood_api.jpa.Cozinha;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AdicionarCozinha {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository controllerCozinha = applicationContext.getBean(CozinhaRepository.class) ;

        Cozinha cozinha1 = new Cozinha(3L , "Japonesa");
        Cozinha cozinha2 = new Cozinha(4L , "Italiana");

        controllerCozinha.save(cozinha1);
        controllerCozinha.save(cozinha2);
    }


}

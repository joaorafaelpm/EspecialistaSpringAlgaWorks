package com.algaworks.algafood_api.jpa.Cozinha;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class ProcurarCozinha {

    public static void main(String[] args) {

         ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                 .web(WebApplicationType.NONE)
                 .run(args);

        CozinhaRepository controllerCozinha = applicationContext.getBean(CozinhaRepository.class) ;


        List<Cozinha> cozinhas = controllerCozinha.listar();

        for (Cozinha i : cozinhas) {
            System.out.println(i.getNome());
        }

        Cozinha cozinha = controllerCozinha.buscar(2L);
        System.out.println(cozinha.toString());

    }
}

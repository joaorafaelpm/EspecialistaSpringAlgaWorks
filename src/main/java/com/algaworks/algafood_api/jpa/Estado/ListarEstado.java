package com.algaworks.algafood_api.jpa.Estado;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class ListarEstado {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);


        List<Estado> listaEstado = estadoRepository.all();

        for (Estado i : listaEstado) {
            System.out.println(i.toString());
        }

        System.out.println(estadoRepository.getById(1L));


    }

}

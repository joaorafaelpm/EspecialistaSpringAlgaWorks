package com.algaworks.algafood_api.jpa.Restaurante;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;

public class AdicionarRestaurante {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restaurante = applicationContext.getBean(RestauranteRepository.class);

        Restaurante restaurante1 = new Restaurante("Ronaldo 2" , new BigDecimal(10.00));
        restaurante.add(restaurante1);
    }

}

package com.algaworks.algafood_api.jpa.Permissao;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.Permissao;
import com.algaworks.algafood_api.domain.repository.PermissaoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AdicionarPermissao {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

        Permissao permissao = new Permissao("Ver pedidos em entrega" , "Pode ver a lista de todos os pedidos que sairam para entrega no período de tempo determinado.");
        permissaoRepository.save(permissao);
    }

}

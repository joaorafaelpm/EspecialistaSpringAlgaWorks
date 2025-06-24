package com.algaworks.algafood_api.jpa.Permissao;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.Permissao;
import com.algaworks.algafood_api.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood_api.domain.repository.PermissaoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class ListarPermissao {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);


        List<Permissao> listaPermissao = permissaoRepository.all();

        for (Permissao i : listaPermissao) {
            System.out.println(i.toString());
        }

        System.out.println(permissaoRepository.getById(1L));


    }

}

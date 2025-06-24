package com.algaworks.algafood_api.jpa.Cidade;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AdicionarCidade {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);



        Cidade cidade = new Cidade(4L , "Cuiabá" , estadoRepository.getById(4L));
        cidadeRepository.save(cidade);
    }

}

package com.algaworks.algafood_api.jpa.FormaPagamento;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class ListarFormaPagamento {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);


        List<FormaPagamento> listaFormaPagamento = formaPagamentoRepository.all();

        for (FormaPagamento i : listaFormaPagamento) {
            System.out.println(i.toString());
        }

        System.out.println(formaPagamentoRepository.getById(1L));


    }

}

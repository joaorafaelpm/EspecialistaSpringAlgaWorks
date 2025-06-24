package com.algaworks.algafood_api.jpa.FormaPagamento;

import com.algaworks.algafood_api.AlgafoodApiApplication;
import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AtualizarFormaPagamento {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);


        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(1L);
        formaPagamentoRepository.save(formaPagamento);
    }

}

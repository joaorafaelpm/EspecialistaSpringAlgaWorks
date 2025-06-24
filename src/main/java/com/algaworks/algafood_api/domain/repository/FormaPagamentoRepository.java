package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> all () ;
    FormaPagamento getById (Long id) ;
    FormaPagamento save (FormaPagamento formaPagamento);
    void remove (Long id) ;

}

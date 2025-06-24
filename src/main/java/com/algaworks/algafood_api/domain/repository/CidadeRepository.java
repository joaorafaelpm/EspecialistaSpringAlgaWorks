package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> all () ;
    Cidade getById (Long id) ;
    Cidade save (Cidade cidade);
    void remove (Long id) ;

}

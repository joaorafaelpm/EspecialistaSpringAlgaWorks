package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> all () ;
    Permissao getById (Long id) ;
    Permissao save (Permissao permissao);
    void remove (Long id) ;

}

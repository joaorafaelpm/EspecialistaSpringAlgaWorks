package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> all () ;
    Estado getById (Long id) ;
    Estado save (Estado estado);
    void remove (Long id) ;

}

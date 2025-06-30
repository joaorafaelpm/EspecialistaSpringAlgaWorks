package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository{

    List<Cozinha> all() ;
    Cozinha getById(Long id) ;
    Cozinha save(Cozinha cozinha);
    void remove(Long id) ;




}

package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> all () ;
    Restaurante getById (Long id);
    Restaurante add (Restaurante restaurante) ;
    void remove (Restaurante restaurante);

}

package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    List<Restaurante> find (String nome , BigDecimal taxaInicial , BigDecimal taxaFinal) ;

    List<Restaurante> findFreteGratisPorNome (String nome) ;

}

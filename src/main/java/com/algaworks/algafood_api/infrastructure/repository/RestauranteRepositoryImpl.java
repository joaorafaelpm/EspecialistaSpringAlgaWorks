package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome , BigDecimal taxaInicial , BigDecimal taxaFinal) {
        var jpql = new StringBuilder();
        var parametros = new HashMap<String , Object>();

        jpql.append("from Restaurante where 0 = 0 ");

        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            parametros.put("nome" , "%" + nome + "%");
        }
        if (taxaInicial != null ) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial" , taxaInicial);
        }
        if (nome != null ) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal" , taxaFinal);
        }

        TypedQuery<Restaurante> query = manager
                .createQuery(jpql.toString() , Restaurante.class);

        parametros.forEach(query::setParameter);

        return query.getResultList() ;

    }

}

package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafood_api.infrastructure.repository.spec.RestauranteSpecs;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood_api.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood_api.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome , BigDecimal taxaInicial , BigDecimal taxaFinal) {

//        Inicia a "fabrica" do criteria
        CriteriaBuilder builder = manager.getCriteriaBuilder() ;

//        Instancia um novo query para fazer o JPQL personalizado
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class) ;

//        Pega a instância do objeto que estamos trabalhando, nesse caso, ele pode acessar as informações da classe de restaurante
        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

//        Criamos os predicados para passar de parâmetro no where
        if (StringUtils.hasLength(nome)) {
            Predicate nomePredicate = builder
                    .like(root.get("nome") , "%" + nome + "%");
            predicates.add(nomePredicate);
        }
        if (taxaInicial != null) {
            predicates.add(builder
                    .greaterThanOrEqualTo(root.get("taxaFrete") , taxaInicial));
        }
        if(taxaFinal != null) {
            predicates.add(builder
                    .lessThanOrEqualTo(root.get("taxaFrete") , taxaFinal));
        }

//        Recebe predicados que são os parâmetros que passamos junto do where no JPQL (like, >= , <=)
        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = manager.createQuery(criteria) ;
        return query.getResultList();

    }

    @Override
    public List<Restaurante> findFreteGratisPorNome (String nome) {
        return restauranteRepository.
                findAll(comFreteGratis().and(comNomeSemelhante(nome))) ;
    }

}

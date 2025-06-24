package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestauranteImpl implements RestauranteRepository {

    @PersistenceContext
    EntityManager manager ;

    @Override
    public List<Restaurante> all () {
        return manager.createQuery("from Restaurante" , Restaurante.class).getResultList() ;
    }

    @Override
    public Restaurante getById (Long id) {
        return manager.find(Restaurante.class , id );
    }

    @Transactional
    @Override
    public Restaurante add (Restaurante restaurante) {
        return manager.merge(restaurante) ;
    }

    @Transactional
    @Override
    public void remove (Long id) {
        Restaurante restaurante = getById(id);
        manager.remove(restaurante);
    }

}

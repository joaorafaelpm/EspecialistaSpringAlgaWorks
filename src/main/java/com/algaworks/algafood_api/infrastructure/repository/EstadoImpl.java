package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstadoImpl implements EstadoRepository {
    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Estado> all() {
        return manager.createQuery("from Estado" , Estado.class).getResultList();
    }

    @Override
    public Estado getById (Long id) {
        return manager.find(Estado.class , id);
    }

    @Override
    @Transactional
    public Estado save(Estado estado) {
        return manager.merge(estado);
    }

    @Override
    @Transactional
    public void remove (Long id) {
        Estado estado = getById(id) ;
        manager.remove(estado);
    }
}

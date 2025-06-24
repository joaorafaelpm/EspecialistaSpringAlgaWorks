package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeImpl implements CidadeRepository {
    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Cidade> all() {
        return manager.createQuery("from Cidade" , Cidade.class).getResultList();
    }

    @Override
    public Cidade getById (Long id) {
        return manager.find(Cidade.class , id);
    }

    @Override
    @Transactional
    public Cidade save(Cidade cidade) {
        return manager.merge(cidade);
    }

    @Override
    @Transactional
    public void remove (Long id) {
        Cidade cidade = getById(id) ;
        manager.remove(cidade);
    }
}

package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.Permissao;
import com.algaworks.algafood_api.domain.repository.PermissaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissaoImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Permissao> all() {
        return manager.createQuery("from Permissao" , Permissao.class).getResultList();
    }

    @Override
    public Permissao getById (Long id) {
        return manager.find(Permissao.class , id);
    }

    @Override
    @Transactional
    public Permissao save(Permissao permissao) {
        return manager.merge(permissao);
    }

    @Override
    @Transactional
    public void remove (Long id) {
        Permissao permissao = getById(id) ;
        manager.remove(permissao);
    }

}

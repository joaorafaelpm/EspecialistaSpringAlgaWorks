package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.FotoProduto;
import com.algaworks.algafood_api.domain.repository.ProdutoRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    EntityManager manager ;

    @Transactional
    @Override
    public FotoProduto save (FotoProduto foto) {
        return manager.merge(foto);
    }
    @Transactional
    @Override
    public void delete (FotoProduto foto) {
        manager.remove(foto);
    }



}

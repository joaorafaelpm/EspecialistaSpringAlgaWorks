package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.FormaPagamento;
import com.algaworks.algafood_api.domain.repository.FormaPagamentoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormaPagamentoImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<FormaPagamento> all() {
        return manager.createQuery("from FormaPagamento" , FormaPagamento.class).getResultList();
    }

    @Override
    public FormaPagamento getById (Long id) {
        return manager.find(FormaPagamento.class , id);
    }

    @Override
    @Transactional
    public FormaPagamento save(FormaPagamento formaPagamento) {
        return manager.merge(formaPagamento);
    }

    @Override
    @Transactional
    public void remove (Long id) {
        FormaPagamento formaPagamento = getById(id) ;
        manager.remove(formaPagamento);
    }

}

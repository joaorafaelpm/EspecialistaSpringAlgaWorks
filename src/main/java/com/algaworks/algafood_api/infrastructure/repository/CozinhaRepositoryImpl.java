package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Cozinha> listar() {
        //        TypedQuery<Cozinha> query = manager.createQuery("from Cozinha" , Cozinha.class) ;
        //        List<Cozinha> listaCozinha = query.getResultList();
        //        return listaCozinha ;
        return manager.createQuery("from Cozinha" , Cozinha.class).getResultList();
    }

    @Override
    public Cozinha buscar (Long id) {
        return manager.find(Cozinha.class , id);
    }

    @Override
    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
//        No caso de uma atualização, a única coisa que é necessário fazer é adiciona-la novamente ao banco, por que ele já entende que o id se repete e que ele deve atualizar a instância que tiver aquele id!
        return manager.merge(cozinha);
    }

    @Override
    @Transactional
    public void remover (Cozinha cozinha) {
        cozinha = buscar(cozinha.getId()) ;
        manager.remove(cozinha);
    }


}

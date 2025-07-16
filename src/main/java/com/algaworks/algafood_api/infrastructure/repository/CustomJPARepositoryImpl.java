package com.algaworks.algafood_api.infrastructure.repository;

import com.algaworks.algafood_api.domain.repository.CustomJPARepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.Optional;

public class CustomJPARepositoryImpl<T , ID>
        extends SimpleJpaRepository<T , ID>
        implements CustomJPARepository<T , ID>      {

    private EntityManager manager ;

    public CustomJPARepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                   EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    @Override
    public Optional<T> findFirst () {
        var jpql = "from " + getDomainClass().getName();

        T entity = manager.createQuery(jpql , getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity) ;
    }

}

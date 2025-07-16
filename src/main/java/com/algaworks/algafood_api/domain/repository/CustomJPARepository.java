package com.algaworks.algafood_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CustomJPARepository<T , ID> extends JpaRepository<T , ID> {

    Optional<T> findFirst() ;

}

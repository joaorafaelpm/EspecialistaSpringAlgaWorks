package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade , Long> {

}

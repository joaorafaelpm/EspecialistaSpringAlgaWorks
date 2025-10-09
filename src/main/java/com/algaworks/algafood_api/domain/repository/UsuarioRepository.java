package com.algaworks.algafood_api.domain.repository;

import com.algaworks.algafood_api.domain.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CustomJPARepository<Usuario , Long> {

    Optional<Usuario> findByEmail (String email) ;

}

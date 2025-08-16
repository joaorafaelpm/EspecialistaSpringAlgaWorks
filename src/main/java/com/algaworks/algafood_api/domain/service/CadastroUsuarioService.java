package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository ;


    public Usuario save (Usuario usuario) {
        return usuarioRepository.save(usuario) ;
    }

}

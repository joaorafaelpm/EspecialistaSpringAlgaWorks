package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository ;

    public Usuario findById (Long id ) {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado um usuário com id de %d!" , id)
                ));
    }

    public Usuario save (Usuario usuario) {
        return usuarioRepository.save(usuario) ;
    }

}

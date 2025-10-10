package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastroUsuarioService  {

    private final UsuarioRepository usuarioRepository ;

    public List<Usuario> findAll () {
        return usuarioRepository.findAll();
    }

    public Usuario findById (Long id ) {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado um usuário com id de %d!" , id)
                ));
    }
    @Transactional
    public Usuario save (Usuario usuario) {
//        A gente precisa fazer esse comando para o JPA parar de instanciar essa entidade, por que quando nós vamos copiar outro email o JPA já entende que aquele email foi alterado da classe original, e caso existam 2 emails daquele a nossa função de procurar pelo email não vai retornal um optional e sim uma lista, por isso precisamos "parar" o JPA
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format(
                    "O email '%s' já está sendo usado" , usuario.getEmail()
            ));
        }

        return usuarioRepository.save(usuario) ;
    }

    @Transactional
    public void remove (Long id) {
        usuarioRepository.deleteById(id);
        usuarioRepository.flush();
    }

    @Transactional
    public void savePassword (Usuario usuario , String senhaAntiga , String senhaNova) {
        if (usuario.senhaNaoCoincideCom(senhaAntiga)) {
            throw new NegocioException("Senhas não coincidem, por favor verifique de novo e tente novamente.");
        }
        if (usuario.senhaCoincideCom(senhaAntiga)) {
            usuario.setSenha(senhaNova);
        }
        usuarioRepository.save(usuario);
    }





}

package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Usuario;
import com.algaworks.algafood_api.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CadastroUsuarioService  {

    private UsuarioRepository usuarioRepository ;
    private PasswordEncoder passwordEncoder ;


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

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuario) ;
    }

    @Transactional
    public void remove (Long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(id);
        }

    }

    @Transactional
    public void changePassword(Long id , String senhaAntiga , String senhaNova) {
        Usuario usuario = findById(id);

        if (!passwordEncoder.matches(senhaAntiga, usuario.getSenha())) {
            throw new NegocioException("Senhas não coincidem, por favor verifique de novo e tente novamente.");
        }
        usuario.setSenha(passwordEncoder.encode(senhaNova));
    }





}

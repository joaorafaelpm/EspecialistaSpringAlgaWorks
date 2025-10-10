package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Grupo;
import com.algaworks.algafood_api.domain.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CadastroGrupoService {

    private final GrupoRepository grupoRepository ;

    private final CadastroPermissaoService permissaoService;
    private final CadastroUsuarioService usuarioService;

    public List<Grupo> findAll () {
        return grupoRepository.findAll();
    }

    public Grupo findById (Long id ) {
        return grupoRepository.findById(id).orElseThrow(() ->
                new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public Grupo save (Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void deleteById (Long id) {
        try {
            grupoRepository.delete(findById(id));
            grupoRepository.flush();
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Entidade de id '%s' está em uso, logo não pode ser deletada." , id));
        }
    }

    @Transactional
    public void associar (Long grupoId , Long permissaoId) {
        Grupo grupo = findById(grupoId);
        grupo.associarPermissao(permissaoService.findById(permissaoId));
    }
    @Transactional
    public void desassociar (Long grupoId , Long permissaoId) {
        Grupo grupo = findById(grupoId);
        grupo.desassociarPermissao(permissaoService.findById(permissaoId));
    }

    @Transactional
    public void associarGrupo (Long usuarioId , Long grupoId) {
        usuarioService.findById(usuarioId).associar(findById(grupoId));
    }
    @Transactional
    public void desassociarGrupo (Long usuarioId , Long grupoId) {
        usuarioService.findById(usuarioId).desassociar(findById(grupoId));
    }


}

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
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        }
        catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Entidade de id '%s' está em uso, logo não pode ser deletada." , id));
        }
    }

}

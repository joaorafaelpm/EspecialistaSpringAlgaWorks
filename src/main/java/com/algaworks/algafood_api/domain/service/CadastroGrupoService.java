package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.model.Grupo;
import com.algaworks.algafood_api.domain.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class CadastroGrupoService {

    private final GrupoRepository grupoRepository ;

    public Grupo save (Grupo grupo) {
        return grupoRepository.save(grupo);
    }


}

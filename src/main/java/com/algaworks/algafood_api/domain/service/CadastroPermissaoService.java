package com.algaworks.algafood_api.domain.service;

import com.algaworks.algafood_api.domain.model.Permissao;
import com.algaworks.algafood_api.domain.repository.GrupoRepository;
import com.algaworks.algafood_api.domain.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class CadastroPermissaoService {

    private final PermissaoRepository permissaoRepository;
    private final CadastroGrupoService grupoService;

    public Permissao save (Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

}

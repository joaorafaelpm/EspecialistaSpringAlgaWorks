package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Permissao;
import com.algaworks.algafood_api.domain.repository.PermissaoRepository;
import com.algaworks.algafood_api.domain.service.CadastroPermissaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissoes")
@AllArgsConstructor
public class PermissaoController {

    private final PermissaoRepository permissaoRepository;
    private final CadastroPermissaoService permissaoService;

    @GetMapping
    public List<Permissao> findALl () {
        return permissaoRepository.findAll();
    }

    @GetMapping("/{permissaoId}")
    public ResponseEntity<Permissao> findById (@PathVariable Long permissaoId) {
        return ResponseEntity.ok(permissaoService.findById(permissaoId));
    }

    @PostMapping
    public Permissao save (@RequestBody Permissao permissao) {
        return permissaoService.save(permissao);
    }

}

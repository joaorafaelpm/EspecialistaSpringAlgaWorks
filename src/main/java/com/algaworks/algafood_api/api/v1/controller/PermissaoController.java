package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.PermissaoAssembler;
import com.algaworks.algafood_api.api.v1.model.PermissaoModel;
import com.algaworks.algafood_api.domain.service.CadastroPermissaoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/permissoes")
@AllArgsConstructor
public class PermissaoController {

    private CadastroPermissaoService permissaoService;

    private PermissaoAssembler permissaoAssembler;

    @GetMapping
    public CollectionModel<PermissaoModel> findALl () {
        return permissaoAssembler.toCollection(permissaoService.findAll());
    }



}

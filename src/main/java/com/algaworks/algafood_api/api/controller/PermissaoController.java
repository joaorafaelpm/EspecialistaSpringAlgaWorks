package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.PermissaoAssembler;
import com.algaworks.algafood_api.api.model.PermissaoModel;
import com.algaworks.algafood_api.domain.service.CadastroPermissaoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissoes")
@AllArgsConstructor
public class PermissaoController {

    private CadastroPermissaoService permissaoService;

    private PermissaoAssembler permissaoAssembler;

    @GetMapping
    public CollectionModel<PermissaoModel> findALl () {
        return permissaoAssembler.toCollection(permissaoService.findAll());
    }



}

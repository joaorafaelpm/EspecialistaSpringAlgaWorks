package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.GrupoAssembler;
import com.algaworks.algafood_api.api.assembler.PermissaoAssembler;
import com.algaworks.algafood_api.api.model.PermissaoModel;
import com.algaworks.algafood_api.domain.service.CadastroPermissaoService;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
@AllArgsConstructor
public class GrupoPermissaoController {

    private final CadastroGrupoService grupoService;

    private final PermissaoAssembler produtoAssembler;

    @GetMapping
    public List<PermissaoModel> listarPermissao (@PathVariable Long grupoId) {
        return produtoAssembler.toCollection(grupoService.findById(grupoId).getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao (@PathVariable Long grupoId , @PathVariable Long permissaoId) {
        grupoService.associar(grupoId , permissaoId);
    }
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao (@PathVariable Long grupoId , @PathVariable Long permissaoId) {
        grupoService.desassociar(grupoId , permissaoId);
    }


}


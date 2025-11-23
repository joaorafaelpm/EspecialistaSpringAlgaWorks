package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.PermissaoAssembler;
import com.algaworks.algafood_api.api.v1.model.PermissaoModel;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
@AllArgsConstructor
public class GrupoPermissaoController {

    private CadastroGrupoService grupoService;

    private PermissaoAssembler permissaoAssembler ;

    @GetMapping
    public CollectionModel<PermissaoModel> listarPermissao (@PathVariable Long grupoId) {
        return permissaoAssembler.toCollectionRefGrupo(grupoId , grupoService.findById(grupoId).getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> associarPermissao (@PathVariable Long grupoId , @PathVariable Long permissaoId) {
        grupoService.associar(grupoId , permissaoId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desassociarPermissao (@PathVariable Long grupoId , @PathVariable Long permissaoId) {
        grupoService.desassociar(grupoId , permissaoId);
        return ResponseEntity.noContent().build();
    }


}


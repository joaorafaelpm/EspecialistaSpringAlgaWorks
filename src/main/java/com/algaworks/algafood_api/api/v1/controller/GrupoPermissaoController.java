package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.PermissaoAssembler;
import com.algaworks.algafood_api.api.v1.model.PermissaoModel;
import com.algaworks.algafood_api.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/grupos/{grupoId}/permissoes")
@AllArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private CadastroGrupoService grupoService;

    private PermissaoAssembler permissaoAssembler ;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<PermissaoModel> listarPermissao (@PathVariable Long grupoId) {
        return permissaoAssembler.toCollectionRefGrupo(grupoId , grupoService.findById(grupoId).getPermissoes());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> associarPermissao (@PathVariable Long grupoId , @PathVariable Long permissaoId) {
        grupoService.associar(grupoId , permissaoId);
        return ResponseEntity.noContent().build();
    }
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desassociarPermissao (@PathVariable Long grupoId , @PathVariable Long permissaoId) {
        grupoService.desassociar(grupoId , permissaoId);
        return ResponseEntity.noContent().build();
    }


}


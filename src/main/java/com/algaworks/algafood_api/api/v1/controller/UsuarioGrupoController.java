package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.GrupoAssembler;
import com.algaworks.algafood_api.api.v1.model.GrupoModel;
import com.algaworks.algafood_api.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import com.algaworks.algafood_api.domain.service.CadastroUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios/{usuarioId}/grupos")
@AllArgsConstructor
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private CadastroGrupoService grupoService;
    private CadastroUsuarioService usuarioService;

    private GrupoAssembler grupoAssembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoModel> pegarTodosGruposDeUmUsuario (@PathVariable Long usuarioId) {
        return grupoAssembler.toCollectionRefUsuario(usuarioId, usuarioService.findById(usuarioId).getGrupos());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associar (@PathVariable Long usuarioId , @PathVariable Long grupoId) {
        grupoService.associarGrupo(usuarioId , grupoId);
        return ResponseEntity.noContent().build();
        }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociar (@PathVariable Long usuarioId , @PathVariable Long grupoId) {
        grupoService.desassociarGrupo(usuarioId , grupoId);
        return ResponseEntity.noContent().build();
        }
}


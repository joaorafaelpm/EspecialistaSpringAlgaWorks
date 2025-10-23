package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.GrupoAssembler;
import com.algaworks.algafood_api.api.assembler.mapper.GrupoMapper;
import com.algaworks.algafood_api.api.model.GrupoModel;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import com.algaworks.algafood_api.domain.service.CadastroUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
@AllArgsConstructor
public class UsuarioGrupoController {

    private CadastroGrupoService grupoService;
    private CadastroUsuarioService usuarioService;

    private GrupoAssembler grupoAssembler;

    @GetMapping
    public CollectionModel<GrupoModel> pegarTodosGruposDeUmUsuario (@PathVariable Long usuarioId) {
        return grupoAssembler.toCollectionRefUsuario(usuarioId, usuarioService.findById(usuarioId).getGrupos());
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associar (@PathVariable Long usuarioId , @PathVariable Long grupoId) {
        grupoService.associarGrupo(usuarioId , grupoId);
        return ResponseEntity.noContent().build();
        }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociar (@PathVariable Long usuarioId , @PathVariable Long grupoId) {
        grupoService.desassociarGrupo(usuarioId , grupoId);
        return ResponseEntity.noContent().build();
        }
}


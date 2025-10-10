package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.GrupoAssembler;
import com.algaworks.algafood_api.api.model.GrupoModel;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import com.algaworks.algafood_api.domain.service.CadastroUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
@AllArgsConstructor
public class UsuarioGrupoController {

    private final CadastroGrupoService grupoService;
    private final CadastroUsuarioService usuarioService;

    private final GrupoAssembler grupoAssembler;

    @GetMapping
    public List<GrupoModel> pegarTodosGruposDeUmUsuario (@PathVariable Long usuarioId) {
        return grupoAssembler.toCollection(usuarioService.findById(usuarioId).getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associando (@PathVariable Long usuarioId , @PathVariable Long grupoId) {
        grupoService.associarGrupo(usuarioId , grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Long usuarioId , @PathVariable Long grupoId) {
        grupoService.desassociarGrupo(usuarioId , grupoId);
    }

}


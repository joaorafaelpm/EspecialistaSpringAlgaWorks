package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.GrupoAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.GrupoDisassembler;
import com.algaworks.algafood_api.api.v1.model.GrupoModel;
import com.algaworks.algafood_api.api.v1.model.DTO.GrupoDTO;
import com.algaworks.algafood_api.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.model.Grupo;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/grupos")
@AllArgsConstructor
public class GrupoController implements GrupoControllerOpenApi {

    private CadastroGrupoService grupoService;

    private GrupoAssembler grupoAssembler;
    private GrupoDisassembler grupoDisassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoModel> all () {
        return grupoAssembler.toCollection(grupoService.findAll());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{grupoId}")
    public GrupoModel getById (@PathVariable Long grupoId) {
        return grupoAssembler.toModel(grupoService.findById(grupoId));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel add (@RequestBody @Valid GrupoDTO grupoDTO) {
        Grupo grupo = grupoDisassembler.grupoDTOToGrupo(grupoDTO);
        return grupoAssembler.toModel(grupoService.save(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    public GrupoModel save (@PathVariable Long grupoId , @RequestBody @Valid GrupoDTO grupoDTO) {
        Grupo grupoAntigo = grupoService.findById(grupoId);
        grupoDisassembler.updateGrupoFromDto(grupoDTO , grupoAntigo);

        return grupoAssembler.toModel(grupoService.save(grupoAntigo));
        }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remove (@PathVariable Long grupoId) {
        grupoService.deleteById(grupoId);
        return ResponseEntity.noContent().build();
    }

}






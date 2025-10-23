package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.GrupoAssembler;
import com.algaworks.algafood_api.api.assembler.mapper.GrupoMapper;
import com.algaworks.algafood_api.api.assembler.disassambler.GrupoDisassembler;
import com.algaworks.algafood_api.api.model.GrupoModel;
import com.algaworks.algafood_api.api.model.DTO.GrupoDTO;
import com.algaworks.algafood_api.domain.model.Grupo;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
@AllArgsConstructor
public class GrupoController {

    private CadastroGrupoService grupoService;

    private GrupoAssembler grupoAssembler;
    private GrupoDisassembler grupoDisassembler;

    @GetMapping
    public CollectionModel<GrupoModel> findAll () {
        return grupoAssembler.toCollection(grupoService.findAll());
    }

    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoModel> findById (@PathVariable Long grupoId) {
        return ResponseEntity.ok(grupoAssembler.toModel(grupoService.findById(grupoId)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel save (@RequestBody @Valid GrupoDTO grupoDTO) {
        Grupo grupo = grupoDisassembler.grupoDTOToGrupo(grupoDTO);
        return grupoAssembler.toModel(grupoService.save(grupo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoModel> save (@PathVariable Long id , @RequestBody @Valid GrupoDTO grupoDTO) {
        Grupo grupoAntigo = grupoService.findById(id);
        grupoDisassembler.updateGrupoFromDto(grupoDTO , grupoAntigo);

        return ResponseEntity.ok(grupoAssembler.toModel(grupoService.save(grupoAntigo)));
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover (@PathVariable Long id) {
        grupoService.deleteById(id);
    }

}






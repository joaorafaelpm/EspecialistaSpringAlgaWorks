package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.model.Grupo;
import com.algaworks.algafood_api.domain.repository.GrupoRepository;
import com.algaworks.algafood_api.domain.service.CadastroGrupoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grupos")
@AllArgsConstructor
@Slf4j
public class GrupoController {

    private final GrupoRepository grupoRepository ;
    private final CadastroGrupoService grupoService;

    @GetMapping
    public List<Grupo> findAll () {
        return grupoRepository.findAll();
    }

    @GetMapping("/{grupoId}")
    public ResponseEntity<Grupo> findById (@PathVariable Long grupoId) {
        Optional<Grupo> grupo = grupoRepository.findById(grupoId);
        if (grupo.isPresent()) {
            return ResponseEntity.ok(grupo.get());
        }
        return ResponseEntity.notFound().build() ;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Grupo save (@RequestBody Grupo grupo) {
        return grupoService.save(grupo);
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<Grupo> save (@PathVariable Long grupoId , @RequestBody Grupo grupo) {
        log.info(grupo.toString());
        Optional<Grupo> grupoAntigo = grupoRepository.findById(grupoId);
        if (grupoAntigo.isPresent()) {
            BeanUtils.copyProperties(grupo , grupoAntigo.get() , "id");
            Grupo grupoAtualizado = grupoService.save(grupoAntigo.get());
            return ResponseEntity.ok(grupoAtualizado);
        }
        return ResponseEntity.notFound().build();
        }
    }


package com.algaworks.algafood_api.api.v1.controller;


import com.algaworks.algafood_api.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.CozinhaDisassembler;
import com.algaworks.algafood_api.api.v1.model.CozinhaModel;
import com.algaworks.algafood_api.api.v1.model.DTO.CozinhaDTO;
import com.algaworks.algafood_api.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {


    private CadastroCozinhaService cozinhaService ;

    private CozinhaModelAssembler cozinhaModelAssembler;
    private CozinhaDisassembler cozinhaDisassembler ;

    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping
    public PagedModel<CozinhaModel> all (Pageable pageable) {
        Page<Cozinha> findAll = cozinhaService.findAll(pageable);
        return pagedResourcesAssembler
                .toModel(findAll , cozinhaModelAssembler);

    }

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping("/{cozinhaId}")
    public CozinhaModel getById (@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toModel(cozinhaService.findById(cozinhaId));
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel add (@RequestBody @Valid CozinhaDTO cozinhaDTO) {
        Cozinha cozinha = cozinhaDisassembler.cozinhaDTOToCozinha(cozinhaDTO);
        return cozinhaModelAssembler.toModel(cozinhaService.save(cozinha)) ;
    }
    @CheckSecurity.Cozinhas.PodeEditar
    @PutMapping("/{cozinhaId}")
    public CozinhaModel save (@PathVariable Long cozinhaId , @RequestBody @Valid CozinhaDTO cozinhaDTO) {
        Cozinha cozinhaAntigo = cozinhaService.findById(cozinhaId);
        cozinhaDisassembler.updateCozinhaFromDto(cozinhaDTO , cozinhaAntigo);
        return cozinhaModelAssembler.toModel(cozinhaService
                        .save(cozinhaId , cozinhaAntigo));
    }
    @CheckSecurity.Cozinhas.PodeEditar
    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Void> remove (@PathVariable Long cozinhaId) {
        cozinhaService.remove(cozinhaId);
        return ResponseEntity.noContent().build();
    }





}

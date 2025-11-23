package com.algaworks.algafood_api.api.v2.controller;


import com.algaworks.algafood_api.api.v2.assembler.CozinhaModelAssemblerV2;
import com.algaworks.algafood_api.api.v2.assembler.disassembler.CozinhaDisassemblerV2;
import com.algaworks.algafood_api.api.v2.model.CozinhaModelV2;
import com.algaworks.algafood_api.api.v2.model.DTO.CozinhaDTOV2;
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
@RequestMapping(value = "/v2/cozinhas")
public class CozinhaControllerV2 {

    private CadastroCozinhaService cozinhaService ;

    private CozinhaModelAssemblerV2 cozinhaModelAssembler;
    private CozinhaDisassemblerV2 cozinhaDisassembler ;

    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<CozinhaModelV2> all (Pageable pageable) {
        Page<Cozinha> findAll = cozinhaService.findAll(pageable);
        PagedModel<CozinhaModelV2> cozinhaPagedModel = pagedResourcesAssembler
                .toModel(findAll , cozinhaModelAssembler);
        return cozinhaPagedModel ;

    }

    @GetMapping("/{id}")
    public CozinhaModelV2 getById (@PathVariable Long id) {
        return cozinhaModelAssembler.toModel(cozinhaService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelV2 add (@RequestBody @Valid CozinhaDTOV2 cozinhaDTO) {
        Cozinha cozinha = cozinhaDisassembler.cozinhaDTOToCozinha(cozinhaDTO);
        return cozinhaModelAssembler.toModel(cozinhaService.save(cozinha)) ;
    }

    @PutMapping("/{id}")
    public  ResponseEntity<CozinhaModelV2> save (@PathVariable Long id , @RequestBody @Valid CozinhaDTOV2 cozinhaDTO) {
        Cozinha cozinhaAntigo = cozinhaService.findById(id);
        cozinhaDisassembler.updateCozinhaFromDto(cozinhaDTO , cozinhaAntigo);
        return ResponseEntity.ok(cozinhaModelAssembler
                .toModel(cozinhaService
                        .save(id , cozinhaAntigo)));
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        cozinhaService.remove(id);
    }





}

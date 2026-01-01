package com.algaworks.algafood_api.api.v1.controller;


import com.algaworks.algafood_api.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.CozinhaDisassembler;
import com.algaworks.algafood_api.api.v1.model.CozinhaModel;
import com.algaworks.algafood_api.api.v1.model.DTO.CozinhaDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/cozinhas")
public class CozinhaController {


    private CadastroCozinhaService cozinhaService ;

    private CozinhaModelAssembler cozinhaModelAssembler;
    private CozinhaDisassembler cozinhaDisassembler ;

    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping
    public PagedModel<CozinhaModel> all (Pageable pageable) {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        Page<Cozinha> findAll = cozinhaService.findAll(pageable);
        PagedModel<CozinhaModel> cozinhaPagedModel = pagedResourcesAssembler
                .toModel(findAll , cozinhaModelAssembler);
        return cozinhaPagedModel ;

    }

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping("/{id}")
    public CozinhaModel getById (@PathVariable Long id) {
        return cozinhaModelAssembler.toModel(cozinhaService.findById(id));
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel add (@RequestBody @Valid CozinhaDTO cozinhaDTO) {
        Cozinha cozinha = cozinhaDisassembler.cozinhaDTOToCozinha(cozinhaDTO);
        return cozinhaModelAssembler.toModel(cozinhaService.save(cozinha)) ;
    }
    @CheckSecurity.Cozinhas.PodeEditar
    @PutMapping("/{id}")
    public  ResponseEntity<CozinhaModel> save (@PathVariable Long id , @RequestBody @Valid CozinhaDTO cozinhaDTO) {
        Cozinha cozinhaAntigo = cozinhaService.findById(id);
        cozinhaDisassembler.updateCozinhaFromDto(cozinhaDTO , cozinhaAntigo);
        return ResponseEntity.ok(cozinhaModelAssembler
                .toModel(cozinhaService
                        .save(id , cozinhaAntigo)));
    }
    @CheckSecurity.Cozinhas.PodeEditar
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        cozinhaService.remove(id);
    }





}

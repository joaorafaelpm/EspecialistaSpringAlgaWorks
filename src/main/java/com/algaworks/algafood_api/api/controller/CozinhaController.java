package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.api.assembler.CozinhaAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.CozinhaDisassembler;
import com.algaworks.algafood_api.api.model.CozinhaModel;
import com.algaworks.algafood_api.api.model.DTO.CozinhaDTO;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    CadastroCozinhaService cozinhaService ;

    CozinhaAssembler cozinhaAssembler ;
    CozinhaDisassembler cozinhaDisassembler ;

    @GetMapping
    public Page<CozinhaModel> all (Pageable pageable) {
        Page<Cozinha> findAll = cozinhaService.findAll(pageable);
        return cozinhaAssembler.toPageable(
                findAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CozinhaModel> getById (@PathVariable Long id) {
        return ResponseEntity.ok().body(cozinhaAssembler.cozinhaToCozinhaModel(cozinhaService.findById(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel add (@RequestBody @Valid CozinhaDTO cozinhaDTO) {
        Cozinha cozinha = cozinhaDisassembler.cozinhaDTOToCozinha(cozinhaDTO);
        return cozinhaAssembler.cozinhaToCozinhaModel(cozinhaService.save(cozinha)) ;
    }

    @PutMapping("/{id}")
    public  ResponseEntity<CozinhaModel> save (@PathVariable Long id , @RequestBody @Valid CozinhaDTO cozinhaDTO) {
        Cozinha cozinhaAntigo = cozinhaService.findById(id);
        cozinhaDisassembler.updateCozinhaFromDto(cozinhaDTO , cozinhaAntigo);
        return ResponseEntity.ok(cozinhaAssembler
                .cozinhaToCozinhaModel(cozinhaService
                        .save(id , cozinhaAntigo)));
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        cozinhaService.remove(id);
    }





}

package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    CozinhaRepository cozinhaRepository ;

    CadastroCozinhaService cadastroCozinha ;

    @GetMapping
    public List<Cozinha> all () {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> getById (@PathVariable Long id) {
        return ResponseEntity.ok().body(cadastroCozinha.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha add (@RequestBody @Valid Cozinha cozinha) {
        return cadastroCozinha.save(cozinha) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> save (@PathVariable Long id , @RequestBody @Valid Cozinha cozinha) {
        Cozinha cozinhaAtualizada = cadastroCozinha.findById(id);
        BeanUtils.copyProperties(cozinha , cozinhaAtualizada , "id");
        Cozinha cozinhaSalva = cadastroCozinha.save(cozinhaAtualizada);
        return ResponseEntity.ok(cozinhaSalva);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        cadastroCozinha.remove(id);
    }





}

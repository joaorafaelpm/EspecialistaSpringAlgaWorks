package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.api.model.CozinhasXMLWrapper;
import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping(value = "/cozinhas") // , produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

    CozinhaRepository cozinhaRepository ;

    CadastroCozinhaService cadastroCozinha ;

    @GetMapping
    public List<Cozinha> all () {
        return cozinhaRepository.all();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CozinhasXMLWrapper allXml () {
        return new CozinhasXMLWrapper(cozinhaRepository.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> getById (@PathVariable Long id) {
        Cozinha cozinha = cozinhaRepository.getById(id) ;
        if (cozinha != null) {
            return ResponseEntity.ok(cozinha);
        }
        return ResponseEntity.notFound().build() ;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha add (@RequestBody Cozinha cozinha) {
        return cadastroCozinha.save(cozinha) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> save (@PathVariable Long id , @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtualizada = cozinhaRepository.getById(id);
        if (cozinhaAtualizada != null) {
            BeanUtils.copyProperties(cozinha , cozinhaAtualizada , "id");
            cozinhaAtualizada = cadastroCozinha.save(cozinhaAtualizada);
            return ResponseEntity.ok(cozinhaAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cozinha> remove (@PathVariable Long id) {
        try {
            cadastroCozinha.remove(id);
            return ResponseEntity.noContent().build();
        }
        catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

    }





}

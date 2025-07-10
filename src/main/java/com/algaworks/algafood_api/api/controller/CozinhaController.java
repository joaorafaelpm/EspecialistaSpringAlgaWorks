package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.repository.CozinhaRepository;
import com.algaworks.algafood_api.domain.service.CadastroCozinhaService;
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
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id) ;
        if (cozinha.isPresent()) {
            return ResponseEntity.ok(cozinha.get());
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
        Optional<Cozinha> cozinhaAtualizada = cozinhaRepository.findById(id);
        if (cozinhaAtualizada.isPresent()) {
            BeanUtils.copyProperties(cozinha , cozinhaAtualizada , "id");
            Cozinha cozinhaSalva = cadastroCozinha.save(cozinhaAtualizada.get());
            return ResponseEntity.ok(cozinhaSalva);
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

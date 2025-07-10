package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/cidades")
public class CidadeController {

    CidadeRepository cidadeRepository;

    CadastroCidadeService cidadeService;

    @GetMapping
    public List<Cidade> all () {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> getById (@PathVariable Long id) {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        if (cidade.isPresent()) {
            return ResponseEntity.ok(cidade.get());
        }
        return ResponseEntity.notFound().build() ;
    }

    @PostMapping
    public ResponseEntity<?> add (@RequestBody Cidade cidade) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeService.save(cidade));
        }
        catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody  Cidade cidade) {
        try {
            Optional<Cidade> cidadeAntiga = cidadeRepository.findById(id);
            if (cidadeAntiga.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            BeanUtils.copyProperties(cidade , cidadeAntiga.get() , "id");
            Cidade cidadeSalva = cidadeService.save(cidadeAntiga.get());
            return ResponseEntity.ok(cidadeSalva);

        }
        catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove (@PathVariable Long id) {
        try {
            cidadeService.remove(id);
            return ResponseEntity.noContent().build();
        }
        catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }


}

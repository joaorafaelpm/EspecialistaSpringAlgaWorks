package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import com.algaworks.algafood_api.domain.service.CadastroEstadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("/estados")
public class EstadoController {

    EstadoRepository estadoRepository ;

    CadastroEstadoService estadoService;

    @GetMapping
    public List<Estado> all () {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> getById (@PathVariable Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);
        if (estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        }
        return ResponseEntity.notFound().build() ;
    }

    @PostMapping
    public  Estado add (@RequestBody Estado estado) {
        return estadoService.save(estado);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Estado> save (@PathVariable Long id , @RequestBody Estado estado) {
        Optional<Estado> estadoAntigo = estadoRepository.findById(id);
        if (estadoAntigo.isPresent()) {
            BeanUtils.copyProperties(estado , estadoAntigo , "id");
            Estado estadoSalvo = estadoService.save(estadoAntigo.get());
            return ResponseEntity.ok(estadoSalvo);
        }
        return ResponseEntity.notFound().build();
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove (@PathVariable Long id) {
        try {
            estadoService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

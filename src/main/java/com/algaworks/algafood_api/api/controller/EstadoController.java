package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import com.algaworks.algafood_api.domain.service.CadastroEstadoService;
import jakarta.validation.Valid;
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
        return ResponseEntity.ok(estadoService.findById(id));
    }

    @PostMapping
    public  Estado add (@RequestBody @Valid Estado estado) {
        return estadoService.save(estado);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Estado> save (@PathVariable Long id , @RequestBody @Valid Estado estado) {
        Estado estadoAntigo = estadoService.findById(id);
        BeanUtils.copyProperties(estado , estadoAntigo , "id");
        Estado estadoSalvo = estadoService.save(estadoAntigo);
        return ResponseEntity.ok(estadoSalvo);
    }




    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long id) {
        estadoService.remove(id);
    }
}

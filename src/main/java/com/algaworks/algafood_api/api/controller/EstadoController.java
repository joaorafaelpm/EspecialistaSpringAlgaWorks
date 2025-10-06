package com.algaworks.algafood_api.api.controller;


import com.algaworks.algafood_api.api.assembler.EstadoAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.EstadoDisassembler;
import com.algaworks.algafood_api.api.model.EstadoModel;
import com.algaworks.algafood_api.api.model.input.EstadoDTO;
import com.algaworks.algafood_api.domain.model.Estado;
import com.algaworks.algafood_api.domain.repository.EstadoRepository;
import com.algaworks.algafood_api.domain.service.CadastroEstadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoRepository estadoRepository ;

    private final CadastroEstadoService estadoService;

    private final EstadoAssembler estadoAssembler ;
    private final EstadoDisassembler estadoDisassembler ;

    @GetMapping
    public List<EstadoModel> all () {
        return estadoAssembler.toCollection(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoModel> getById (@PathVariable Long id) {
        return ResponseEntity.ok(estadoAssembler.estadoToEstadoModel(estadoService.findById(id)));
    }

    @PostMapping
    public EstadoModel add (@RequestBody @Valid EstadoDTO estadoDTO) {
        Estado estado = estadoDisassembler.estadoDTOToEstado(estadoDTO);
        return estadoAssembler.estadoToEstadoModel(estadoService.save(estado));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<EstadoModel> save (@PathVariable Long id , @RequestBody @Valid EstadoDTO estadoDTO) {
        Estado estadoAntigo = estadoService.findById(id);
        estadoDisassembler.updateEstadoFromDto(estadoDTO , estadoAntigo);
        return ResponseEntity.ok(estadoAssembler
                .estadoToEstadoModel(estadoService
                        .save(id , estadoAntigo)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long id) {
        estadoService.remove(id);
    }
}

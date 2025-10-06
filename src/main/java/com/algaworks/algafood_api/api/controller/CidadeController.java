package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.CidadeAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.CidadeDisassembler;
import com.algaworks.algafood_api.api.exceptionhandler.APIError;
import com.algaworks.algafood_api.api.model.CidadeModel;
import com.algaworks.algafood_api.api.model.input.CidadeDTO;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/cidades")
public class CidadeController {

    CidadeRepository cidadeRepository;

    CadastroCidadeService cidadeService;

    CidadeAssembler cidadeAssembler ;
    CidadeDisassembler cidadeDisassembler;

    @GetMapping
    public List<CidadeModel> all () {
        return cidadeAssembler.toCollection(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeModel> getById (@PathVariable Long id) {
        return ResponseEntity.ok(cidadeAssembler.cidadeToCidadeModel(cidadeService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<?> add (@RequestBody @Valid CidadeDTO cidadeDTO) {
        Cidade cidade = cidadeDisassembler.cidadeDTOToCidade(cidadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeAssembler.cidadeToCidadeModel(cidadeService.save(cidade)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody @Valid CidadeDTO cidadeDTO) {
        Cidade cidadeAntiga = cidadeService.findById(id);
        Cidade cidadeAtualizada = cidadeDisassembler.cidadeDTOToCidade(cidadeDTO);

        cidadeDisassembler.updateCidadeFromDto(cidadeDTO , cidadeAntiga);
        cidadeAntiga.setEstado(cidadeAtualizada.getEstado());
        return ResponseEntity.ok(cidadeAssembler
                .cidadeToCidadeModel(cidadeService
                        .save(id , cidadeAntiga)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        cidadeService.remove(id);
    }



}

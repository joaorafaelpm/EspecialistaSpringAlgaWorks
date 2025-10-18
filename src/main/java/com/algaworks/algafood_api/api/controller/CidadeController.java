package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.ResourceUriHelper;
import com.algaworks.algafood_api.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood_api.api.assembler.mapper.CidadeMapper;
import com.algaworks.algafood_api.api.assembler.disassambler.CidadeDisassembler;
import com.algaworks.algafood_api.api.model.CidadeModel;
import com.algaworks.algafood_api.api.model.DTO.CidadeDTO;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@RequestMapping("/cidades")
public class CidadeController {

    CidadeRepository cidadeRepository;

    CadastroCidadeService cidadeService;

    CidadeMapper cidadeMapper ;
    CidadeModelAssembler cidadeAssembler;
    CidadeDisassembler cidadeDisassembler;

    @GetMapping
    public CollectionModel<CidadeModel> all () {
        return cidadeAssembler.toCollection(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeModel getById (@PathVariable Long id) {
        return cidadeAssembler.toModel(cidadeService.findById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CidadeModel add (@RequestBody @Valid CidadeDTO cidadeDTO) {
        try {
            Cidade cidade = cidadeDisassembler.cidadeDTOToCidade(cidadeDTO);
            CidadeModel cidadeModel = cidadeMapper.toModel(cidadeService.save(cidade));

            ResourceUriHelper.addUriResponseHeader(cidadeModel.getId());

            return cidadeModel;
        }
        catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody @Valid CidadeDTO cidadeDTO) {
        Cidade cidadeAntiga = cidadeService.findById(id);
        Cidade cidadeAtualizada = cidadeDisassembler.cidadeDTOToCidade(cidadeDTO);

        cidadeDisassembler.updateCidadeFromDto(cidadeDTO , cidadeAntiga);
        cidadeAntiga.setEstado(cidadeAtualizada.getEstado());
        return ResponseEntity.ok(cidadeMapper
                .toModel(cidadeService
                        .save(id , cidadeAntiga)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        cidadeService.remove(id);
    }



}

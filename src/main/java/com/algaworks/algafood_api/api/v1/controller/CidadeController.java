package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.ResourceUriHelper;
import com.algaworks.algafood_api.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.CidadeDisassembler;
import com.algaworks.algafood_api.api.v1.model.CidadeModel;
import com.algaworks.algafood_api.api.v1.model.DTO.CidadeDTO;
import com.algaworks.algafood_api.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/cidades" , produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CidadeController implements CidadeControllerOpenApi {

    private CidadeRepository cidadeRepository;

    private CadastroCidadeService cidadeService;

    private CidadeModelAssembler cidadeAssembler;
    private CidadeDisassembler cidadeDisassembler;

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping
    public CollectionModel<CidadeModel> all () {
        log.info("Buscando lista de cidades");
        return cidadeAssembler.toCollection(cidadeRepository.findAll());
    }

    @CheckSecurity.Cozinhas.PodeConsultar
    @GetMapping(value = "/{cidadeId}")
    public CidadeModel getById (@PathVariable Long cidadeId) {
        return cidadeAssembler.toModel(cidadeService.findById(cidadeId));
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CidadeModel add (@RequestBody @Valid CidadeDTO cidadeDTO) {
        try {
            Cidade cidade = cidadeDisassembler.cidadeDTOToCidade(cidadeDTO);
            CidadeModel cidadeModel = cidadeAssembler.toModel(cidadeService.save(cidade));

            ResourceUriHelper.addUriResponseHeader(cidadeModel.getId());

            return cidadeModel;
        }
        catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @PutMapping(value = "/{cidadeId}")
    public CidadeModel save (@PathVariable Long cidadeId , @RequestBody @Valid CidadeDTO cidadeDTO) {
        Cidade cidadeAntiga = cidadeService.findById(cidadeId);
        Cidade cidadeAtualizada = cidadeDisassembler.cidadeDTOToCidade(cidadeDTO);

        cidadeDisassembler.updateCidadeFromDto(cidadeDTO , cidadeAntiga);
        cidadeAntiga.setEstado(cidadeAtualizada.getEstado());
        return cidadeAssembler
                .toModel(cidadeService
                        .save(cidadeId , cidadeAntiga));
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Void> remove (@PathVariable Long cidadeId) {
        cidadeService.remove(cidadeId);
        return ResponseEntity.noContent().build();
    }



}

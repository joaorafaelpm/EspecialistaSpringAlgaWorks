package com.algaworks.algafood_api.api.v2.controller;

import com.algaworks.algafood_api.api.ResourceUriHelper;
import com.algaworks.algafood_api.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.CidadeDisassembler;
import com.algaworks.algafood_api.api.v1.model.CidadeModel;
import com.algaworks.algafood_api.api.v1.model.DTO.CidadeDTO;
import com.algaworks.algafood_api.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood_api.api.v2.assembler.disassembler.CidadeDisassemblerV2;
import com.algaworks.algafood_api.api.v2.model.CidadeModelV2;
import com.algaworks.algafood_api.api.v2.model.DTO.CidadeDTOV2;
import com.algaworks.algafood_api.core.web.AlgaMediaTypes;
import com.algaworks.algafood_api.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cidade;
import com.algaworks.algafood_api.domain.repository.CidadeRepository;
import com.algaworks.algafood_api.domain.service.CadastroCidadeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cidades" , produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    private CidadeRepository cidadeRepository;

    private CadastroCidadeService cidadeService;

    private CidadeModelAssemblerV2 cidadeAssembler;
    private CidadeDisassemblerV2 cidadeDisassembler;

    @GetMapping(produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModelV2> all () {
        return cidadeAssembler.toCollection(cidadeRepository.findAll());
    }

    @GetMapping(value = "/{id}" , produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CidadeModelV2 getById (@PathVariable Long id) {
        return cidadeAssembler.toModel(cidadeService.findById(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CidadeModelV2 add (@RequestBody @Valid CidadeDTOV2 cidadeDTO) {
        try {
            Cidade cidade = cidadeDisassembler.cidadeDTOToCidade(cidadeDTO);
            CidadeModelV2 cidadeModel = cidadeAssembler.toModel(cidadeService.save(cidade));

            ResourceUriHelper.addUriResponseHeader(cidadeModel.getIdCidade());

            return cidadeModel;
        }
        catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping(value = "/{id}" , produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
    public CidadeModelV2 save (@PathVariable Long id , @RequestBody @Valid CidadeDTOV2 cidadeDTO) {
        Cidade cidadeAntiga = cidadeService.findById(id);
        Cidade cidadeAtualizada = cidadeDisassembler.cidadeDTOToCidade(cidadeDTO);

        cidadeDisassembler.updateCidadeFromDto(cidadeDTO , cidadeAntiga);
        cidadeAntiga.setEstado(cidadeAtualizada.getEstado());
        return cidadeAssembler
                .toModel(cidadeService
                        .save(id , cidadeAntiga));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void remove (@PathVariable Long id) {
        cidadeService.remove(id);
    }



}

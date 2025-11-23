package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.RestauranteResumoModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.RestauranteDisassembler;
import com.algaworks.algafood_api.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood_api.api.v1.model.RestauranteModel;
import com.algaworks.algafood_api.api.v1.model.DTO.RestauranteDTO;
import com.algaworks.algafood_api.api.v1.model.RestauranteResumoModel;
import com.algaworks.algafood_api.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/restaurantes")
public class RestauranteController {

    private CadastroRestauranteService restauranteService;

    private RestauranteResumoModelAssembler restauranteResumoAssembler;
    private RestauranteApenasNomeModelAssembler restauranteApenasNomeAssembler;
    private RestauranteModelAssembler restauranteAssembler;

    private RestauranteDisassembler restauranteDisessambler;

    @GetMapping
    public CollectionModel<RestauranteResumoModel> listar() {
        return restauranteResumoAssembler.toCollection(restauranteService.findAll());
    }

    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
        return restauranteApenasNomeAssembler.toCollection(restauranteService.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteModel getById (@PathVariable Long id) {
        Restaurante restaurante = restauranteService.findById(id);
        return restauranteAssembler
                .toModel(restaurante);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestauranteModel add (@RequestBody @Valid RestauranteDTO restauranteDTO) {
        try {
            Restaurante restaurante = restauranteService.save(restauranteDisessambler.restauranteDTOToRestaurante(restauranteDTO));
//        O hibernate precisa carregar todos os objetos para que o mapStruct forme eles de acordo com o modelo, então é absolutamente necessário, preparar uma query específica que traga todas as dependências do restaurante.
            return restauranteAssembler
                    .toModel(restaurante);

        }
        catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage()) ;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody @Valid RestauranteDTO restauranteDTO) {
        try {
            Restaurante restauranteAntigo = restauranteService.findById(id);
            Restaurante restauranteAtualizado = restauranteDisessambler.restauranteDTOToRestaurante(restauranteDTO);

            restauranteDisessambler.updateRestauranteFromDto(restauranteDTO , restauranteAntigo);

            restauranteAntigo.setCozinha(restauranteAtualizado.getCozinha());
            restauranteAntigo.getEndereco().setCidade(restauranteAtualizado.getEndereco().getCidade());

            return ResponseEntity.ok(restauranteAssembler
                  .toModel(restauranteService.save(restauranteAntigo)));
        }
        catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage()) ;
        }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar (@PathVariable Long id) {
        restauranteService.ativar(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar (@PathVariable Long id) {
        restauranteService.inativar(id);
        return ResponseEntity.noContent().build();
    }

//    Recebemos uma lista para ativar vários restaurantes de uma vez
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativarMultiplos (@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.ativar(restauranteIds);
            return ResponseEntity.noContent().build();
        }
        catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativarMultiplos (@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.inativar(restauranteIds);
            return ResponseEntity.noContent().build();
        }
        catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir (@PathVariable Long id) {
        restauranteService.abrir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar (@PathVariable Long id) {
        restauranteService.fechar(id);
        return ResponseEntity.noContent().build();
    }


}

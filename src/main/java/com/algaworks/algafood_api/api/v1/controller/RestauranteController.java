package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.RestauranteResumoModelAssembler;
import com.algaworks.algafood_api.api.v1.assembler.disassambler.RestauranteDisassembler;
import com.algaworks.algafood_api.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood_api.api.v1.model.RestauranteModel;
import com.algaworks.algafood_api.api.v1.model.DTO.RestauranteDTO;
import com.algaworks.algafood_api.api.v1.model.RestauranteResumoModel;
import com.algaworks.algafood_api.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood_api.core.security.CheckSecurity;
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
public class RestauranteController implements RestauranteControllerOpenApi {

    private CadastroRestauranteService restauranteService;

    private RestauranteResumoModelAssembler restauranteResumoAssembler;
    private RestauranteApenasNomeModelAssembler restauranteApenasNomeAssembler;
    private RestauranteModelAssembler restauranteAssembler;

    private RestauranteDisassembler restauranteDisessambler;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<RestauranteResumoModel> listar() {
        return restauranteResumoAssembler.toCollection(restauranteService.findAll());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
        return restauranteApenasNomeAssembler.toCollection(restauranteService.findAll());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{restauranteId}")
    public RestauranteModel getById (@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return restauranteAssembler
                .toModel(restaurante);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
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
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}")
    public RestauranteModel save (@PathVariable Long restauranteId , @RequestBody @Valid RestauranteDTO restauranteDTO) {
        try {
            Restaurante restauranteAntigo = restauranteService.findById(restauranteId);
            Restaurante restauranteAtualizado = restauranteDisessambler.restauranteDTOToRestaurante(restauranteDTO);

            restauranteDisessambler.updateRestauranteFromDto(restauranteDTO , restauranteAntigo);

            restauranteAntigo.setCozinha(restauranteAtualizado.getCozinha());
            restauranteAntigo.getEndereco().setCidade(restauranteAtualizado.getEndereco().getCidade());

            return restauranteAssembler
                  .toModel(restauranteService.save(restauranteAntigo));
        }
        catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage()) ;
        }
    }
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}/ativo")
    public ResponseEntity<Void> ativar (@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{restauranteId}/ativo")
    public ResponseEntity<Void> inativar (@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/ativacoes")
    public ResponseEntity<Void> ativarMultiplos (@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.ativar(restauranteIds);
            return ResponseEntity.noContent().build();
        }
        catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/ativacoes")
    public ResponseEntity<Void> inativarMultiplos (@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.inativar(restauranteIds);
            return ResponseEntity.noContent().build();
        }
        catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{restauranteId}/abertura")
    public ResponseEntity<Void> abrir (@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{restauranteId}/fechamento")
    public ResponseEntity<Void> fechar (@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
        return ResponseEntity.noContent().build();
    }


}

package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.RestauranteAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.RestauranteDisassembler;
import com.algaworks.algafood_api.api.model.RestauranteModel;
import com.algaworks.algafood_api.api.model.input.RestauranteDTO;
import com.algaworks.algafood_api.api.model.view.RestauranteView;
import com.algaworks.algafood_api.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes")
@Slf4j
public class RestauranteController {

    private final CadastroRestauranteService restauranteService;

    private final RestauranteAssembler restauranteAssembler;
    private final RestauranteDisassembler restauranteDisessambler;

    @GetMapping
    public MappingJacksonValue all (@RequestParam(required = false) String projecao) {
        List<Restaurante> restaurantes = restauranteService.findAll();
        List<RestauranteModel> restauranteModels = restauranteAssembler
                .toCollection(restaurantes);
        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restauranteModels);

//        Padrão:
        restaurantesWrapper.setSerializationView(RestauranteView.RestauranteResumo.class);

        if ("apenas_nome".equals(projecao)) {
            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
        } else if ("completo".equals(projecao)){
            restaurantesWrapper.setSerializationView(null);
        }

        return restaurantesWrapper;
    }

//    Nós podemos fazer dessa outra forma também, duplicando o Controller com um parâmetro na uri específica
//    separando os métodos por parâmetro e mudando o JacksonView de cada um

//    @GetMapping(params = "projecao=resumo")
//    @JsonView(value = RestauranteView.RestauranteResumo.class)
//    public List<RestauranteModel> allResumido () {
//        return all();
//    }
//    @GetMapping(params = "projecao=apenas_nome")
//    @JsonView(value = RestauranteView.ApenasNome.class)
//    public List<RestauranteModel> allApenasNome () {
//        return all();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteModel> getById (@PathVariable Long id) {
        Restaurante restaurante = restauranteService.findById(id);
        return ResponseEntity.ok(restauranteAssembler
                .restauranteToRestauranteModel(restaurante));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestauranteModel add (@RequestBody @Valid RestauranteDTO restauranteDTO) {
        try {
            Restaurante restaurante = restauranteService.save(restauranteDisessambler.restauranteDTOToRestaurante(restauranteDTO));
//        O hibernate precisa carregar todos os objetos para que o mapStruct forme eles de acordo com o modelo, então é absolutamente necessário, preparar uma query específica que traga todas as dependências do restaurante.
            return restauranteAssembler
                    .restauranteToRestauranteModel(restaurante);

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
                  .restauranteToRestauranteModel(restauranteService.save(restauranteAntigo)));
        }
        catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage()) ;
        }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar (@PathVariable Long id) {
        restauranteService.ativar(id);
    }
    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar (@PathVariable Long id) {
        restauranteService.inativar(id);
    }

//    Recebemos uma lista para ativar vários restaurantes de uma vez
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos (@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.ativar(restauranteIds);
        }
        catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos (@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.inativar(restauranteIds);
        }
        catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir (@PathVariable Long id) {
        restauranteService.abrir(id);
    }

    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar (@PathVariable Long id) {
        restauranteService.fechar(id);
    }


}

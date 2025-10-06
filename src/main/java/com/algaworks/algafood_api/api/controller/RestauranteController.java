package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.RestauranteAssembler;
import com.algaworks.algafood_api.api.assembler.disassambler.RestauranteDisassembler;
import com.algaworks.algafood_api.api.model.RestauranteModel;
import com.algaworks.algafood_api.api.model.input.RestauranteDTO;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes")
@Slf4j
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;

    private final CadastroRestauranteService restauranteService;

    private final RestauranteAssembler restauranteAssembler;
    private final RestauranteDisassembler restauranteDisessambler;

    @GetMapping
    public List<RestauranteModel> all () {
        return restauranteAssembler
                .toCollection(restauranteRepository.findAll());
    }

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
            Restaurante restaurante = restauranteDisessambler.restauranteDTOToRestaurante(restauranteDTO);
            return restauranteAssembler
                    .restauranteToRestauranteModel(restauranteService.save(restaurante));
        }
        catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody @Valid RestauranteDTO restauranteDTO) {
        Restaurante restauranteAntigo = restauranteService.findById(id);
        Restaurante restauranteAtualizado = restauranteDisessambler.restauranteDTOToRestaurante(restauranteDTO);

        restauranteDisessambler.updateRestauranteFromDto(restauranteDTO , restauranteAntigo);
        restauranteAntigo.setCozinha(restauranteAtualizado.getCozinha());
        return ResponseEntity.ok(restauranteAssembler
              .restauranteToRestauranteModel(restauranteService
                      .save(id , restauranteAntigo)));
    }


}

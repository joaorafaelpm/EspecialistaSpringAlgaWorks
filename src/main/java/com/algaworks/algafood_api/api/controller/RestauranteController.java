package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.model.CozinhaModel;
import com.algaworks.algafood_api.api.model.RestauranteModel;
import com.algaworks.algafood_api.api.model.input.RestauranteDTO;
import com.algaworks.algafood_api.core.validation.ValidacaoException;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Cozinha;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroPermissaoService;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes")
@Slf4j
public class RestauranteController {

    RestauranteRepository restauranteRepository;

    CadastroRestauranteService restauranteService;

    private SmartValidator validator;

    @GetMapping
    public List<RestauranteModel> all () {
        return toCollection(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteModel> getById (@PathVariable Long id) {
        Restaurante restaurante = restauranteService.findById(id);
        return ResponseEntity.ok(toModel(restaurante));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestauranteModel add (@RequestBody @Valid RestauranteDTO restauranteDTO) {
        try {
            Restaurante restaurante = toDomainModel(restauranteDTO);
            return toModel(restauranteService.save(restaurante));
        }
        catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody @Valid RestauranteDTO restauranteDTO) {
        try {
          return ResponseEntity.ok(toModel(restauranteService.save(id , toDomainModel(restauranteDTO))));
        }
        catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    private Restaurante toDomainModel (RestauranteDTO restauranteDTO) {
        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteDTO.getCozinha().getId());

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteDTO.getNome());
        restaurante.setTaxaFrete(restauranteDTO.getTaxaFrete());
        restaurante.setCozinha(cozinha);
        return restaurante;
    }

    private RestauranteModel toModel (Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel(restaurante.getCozinha().getId() , restaurante.getCozinha().getNome());
        return new RestauranteModel(restaurante.getId() , restaurante.getNome() , restaurante.getTaxaFrete() , cozinhaModel);
    }

    private List<RestauranteModel> toCollection (List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel).toList();
    }
}

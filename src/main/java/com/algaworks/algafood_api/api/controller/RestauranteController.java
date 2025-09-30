package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes")
@Slf4j
public class RestauranteController {

    RestauranteRepository restauranteRepository;

    CadastroRestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> all () {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> getById (@PathVariable Long id) {
        Restaurante restaurante = restauranteService.findById(id);
        return ResponseEntity.ok(restaurante);
    }


    @PostMapping
    public ResponseEntity<?> add (@RequestBody Restaurante restaurante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restauranteService.save(restaurante));
        }
        catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody Restaurante restaurante) {
        try {
          return ResponseEntity.ok(restauranteService.save(id , restaurante));
        }
        catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage() , e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> savePatch (@PathVariable Long id , @RequestBody Map<String , Object> campos) {
        Restaurante restauranteAntigo = restauranteService.findById(id);
        merge(campos , restauranteAntigo);
        return save(id , restauranteAntigo);
    }

    public void merge (Map<String , Object> dadosOrigem , Restaurante restauranteDestino) {
//        Instancia para mapear o objeto e modificar os valores do campo do body de acordo com os tipos da classe
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem , Restaurante.class);

//        Mapeio todos os dados do body para reconhecer somente os valores especificados e altera-los
        dadosOrigem.forEach((chave , valor) -> {
            Field field = ReflectionUtils.findField(Restaurante.class , chave);
//            Permito que ele acesse instâncias privadas
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field , restauranteOrigem);

//            Eu pego o meu restaurante antigo e defino que as instâncias que vão ser alterados nele são somente as que eu ofereci, usando o field como parâmetro.
//            E como valor para substituir eu uso os que eu modifiquei com base nos padrões da classe usando o "novoValor"
            ReflectionUtils.setField(field , restauranteDestino , novoValor);
        });
    }





}

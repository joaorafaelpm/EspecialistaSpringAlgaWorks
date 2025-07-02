package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.repository.RestauranteRepository;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes")
public class RestauranteController {

    RestauranteRepository restauranteRepository;

    CadastroRestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> all () {
        return restauranteRepository.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> getById (@PathVariable Long id) {
        Restaurante restaurante = restauranteRepository.getById(id);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> add (@RequestBody Restaurante restaurante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restauranteService.save(restaurante));
        }
        catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save (@PathVariable Long id , @RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteAntigo = restauranteRepository.getById(id);
            if (restauranteAntigo != null) {
                BeanUtils.copyProperties(restaurante, restauranteAntigo , "id" );
                restauranteService.save(restaurante);
                return ResponseEntity.ok(restaurante);
            }

            return ResponseEntity.notFound().build();
        }
        catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> savePatch (@PathVariable Long id , @RequestBody Map<String , Object> campos) {
        Restaurante restauranteAntigo = restauranteRepository.getById(id);
        if (restauranteAntigo == null) {
            return ResponseEntity.notFound().build();
        }

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

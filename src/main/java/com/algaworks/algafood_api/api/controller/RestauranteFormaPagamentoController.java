package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.AlgaLinks;
import com.algaworks.algafood_api.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood_api.api.assembler.mapper.FormaPagamentoMapper;
import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private CadastroRestauranteService restauranteService;
    private FormaPagamentoAssembler formaPagamentoAssembler ;

    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar (@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return formaPagamentoAssembler.toCollectionRefRestaurante(restauranteId , restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar (@PathVariable Long restauranteId , @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId , formaPagamentoId) ;
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar (@PathVariable Long restauranteId , @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId , formaPagamentoId) ;
        return ResponseEntity.noContent().build();
    }



}


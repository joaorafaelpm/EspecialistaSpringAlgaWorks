package com.algaworks.algafood_api.api.v1.controller;

import com.algaworks.algafood_api.api.v1.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood_api.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood_api.core.security.CheckSecurity;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/v1/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private CadastroRestauranteService restauranteService;
    private FormaPagamentoAssembler formaPagamentoAssembler ;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar (@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return formaPagamentoAssembler.toCollectionRefRestaurante(restauranteId , restaurante.getFormasPagamento());
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar (@PathVariable Long restauranteId , @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId , formaPagamentoId) ;
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar (@PathVariable Long restauranteId , @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId , formaPagamentoId) ;
        return ResponseEntity.noContent().build();
    }



}


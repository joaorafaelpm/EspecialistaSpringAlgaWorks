package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final CadastroRestauranteService restauranteService;

    private final FormaPagamentoAssembler formaPagamentoAssembler;

    @GetMapping
    public List<FormaPagamentoModel> listar (@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return formaPagamentoAssembler.toCollection(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar (@PathVariable Long restauranteId , @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId , formaPagamentoId);
    }
    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar (@PathVariable Long restauranteId , @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId , formaPagamentoId);
    }



}


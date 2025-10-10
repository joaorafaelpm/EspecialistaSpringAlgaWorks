package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood_api.api.assembler.UsuarioAssembler;
import com.algaworks.algafood_api.api.model.FormaPagamentoModel;
import com.algaworks.algafood_api.api.model.UsuarioModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController {

    private final CadastroRestauranteService restauranteService;

    private final UsuarioAssembler usuarioAssembler;

    @GetMapping
    public List<UsuarioModel> listar (@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return usuarioAssembler.toCollection(restaurante.getUsuarios());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar (@PathVariable Long restauranteId , @PathVariable Long usuarioId) {
        restauranteService.associarUsuarioResponsavel(restauranteId , usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar (@PathVariable Long restauranteId , @PathVariable Long usuarioId) {
        restauranteService.desassociarUsuarioResponsavel(restauranteId , usuarioId);
    }



}



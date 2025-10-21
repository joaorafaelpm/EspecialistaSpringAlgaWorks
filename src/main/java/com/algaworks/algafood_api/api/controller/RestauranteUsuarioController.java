package com.algaworks.algafood_api.api.controller;

import com.algaworks.algafood_api.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood_api.api.assembler.mapper.UsuarioMapper;
import com.algaworks.algafood_api.api.model.UsuarioModel;
import com.algaworks.algafood_api.domain.model.Restaurante;
import com.algaworks.algafood_api.domain.service.CadastroRestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController {

    private CadastroRestauranteService restauranteService;

    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping
    public CollectionModel<UsuarioModel> listar (@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.findById(restauranteId);
        return usuarioModelAssembler.toCollection(restaurante.getUsuarios())
                .removeLinks()
                .add(linkTo(methodOn(RestauranteUsuarioController.class)
                        .listar(restauranteId)).withSelfRel());
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


